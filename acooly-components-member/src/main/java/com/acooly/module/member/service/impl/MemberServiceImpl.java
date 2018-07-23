/*
 * www.acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2018-07-23 18:02 创建
 */
package com.acooly.module.member.service.impl;

import com.acooly.core.common.exception.OrderCheckException;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.mapper.BeanCopier;
import com.acooly.core.utils.validate.Validators;
import com.acooly.module.account.service.AccountManageService;
import com.acooly.module.member.dto.MemberRegistryInfo;
import com.acooly.module.member.entity.Member;
import com.acooly.module.member.enums.MemberActiveTypeEnum;
import com.acooly.module.member.manage.MemberEntityService;
import com.acooly.module.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 会员服务实现
 *
 * @author zhangpu 2018-07-23 18:02
 */
@Slf4j
@Component
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberEntityService memberEntityService;

    @Autowired
    private AccountManageService accountManageService;


    @Override
    public Member register(MemberRegistryInfo memberRegistryInfo) {

        try {
            doCheck(memberRegistryInfo);
            Member member = BeanCopier.copy(memberRegistryInfo, Member.class, BeanCopier.CopyStrategy.CONTAIN_NULL, "status");
            memberEntityService.save(member);

        } catch (Exception e) {

        }


        return null;
    }


    @Override
    public void active(Long memberId, String activeValue) {

    }

    @Override
    public void login(String username, String password) {

    }

    protected Member doRegister(MemberRegistryInfo memberRegistryInfo) {
        Member member = BeanCopier.copy(memberRegistryInfo, Member.class, BeanCopier.CopyStrategy.CONTAIN_NULL, "status");
        memberEntityService.save(member);
        return member;
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
    }


}
