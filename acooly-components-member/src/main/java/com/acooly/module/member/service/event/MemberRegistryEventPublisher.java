/*
 * www.acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2018-07-26 17:46 创建
 */
package com.acooly.module.member.service.event;

/**
 * 注册相关的事件发布器
 *
 * @author zhangpu 2018-07-26 17:46
 */
public interface MemberRegistryEventPublisher {

    /**
     * 开始注册前
     * 发布点：进入注册方法，为做任何处理时，参数验证前。
     *
     * @param memberRegistryEvent
     */
    void before(MemberRegistryEvent memberRegistryEvent);

    /**
     * 开始注册时
     * 发布点：已完成参数和基本逻辑坚持，注册开始时
     *
     * @param memberRegistryEvent
     */
    void begin(MemberRegistryEvent memberRegistryEvent);


    /**
     * 完成注册时
     * <p>
     * 注册逻辑完成，但还未提交数据库时
     *
     * @param memberRegistryEvent
     */
    void end(MemberRegistryEvent memberRegistryEvent);


    /**
     * 完成注册后
     * <p>
     * 完成所有注册工作，并提交到数据库后
     *
     * @param memberRegistryEvent
     */
    void after(MemberRegistryEvent memberRegistryEvent);

    void exception(MemberRegistryEvent memberRegistryEvent);

}
