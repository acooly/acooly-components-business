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
import com.acooly.module.member.entity.Member;

import java.util.Map;

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


    /**
     * 根据配置发送注册短信验证码时的模板数据扩展。
     * <p>
     * 默认组件以在数据中提供：username, captcha
     * 如果你的注册短信验证码模板新增了其他数据，则请实现该方法扩展.
     * 注册短信的模板配置：acooly.member.active.smsTemplateContent
     *
     * @param member
     * @param data
     */
    void onCaptchaSms(Member member, Map<String, Object> data);

    /**
     * 激活邮件模板数据扩展
     * <p>
     * 默认组件以在数据中提供：username, captcha
     * 注册邮件的模板配置：acooly.member.active.mailTemplateName
     * 这里配置的是/resources/mail/*.ftl的文件模板
     * 注意：这里的参数data为Map<String,String>是因为发送组件的限制，够用，谅解。
     *
     * @param member
     * @param data
     */
    void onCaptchaMail(Member member, Map<String, String> data);

}
