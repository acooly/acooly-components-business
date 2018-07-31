/*
 * www.acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2018-07-27 14:04 创建
 */
package com.acooly.module.member.service;

import com.acooly.module.member.dto.MemberRealNameInfo;

/**
 * @author zhangpu 2018-07-27 14:04
 */
public interface MemberRealNameService {

    /**
     * 会员实名认证
     * <p>
     * 根据会员系统已提交的认证信息进行认证。
     * 能力依赖：实名认证组件
     *
     * @param id
     */
    void verify(Long id);


    /**
     * 实名认证
     * <p>
     * 根据提交的信息进行认证，并写入会员系统
     * 能力依赖：实名认证组件
     *
     * @param memberRealNameInfo
     */
    void verify(MemberRealNameInfo memberRealNameInfo);


    /**
     * 保存认证信息
     * <p>
     * 该方法用于根据传入的认证信息不进行认证，直接保存。
     * 主要应对场景是外部其他功能已完成实名认证后（如：外部绑卡4要素认证通过，则实名直接通过），直接写入会员组件
     *
     * @param memberRealNameInfo
     */
    void saveVerify(MemberRealNameInfo memberRealNameInfo);
}
