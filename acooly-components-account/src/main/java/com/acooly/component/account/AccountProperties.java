/*
 * www.acooly.cn Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2016-10-27 23:31 创建
 */
package com.acooly.component.account;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhangpu
 * @date 2018-07-10
 */
@ConfigurationProperties(AccountProperties.PREFIX)
@Data
public class AccountProperties {

    public static final String PREFIX = "acooly.account";
    /**
     * 是否启用
     */
    private boolean enable = true;

    /**
     * 批量处理最大值
     */
    private int batchMaxSize = 300;

}
