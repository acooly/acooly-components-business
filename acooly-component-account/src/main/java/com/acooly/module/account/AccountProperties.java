/*
 * www.acooly.cn Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2016-10-27 23:31 创建
 */
package com.acooly.module.account;

import lombok.Data;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.acooly.module.security.domain.User;
import com.google.common.collect.Maps;

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
	private int batchMaxSize = 600;

	/**
	 * 是否验证交易的bizOrderNo
	 */
	private boolean checkBizOrderNo = false;

	/**
	 * 是否开启账务分析(待实现)
	 */
	private boolean analyseEnable = false;

	/**
	 * 账户类型(仅作为列表关系映射)
	 */
	private Map<String, String> accountType = Maps.newLinkedHashMap();

	/**
	 * 初始化
	 */
	public AccountProperties() {
		accountType.put("main", "主账号");
	}

}
