/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-03-13 17:05 创建
 */
package com.acooly.module.redpack;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author cuifuq
 */
@ConfigurationProperties(RedPackProperties.PREFIX)
@Data
public class RedPackProperties {

    public static final String PREFIX = "acooly.redPack";

    /**
     * 是否启用
     **/
    private boolean enable = true;

    /**
     * 红包分布式锁Key
     */
    private String redPackDistributedLockKey = "red_pack_key";

    /**
     * 红包分布式 redis缓存时间(最少设置10分钟)
     */
    private Long redPackRedisTime = 10L;

    /**
     * 分布式部署 单个红包过期通知（不考虑单机部署）
     * <li>true: 通知所有服务器节点
     * <li>false：仅通知单服务器
     */
    private boolean isOverdueMoreNotify = true;

}
