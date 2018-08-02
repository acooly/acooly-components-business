/*
 * www.acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2018-08-02 14:16 创建
 */
package com.acooly.module.member.event;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.module.member.entity.Member;
import com.acooly.module.member.service.interceptor.MemberRegistryData;
import com.acooly.module.member.service.interceptor.MemberRegistryInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 测试用注册拦截器
 *
 * @author zhangpu 2018-08-02 14:16
 */
@Component
@Slf4j
public class TestMemberRegistryInterceptor implements MemberRegistryInterceptor {

    @Override
    public void beginRegistry(MemberRegistryData memberRegistryData) {

    }

    @Override
    public void endRegistry(MemberRegistryData memberRegistryData) {
        // 组件内标准注册完成，事务提交前
        log.info("这里是组件内标准注册完成，事务提交前的拦截，可以在这里进行扩展注册功能的开发。memberRegistryData:{}", memberRegistryData);

    }

    @Override
    public void afterCommitRegistry(MemberRegistryData memberRegistryData) {

    }

    @Override
    public void exceptionRegistry(MemberRegistryData memberRegistryData, BusinessException be) {

    }

    @Override
    public void onCaptchaSms(Member member, Map<String, Object> data) {

    }

    @Override
    public void onCaptchaMail(Member member, Map<String, String> data) {

    }
}
