/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-03-13 17:05 创建
 */
package com.acooly.module.countnum;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author cuifuq
 */
@ConfigurationProperties(CountNumProperties.PREFIX)
@Data
public class CountNumProperties {
	public static final String PREFIX = "acooly.countnum";

	/** 是否启用 **/
	private boolean enable = true;

	/** 计数类游戏-分布式锁Key */
	private String countNumDistributedLockKey = "game_count_num_key";

	/** 计数类游戏-分布式 redis缓存时间(最少设置10分钟) */
	private Long countNumRedisTime = 10L;

	/** 计数类游戏-布式 redis缓存记录条数 */
	private Long countNumRedisOrderNum = 50L;

}
