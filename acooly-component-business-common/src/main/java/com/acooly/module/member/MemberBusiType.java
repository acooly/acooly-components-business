/**
 * acooly-components-business
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2021-01-12 16:38
 */
package com.acooly.module.member;

/**
 * 会员业务分类
 * <p>
 * <li>1、用于集成系统自定义会员的业务分类。</li>
 *
 * @author zhangpu
 * @date 2021-01-12 16:38
 */
public interface MemberBusiType {

    /**
     * 编码
     *
     * @return
     */
    String getCode();

    /**
     * 名称
     *
     * @return
     */
    String getName();


}
