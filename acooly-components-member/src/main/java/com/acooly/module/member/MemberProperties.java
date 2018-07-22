/*
 * www.acooly.cn Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2016-10-27 23:31 创建
 */
package com.acooly.module.member;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhangpu
 * @date 2018-07-10
 */
@ConfigurationProperties(MemberProperties.PREFIX)
@Data
public class MemberProperties {

    public static final String PREFIX = "acooly.member";
    /**
     * 是否启用
     */
    private boolean enable = true;


    /**
     * 是否开启账务功能，同步开账户
     */
    private boolean accountEnable = true;

}
