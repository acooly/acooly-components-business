/*
 * www.acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2018-07-23 18:02 创建
 */
package com.acooly.module.member.service.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.exception.OrderCheckException;
import com.acooly.core.utils.Ids;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.enums.Messageable;
import com.acooly.core.utils.mapper.BeanCopier;
import com.acooly.core.utils.validate.Validators;
import com.acooly.module.account.dto.AccountInfo;
import com.acooly.module.account.entity.Account;
import com.acooly.module.account.service.AccountManageService;
import com.acooly.module.member.dto.MemberInfo;
import com.acooly.module.member.dto.MemberRegistryInfo;
import com.acooly.module.member.entity.*;
import com.acooly.module.member.enums.*;
import com.acooly.module.member.exception.MemberErrorEnum;
import com.acooly.module.member.exception.MemberOperationException;
import com.acooly.module.member.service.AbstractMemberService;
import com.acooly.module.member.service.MemberRealNameService;
import com.acooly.module.member.service.MemberService;
import com.acooly.module.member.service.interceptor.MemberRegistryData;
import com.acooly.module.member.service.interceptor.MemberRegistryInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;


/**
 * 会员服务实现
 *
 * @author zhangpu 2018-07-23 18:02
 */
@Slf4j
@Component
public class MemberServiceImpl extends AbstractMemberService implements MemberService {

    @Autowired
    private AccountManageService accountManageService;

    @Autowired
    private MemberRealNameService memberRealNameService;

    /**
     * 会员注册
     *
     * @param memberRegistryInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Member register(MemberRegistryInfo memberRegistryInfo) {
        final Member member;
        final MemberRegistryData memberRegistryData = new MemberRegistryData();
        try {
            memberRegistryData.setMemberRegistryInfo(memberRegistryInfo);
            // 合法性检查
            doCheck(memberRegistryInfo);
            memberRegistryInterceptor.beginRegistry(memberRegistryData);
            // 注册会员主体
            member = doRegister(memberRegistryInfo);
            // 注册附属信息
            doRegisterProfile(memberRegistryInfo, member);
            // 根据配置开关同步实名认证
            doRealNameVerify(memberRegistryInfo, member);
            // 判断执行同步开户
            doOpenAccount(memberRegistryInfo, member);
            memberRegistryData.setMember(member);
            memberRegistryInterceptor.endRegistry(memberRegistryData);
            log.info("注册 成功 member:{}", member);
            // 注册成功后，发送激活验证
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    memberRegistryInterceptor.afterCommitRegistry(memberRegistryData);
                    eventBus.publish(memberRegistryData);
                    doActiveSend(member.getUsername(), memberRegistryInfo.getMemberActiveType());
                }
            });
        } catch (OrderCheckException oe) {
            MemberOperationException moe = new MemberOperationException((Messageable) oe);
            publishExceptionEvent(memberRegistryInterceptor, memberRegistryData, moe);
            throw moe;
        } catch (BusinessException be) {
            publishExceptionEvent(memberRegistryInterceptor, memberRegistryData, be);
            throw be;
        } catch (Exception e) {
            MemberOperationException moe = new MemberOperationException(MemberErrorEnum.MEMBER_INTERNAL_ERROR, "注册内部错误");
            log.error("注册 失败 内部错误：{}", e);
            publishExceptionEvent(memberRegistryInterceptor, memberRegistryData, moe);
            throw moe;
        }
        return member;
    }


    @Override
    public void activeSend(String username, MemberActiveTypeEnum memberActiveType) {
        doActiveSend(username, memberActiveType);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void active(Long memberId, String activeValue, MemberActiveTypeEnum memberActiveType) {
        doActive(memberId, null, activeValue, memberActiveType);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void active(String username, String activeValue, MemberActiveTypeEnum memberActiveType) {
        doActive(null, username, activeValue, memberActiveType);
    }


    @Override
    public void statusChange(MemberInfo memberInfo, MemberStatusEnum memberStatus) {
        throw new UnsupportedOperationException("待实现和开发中...");
    }


    protected void doActive(Long memberId, String username, String activeValue, MemberActiveTypeEnum memberActiveType) {
        try {
            Member member = loadMember(memberId, null, username);
            if (member == null) {
                log.warn("激活 失败 原因:{}, member:{}", MemberErrorEnum.MEMEBER_NOT_EXIST, member);
                throw new MemberOperationException(MemberErrorEnum.MEMEBER_NOT_EXIST);
            }

            if (memberActiveType == MemberActiveTypeEnum.mobileNo || memberActiveType == MemberActiveTypeEnum.email) {
                SendTypeEnum sendType = memberActiveType == MemberActiveTypeEnum.mobileNo ? SendTypeEnum.SMS : SendTypeEnum.MAIL;
                memberSendingService.captchaVerify(member.getUsername(), MemberTemplateEnum.register, sendType, activeValue);


                if (memberProperties.isSendSmsOnActiveSuccess() || memberProperties.isSendMailOnActiveSuccess()) {
                    TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                        @Override
                        public void afterCommit() {
                            if (memberProperties.isSendSmsOnActiveSuccess() && sendType == SendTypeEnum.SMS) {
                                memberSendingService.send(username, MemberTemplateEnum.active, SendTypeEnum.SMS, false);
                            }
                            if (memberProperties.isSendMailOnActiveSuccess() && sendType == SendTypeEnum.MAIL) {
                                memberSendingService.send(username, MemberTemplateEnum.active, SendTypeEnum.MAIL, false);
                            }
                        }
                    });
                }
            }
            member.setStatus(MemberStatusEnum.enable);
            memberEntityService.update(member);
        } catch (OrderCheckException oe) {
            throw oe;
        } catch (BusinessException be) {
            throw be;
        } catch (Exception e) {
            log.error("激活 失败 内部错误：{}", e);
            throw new MemberOperationException(MemberErrorEnum.MEMBER_INTERNAL_ERROR, "激活内部错误");
        }
    }


    /**
     * 处理激活发送验证码
     * <p>
     * 可考虑设计为异步，注册成功后，异步发起
     *
     * @param username
     * @param memberActiveType
     */
    protected void doActiveSend(String username, MemberActiveTypeEnum memberActiveType) {
        // 只处理短信和邮件激活的验证发送
        if (memberActiveType == MemberActiveTypeEnum.mobileNo) {
            memberSendingService.send(username, MemberTemplateEnum.register, SendTypeEnum.SMS, true);
        } else if (memberActiveType == MemberActiveTypeEnum.email) {
            memberSendingService.send(username, MemberTemplateEnum.register, SendTypeEnum.MAIL, true);
        }
    }


    /**
     * 同步开默认账户
     * <p>
     * 根据配置或传入的参数
     *
     * @param memberRegistryInfo
     * @param member
     */
    protected void doOpenAccount(MemberRegistryInfo memberRegistryInfo, Member member) {
        boolean registry = memberRegistryInfo.getAccountRegisty() == null ? memberProperties.isAccountRegisty() : memberRegistryInfo.getAccountRegisty();
        if (!registry) {
            return;
        }
        Account account = accountManageService.openAccount(new AccountInfo(member.getId(), member.getUserNo(), member.getUsername()));
        log.info("注册 同步开账户成功 account:{}", account.getLabel());
    }

    /**
     * 注册会员主体信息
     *
     * @param memberRegistryInfo
     * @return
     */
    protected Member doRegister(MemberRegistryInfo memberRegistryInfo) {
        Member member = BeanCopier.copy(memberRegistryInfo, Member.class, BeanCopier.CopyStrategy.CONTAIN_NULL, "status");
        member.setUserType(memberRegistryInfo.getMemberUserType());
        doSetParent(memberRegistryInfo, member);
        if (Strings.isBlank(member.getUserNo())) {
            member.setUserNo(Ids.getDid());
        }
        doDigestPassword(member);
        // 如果是自动激活，则设置会员状态为enable
        if (memberRegistryInfo.getMemberActiveType() == MemberActiveTypeEnum.auto) {
            member.setStatus(MemberStatusEnum.enable);
        }
        memberEntityService.save(member);
        return member;
    }


    /**
     * 会员实名认证
     *
     * @param member
     */
    protected void doRealNameVerify(MemberRegistryInfo memberRegistryInfo, Member member) {
        boolean realName = memberRegistryInfo.getRealNameOnRegisty() == null ? memberProperties.isRealNameOnRegistry() : memberRegistryInfo.getRealNameOnRegisty();
        if (!realName) {
            return;
        }
        memberRealNameService.verify(member.getId());
    }

    /**
     * 保持会员附属信息
     *
     * @param memberRegistryInfo
     * @param member
     */
    protected void doRegisterProfile(MemberRegistryInfo memberRegistryInfo, Member member) {
        MemberProfile memberProfile = new MemberProfile();
        memberProfile.setId(member.getId());
        memberProfile.setUserNo(member.getUserNo());
        memberProfile.setUsername(member.getUsername());
        memberProfile.setManager(memberRegistryInfo.getManager());
        memberProfile.setBroker(memberRegistryInfo.getBroker());
        memberProfile.setInviter(memberRegistryInfo.getInviter());
        memberProfileEntityService.save(memberProfile);


        if (memberRegistryInfo.getMemberUserType() == MemberUserTypeEnum.personal) {
            MemberPersonal memberPersonal = new MemberPersonal();
            memberPersonal.setId(member.getId());
            memberPersonal.setUserNo(member.getUserNo());
            memberPersonal.setUsername(member.getUsername());
            memberPersonalEntityService.save(memberPersonal);
        } else {
            MemberEnterprise memberEnterprise = new MemberEnterprise();
            memberEnterprise.setId(member.getId());
            memberEnterprise.setUserNo(member.getUserNo());
            memberEnterprise.setUsername(member.getUsername());
            memberEnterprise.setEntType(memberRegistryInfo.getMemberUserType());
            memberEnterpriseEntityService.save(memberEnterprise);
        }


        // 其他关联信息注册时候同步写入，后续只需要修改，简化后期开发成本
        MemberContact memberContact = new MemberContact();
        memberContact.setId(member.getId());
        memberContact.setUserNo(member.getUserNo());
        memberContact.setUsername(member.getUsername());
        memberContact.setMobileNo(member.getMobileNo());
        memberContactEntityService.save(memberContact);
    }


    /**
     * 判断给会员设置parent
     *
     * @param memberRegistryInfo
     * @param member
     */
    protected void doSetParent(MemberRegistryInfo memberRegistryInfo, Member member) {
        if (memberRegistryInfo.getParentid() != null || Strings.isNotBlank(memberRegistryInfo.getParentUserNo())) {
            Member parent = loadMember(memberRegistryInfo.getParentid(), memberRegistryInfo.getParentUserNo(), null);
            if (parent != null) {
                member.setParentid(parent.getId());
                member.setParentUserNo(parent.getUserNo());
            } else {
                log.warn("注册 失败 设置的parentId没有对应的会员存在。 memberInfo:{}", memberRegistryInfo.getLabel());
            }
        }
    }


    protected void doCheck(MemberRegistryInfo memberRegistryInfo) {
        Validators.assertJSR303(memberRegistryInfo);
        if (memberRegistryInfo.getMemberActiveType() != MemberActiveTypeEnum.human) {
            boolean hasMobileNo = Strings.isNotBlank(memberRegistryInfo.getMobileNo());
            boolean hasEmail = Strings.isNotBlank(memberRegistryInfo.getEmail());
            MemberActiveTypeEnum activeType = memberRegistryInfo.getMemberActiveType();

            if (activeType == MemberActiveTypeEnum.mobileNo && !hasMobileNo) {
                log.warn("注册 失败 memberInfo:{}, 原因:手机激活方式手机号码不能为空", memberRegistryInfo.getLabel());
                OrderCheckException.throwIt("mobileNo", "手机激活方式手机号码不能为空");
            }

            if (activeType == MemberActiveTypeEnum.email && !hasEmail) {
                log.warn("注册 失败 memberInfo:{}, 原因:邮件激活方式邮件不能为空", memberRegistryInfo.getLabel());
                OrderCheckException.throwIt("mobileNo", "邮件激活方式邮件不能为空");
            }

            if (activeType == MemberActiveTypeEnum.auto && !hasEmail && !hasMobileNo) {
                log.warn("注册 失败 memberInfo:{}, 原因:自动激活方式手机号码或邮件不能同时为空", memberRegistryInfo.getLabel());
                OrderCheckException.throwIt("mobileNo", "自动激活方式手机号码或邮件不能同时为空");
            }
        }

        if (loadMember(null, null, memberRegistryInfo.getUsername()) != null) {
            log.warn("注册 失败 原因:{}, memberInfo:{}", MemberErrorEnum.MEMEBER_USERNAME_ALREADY_EXIST, memberRegistryInfo.getLabel());
            throw new MemberOperationException(MemberErrorEnum.MEMEBER_USERNAME_ALREADY_EXIST);
        }

        if (Strings.isNotBlank(memberRegistryInfo.getUserNo()) && loadMember(null, memberRegistryInfo.getUserNo(), null) != null) {
            log.warn("注册 失败 原因:{}, memberInfo:{}", MemberErrorEnum.MEMEBER_USERNO_ALREADY_EXIST, memberRegistryInfo.getLabel());
            throw new MemberOperationException(MemberErrorEnum.MEMEBER_USERNO_ALREADY_EXIST);
        }

        if (Strings.isNoneBlank(memberRegistryInfo.getBroker()) && memberProperties.isBrokerMustBeMember()
                && loadMember(null, null, memberRegistryInfo.getBroker()) == null) {
            log.warn("注册 失败 原因:{}, memberInfo:{}", MemberErrorEnum.BROKER_MUST_BE_A_MEMBER, memberRegistryInfo.getLabel());
            throw new MemberOperationException(MemberErrorEnum.BROKER_MUST_BE_A_MEMBER);
        }

        if (Strings.isNoneBlank(memberRegistryInfo.getInviter()) && memberProperties.isInviterMustBeMember()
                && loadMember(null, null, memberRegistryInfo.getInviter()) == null) {
            log.warn("注册 失败 原因:{}, memberInfo:{}", MemberErrorEnum.INVITER_MUST_BE_A_MEMBER, memberRegistryInfo.getLabel());
            throw new MemberOperationException(MemberErrorEnum.INVITER_MUST_BE_A_MEMBER);
        }
    }

    private void publishExceptionEvent(MemberRegistryInterceptor memberRegistryInterceptor,
                                       MemberRegistryData memberRegistryData, BusinessException be) {
        try {
            memberRegistryInterceptor.exceptionRegistry(memberRegistryData, be);
        } catch (Exception e) {
        }
    }


}
