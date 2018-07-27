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
     *
     * @param id
     */
    void verify(Long id);


    /**
     * 实名认证
     * <p>
     * 根据提交的信息进行认证，并写入会员系统
     *
     * @param memberRealNameInfo
     */
    void verify(MemberRealNameInfo memberRealNameInfo);
}
