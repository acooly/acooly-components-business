/*
 * www.acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2018-07-27 15:00 创建
 */
package com.acooly.module.member.service.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.exception.OrderCheckException;
import com.acooly.core.utils.Dates;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.enums.ResultStatus;
import com.acooly.core.utils.enums.WhetherStatus;
import com.acooly.module.certification.enums.CertResult;
import com.acooly.module.member.dto.MemberRealNameInfo;
import com.acooly.module.member.entity.Member;
import com.acooly.module.member.entity.MemberContact;
import com.acooly.module.member.entity.MemberPersonal;
import com.acooly.module.member.entity.MemberProfile;
import com.acooly.module.member.enums.GenderEnum;
import com.acooly.module.member.enums.MemberUserTypeEnum;
import com.acooly.module.member.exception.MemberErrorEnum;
import com.acooly.module.member.exception.MemberOperationException;
import com.acooly.module.member.manage.MemberEntityService;
import com.acooly.module.member.service.AbstractMemberService;
import com.acooly.module.member.service.MemberRealNameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.OperationNotSupportedException;
import java.util.Date;


/**
 * @author zhangpu 2018-07-27 15:00
 */
@Slf4j
@Component
public class MemberRealNameServiceImpl extends AbstractMemberService implements MemberRealNameService {


    @Autowired
    protected MemberEntityService memberEntityService;

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void verify(Long id) {
        try {
            Member member = loadMember(id);
            if (member == null) {
                log.warn("实名 [失败] 原因:{}, member:{}", MemberErrorEnum.MEMEBER_NOT_EXIST, id);
                throw new MemberOperationException(MemberErrorEnum.MEMEBER_NOT_EXIST);
            }

            if (member.getUserType() == MemberUserTypeEnum.personal) {
                doPersonalVerify(member);
                log.info("实名 [成功] 个人实名认证 member:{}", member);
            } else {
                // todo: 待开发企业实名认证
                throw new OperationNotSupportedException();
            }
        } catch (OrderCheckException oe) {
            throw oe;
        } catch (BusinessException be) {
            throw be;
        } catch (Exception e) {
            log.error("实名 失败 内部错误：{}", e);
            throw new MemberOperationException(MemberErrorEnum.MEMBER_INTERNAL_ERROR, "实名内部错误");
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void verify(MemberRealNameInfo memberRealNameInfo) {
        try {
            Member member = loadMember(memberRealNameInfo.getId());
            if (member == null) {
                log.warn("实名 [失败] 原因:{}, memberId:{}", MemberErrorEnum.MEMEBER_NOT_EXIST, memberRealNameInfo.getId());
                throw new MemberOperationException(MemberErrorEnum.MEMEBER_NOT_EXIST);
            }

            if (member.getUserType() == MemberUserTypeEnum.personal && memberRealNameInfo.getPersonalRealNameInfo() == null) {
                log.warn("实名 [失败] 原因:{}, memberId:{}", "个人实名认证的信息为空或不全", memberRealNameInfo.getId());
                throw new MemberOperationException(MemberErrorEnum.MEMEBER_REALNAME_DATA_MISSING, "个人实名认证的信息为空或不全");
            }
        } catch (OrderCheckException oe) {
            throw oe;
        } catch (BusinessException be) {
            throw be;
        } catch (Exception e) {
            log.error("实名 失败 内部错误：{}", e);
            throw new MemberOperationException(MemberErrorEnum.MEMBER_INTERNAL_ERROR, "实名内部错误");
        }


    }


    protected void doPersonalVerify(Member member) {
        CertResult certResult = doPersonalRealName(member);
        MemberPersonal memberPersonal = memberPersonalEntityService.get(member.getId());
        if (memberPersonal != null) {
            memberPersonal.setBirthday(convertBirthday(certResult.getBirthday()));
            memberPersonal.setGender(GenderEnum.find(certResult.getSex()));
            memberPersonalEntityService.update(memberPersonal);
        }
        if (Strings.isNotBlank(certResult.getAddress())) {
            MemberContact memberContact = memberContactEntityService.get(member.getId());
            if (memberContact != null) {

                String[] addresses = Strings.split(certResult.getAddress(), '-');
                if (addresses != null && addresses.length >= 1) {
                    memberContact.setProvince(addresses[0]);
                }

                if (addresses != null && addresses.length >= 2) {
                    memberContact.setCity(addresses[1]);
                }

                if (addresses != null && addresses.length >= 3) {
                    memberContact.setDistrict(addresses[2]);
                }

                if (addresses != null) {
                    memberContact.setAddress(Strings.join(addresses, ""));
                }
            }
            memberContactEntityService.update(memberContact);
        }
        MemberProfile memberProfile = memberProfileEntityService.get(member.getId());
        if (memberProfile != null) {
            memberProfile.setRealNameStatus(WhetherStatus.yes);
            memberProfileEntityService.update(memberProfile);
        }
    }


    private Date convertBirthday(String birhday) {
        if (Strings.isBlank(birhday)) {
            return null;
        }
        try {
            return Dates.parse(birhday);
        } catch (Exception e) {
            log.warn("实名 失败 实名生日转换日期失败。 birthday:{}", birhday);
            return null;
        }
    }


    /**
     * 会员实名认证
     *
     * @param member
     */
    protected CertResult doPersonalRealName(Member member) {
        if (member.getUserType() != MemberUserTypeEnum.personal.code()
                || Strings.isBlank(member.getRealName()) || Strings.isBlank(member.getIdCardNo())) {
            log.warn("实名 [失败] 认证类型或认证数据不全");
            throw new MemberOperationException(MemberErrorEnum.MEMEBER_REALNAME_DATA_MISSING);
        }
        CertResult certResult = null;
        try {
            certResult = certificationService.certification(member.getRealName(), member.getIdCardNo());
        } catch (Exception e) {
            log.warn("实名 [失败] 实名认证调用。 错误信息:{}", e.getMessage());
            throw new MemberOperationException(MemberErrorEnum.MEMEBER_REALNAME_FAIL, "调用认证组件异常");
        }
        // 认证调用成功后，certResult不会为空，不做空判断。
        if (!ResultStatus.success.code().equals(certResult.getResultCode())) {
            log.warn("实名 [失败] {} 原因:{}, member:{}", MemberErrorEnum.MEMEBER_REALNAME_FAIL.message(), certResult.getResultMessage(), member);
            throw new MemberOperationException(MemberErrorEnum.MEMEBER_REALNAME_FAIL, certResult.getResultMessage());
        }
        return certResult;
    }
}
