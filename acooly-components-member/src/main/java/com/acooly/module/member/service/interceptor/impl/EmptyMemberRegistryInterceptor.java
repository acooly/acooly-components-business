/*
 * www.acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2018-07-26 18:05 创建
 */
package com.acooly.module.member.service.interceptor.impl;

import com.acooly.module.member.service.interceptor.MemberRegistryData;
import com.acooly.module.member.service.interceptor.MemberRegistryInterceptor;
import org.springframework.stereotype.Component;

/**
 * @author zhangpu 2018-07-26 18:05
 */
@Component
public class EmptyMemberRegistryInterceptor implements MemberRegistryInterceptor {

    @Override
    public void beforeRegistry(MemberRegistryData memberRegistryEvent) {

    }

    @Override
    public void beginRegistry(MemberRegistryData memberRegistryEvent) {

    }

    @Override
    public void endRegistry(MemberRegistryData memberRegistryEvent) {

    }

    @Override
    public void afterRegistry(MemberRegistryData memberRegistryEvent) {

    }

    @Override
    public void exceptionRegistry(MemberRegistryData memberRegistryEvent) {

    }
}
