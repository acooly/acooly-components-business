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
import com.acooly.core.utils.Dates;
import com.acooly.core.utils.Encodes;
import com.acooly.core.utils.Ids;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.mapper.BeanCopier;
import com.acooly.core.utils.validate.Validators;
import com.acooly.module.account.dto.AccountInfo;
import com.acooly.module.account.service.AccountManageService;
import com.acooly.module.certification.enums.CertResult;
import com.acooly.module.member.MemberProperties;
import com.acooly.module.member.dto.MemberRegistryInfo;
import com.acooly.module.member.entity.Member;
import com.acooly.module.member.entity.MemberContact;
import com.acooly.module.member.entity.MemberPersonal;
import com.acooly.module.member.entity.MemberProfile;
import com.acooly.module.member.enums.MemberActiveTypeEnum;
import com.acooly.module.member.enums.MemberUserTypeEnum;
import com.acooly.module.member.exception.MemberErrorEnum;
import com.acooly.module.member.exception.MemberOperationException;
import com.acooly.module.member.manage.MemberContactEntityService;
import com.acooly.module.member.manage.MemberEntityService;
import com.acooly.module.member.manage.MemberPersonalEntityService;
import com.acooly.module.member.manage.MemberProfileEntityService;
import com.acooly.module.member.service.AbstractMemberService;
import com.acooly.module.member.service.MemberService;
import com.acooly.module.security.utils.Digests;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * 会员服务实现
 *
 * @author zhangpu 2018-07-23 18:02
 */
@Slf4j
@Component
public class MemberServiceImpl extends AbstractMemberService implements MemberService {

    public static final int HASH_INTERATIONS = 512;
    public static final int SALT_SIZE = 8;

    @Autowired
    private MemberEntityService memberEntityService;

    @Autowired
    private MemberProfileEntityService memberProfileEntityService;

    @Autowired
    private MemberContactEntityService memberContactEntityService;

    @Autowired
    private MemberPersonalEntityService memberPersonalEntityService;

    @Autowired
    private AccountManageService accountManageService;

    @Autowired
    private MemberProperties memberProperties;


    /**
     * 会员注册
     * <p>
     * todo: 外部扩展的事件机制
     *
     * @param memberRegistryInfo
     * @return
     */
    @Override
    @Transactional
    public Member register(MemberRegistryInfo memberRegistryInfo) {
        Member member = null;
        try {
            // 合法性检查
            doCheck(memberRegistryInfo);
            // 注册会员主体
            member = doRegister(memberRegistryInfo);
            // 注册附属信息
            doRegisterProfile(memberRegistryInfo, member);
            // 判断执行同步开户
            doOpenAccount(memberRegistryInfo, member);
        } catch (OrderCheckException oe) {
            throw oe;
        } catch (BusinessException be) {
            throw be;
        } catch (Exception e) {
            log.error("注册 失败 内部错误：{}", e);
            throw new MemberOperationException(MemberErrorEnum.MEMBER_INTERNAL_ERROR, "注册内部错误");
        }
        return member;
    }


    @Override
    public void active(Long memberId, String activeValue) {

    }

    @Override
    public void login(String username, String password) {

    }


    /**
     * 处理激活发送验证码
     * <p>
     * 可考虑设计为异步，注册成功后，异步发起
     *
     * @param memberRegistryInfo
     */
    protected void doActiveSend(MemberRegistryInfo memberRegistryInfo) {

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
        if (memberRegistryInfo.getAccountRegisty() == null) {
            if (!memberProperties.isAccountRegisty()) {
                return;
            }
        } else {
            if (!memberRegistryInfo.getAccountRegisty()) {
                return;
            }
        }

        accountManageService.openAccount(new AccountInfo(member.getId(), member.getUserNo()));
        log.info("注册 同步开账户成功");
    }

    /**
     * 注册会员主体信息
     *
     * @param memberRegistryInfo
     * @return
     */
    protected Member doRegister(MemberRegistryInfo memberRegistryInfo) {
        Member member = BeanCopier.copy(memberRegistryInfo, Member.class, BeanCopier.CopyStrategy.CONTAIN_NULL, "status");
        doSetParent(memberRegistryInfo, member);
        if (Strings.isBlank(member.getUserNo())) {
            member.setUserNo(Ids.getDid());
        }
        doDigestPassword(member);
        memberEntityService.save(member);
        return member;
    }


    /**
     * 会员实名认证
     *
     * @param member
     */
    protected CertResult doRealNameAuthenticate(Member member) {
        if (!memberProperties.isRealNameAuthOnRegistry()
                || member.getUserType() != MemberUserTypeEnum.personal.code()
                || Strings.isBlank(member.getRealName()) || Strings.isBlank(member.getIdCardNo())) {
            return null;
        }

        try {
//            CertResult certResult = certificationService.certification(member.getRealName(), member.getIdCardNo());
            //todo:此处待实名认证组件完善文档后，补充判断认证结果逻辑。如果认证失败，则跳过。
//            return certResult;
        } catch (Exception e) {
            log.warn("注册 实名认证调用使用，忽略实名认证 继续注册。 错误信息:{}", e.getMessage());
        }

        return null;
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


        CertResult certResult = doRealNameAuthenticate(member);
        MemberPersonal memberPersonal = new MemberPersonal();
        memberPersonal.setId(member.getId());
        memberPersonal.setUserNo(member.getUserNo());
        memberPersonal.setUsername(member.getUsername());
        if (certResult != null) {
            memberPersonal.setBirthday(Dates.parse(certResult.getBirthday()));
//            memberPersonal.setGender();
        }
        memberPersonalEntityService.save(memberPersonal);

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

    /**
     * 依次尝试加载会员
     *
     * @param id
     * @param userNo
     * @param username
     * @return
     */
    protected Member loadMember(Long id, String userNo, String username) {
        if (id != null) {
            return memberEntityService.get(id);
        }

        if (Strings.isNotBlank(userNo)) {
            return memberEntityService.findUniqueByUserNo(userNo);
        }

        if (Strings.isNotBlank(username)) {
            return memberEntityService.findUniqueByUsername(username);
        }
        return null;
    }


//    public void validatePassword(Member member, String password) {
//        if (StringUtils.isBlank(password)) {
//            throw new RuntimeException("密码不能为空");
//        }
//        String dbSalt = member.getSalt();
//        String enPassword = digestPassword(password, dbSalt);
//        String dbPassword = member.getPassword();
//        if (!enPassword.equals(dbPassword)) {
//            throw new RuntimeException("密码错误,请重新输入");
//        }
//    }

    protected void doDigestPassword(Member member) {
        String salt = Encodes.encodeHex(Digests.generateSalt(SALT_SIZE));
        member.setSalt(salt);
        member.setPassword(digestPassword(member.getPassword(), salt));
    }

    protected String digestPassword(String plainPassword, String salt) {
        return Encodes.encodeHex(Digests.sha1(plainPassword.getBytes(), Encodes.decodeHex(salt), HASH_INTERATIONS));
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


}
