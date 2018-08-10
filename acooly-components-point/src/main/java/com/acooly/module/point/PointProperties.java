/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-03-13 17:05 创建
 */
package com.acooly.module.point;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author cuifuq
 */
@ConfigurationProperties(PointProperties.PREFIX)
@Data
public class PointProperties {
	public static final String PREFIX = "acooly.point";

	/** 是否启用 **/
	private boolean enable = true;

	/** 组件名称(积分，经验值等：默认名称：积分) */
	private String pointModuleName="积分";
}
