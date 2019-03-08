/*
 * www.acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2018-07-26 17:46 创建
 */
package com.acooly.module.member.service.interceptor;

import com.acooly.core.common.exception.BusinessException;

/**
 * 注册相关的扩拦截展器
 * <p>
 * <li>集成系统可根据实际的需求，实现拦截器扩展注册相关的能力</li>
 * <li>集成系统在拦截方法中抛出异常（MemberOperationException）则终止注册</li>
 * <li>集成系统如果在拦截方法中使用异步处理则不影响主注册流程</li>
 *
 * @author zhangpu 2018-07-26 17:46
 */
public interface MemberRegistryInterceptor {

    /**
     * 开始注册时
     * 发布点：已完成参数和基本逻辑坚持，注册开始时
     *
     * @param memberRegistryData
     */
    void beginRegistry(MemberRegistryData memberRegistryData);


    /**
     * 完成注册时
     * <p>
     * 注册逻辑完成，但还未提交数据库时
     *
     * @param memberRegistryData
     */
    void endRegistry(MemberRegistryData memberRegistryData);


    /**
     * 完成注册后
     * <p>
     * 完成所有注册工作，并提交到数据库后
     *
     * @param memberRegistryData
     */
    void afterCommitRegistry(MemberRegistryData memberRegistryData);

    /**
     * 注册异常时
     *
     * @param memberRegistryData
     */
    void exceptionRegistry(MemberRegistryData memberRegistryData, BusinessException be);


}
