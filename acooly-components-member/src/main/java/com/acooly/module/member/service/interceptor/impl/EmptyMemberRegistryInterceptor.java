/*
 * www.acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2018-07-26 18:05 创建
 */
package com.acooly.module.member.service.interceptor.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.module.member.entity.Member;
import com.acooly.module.member.service.interceptor.MemberRegistryData;
import com.acooly.module.member.service.interceptor.MemberRegistryInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhangpu 2018-07-26 18:05
 */
@Slf4j
@Component
public class EmptyMemberRegistryInterceptor implements MemberRegistryInterceptor {


    @Override
    public void beginRegistry(MemberRegistryData memberRegistryData) {
        log.debug("Empty beginRegistry");
    }

    @Override
    public void endRegistry(MemberRegistryData memberRegistryData) {
        log.debug("Empty endRegistry");
    }

    @Override
    public void afterCommitRegistry(MemberRegistryData memberRegistryData) {
        log.debug("Empty afterCommitRegistry");
    }

    @Override
    public void exceptionRegistry(MemberRegistryData memberRegistryData, BusinessException be) {
        log.debug("Empty exceptionRegistry");
    }

    @Override
    public void onCaptchaSms(Member member, Map<String, Object> data) {
        log.debug("Empty onCaptchaSms");
    }

    @Override
    public void onCaptchaMail(Member member, Map<String, String> data) {
        log.debug("Empty onCaptchaMail");
    }
}
