/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by cuifuqiang
 * date:2017-02-03
 *
 */
package com.acooly.module.point.enums;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.acooly.core.utils.enums.Messageable;

/**
 * 积分规则类型
 *
 */
public enum PointRuleValidTypeTypeEnum implements Messageable {
	/**
	 * 有效一年
	 */
	only_one_year("only_one_year", "有效一年"),

	/**
	 * 次年年底
	 */
	next_year_over("next_year_over", "次年年底"),

	/**
	 * 无限期
	 */
	no_time_limit("no_time_limit", "无限期"),;

	private final String code;
	private final String message;

	private PointRuleValidTypeTypeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public static Map<String, String> mapping() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (PointRuleValidTypeTypeEnum type : values()) {
			map.put(type.getCode(), type.getMessage());
		}
		return map;
	}

	/**
	 * 通过枚举值码查找枚举值。
	 *
	 * @param code 查找枚举值的枚举值码。
	 * @return 枚举值码对应的枚举值。
	 * @throws IllegalArgumentException 如果 code 没有对应的 Status 。
	 */
	public static PointRuleValidTypeTypeEnum find(String code) {
		for (PointRuleValidTypeTypeEnum status : values()) {
			if (status.getCode().equals(code)) {
				return status;
			}
		}
		throw new IllegalArgumentException("TradeType not legal:" + code);
	}

	/**
	 * 获取全部枚举值。
	 *
	 * @return 全部枚举值。
	 */
	public static List<PointRuleValidTypeTypeEnum> getAll() {
		List<PointRuleValidTypeTypeEnum> list = new ArrayList<PointRuleValidTypeTypeEnum>();
		for (PointRuleValidTypeTypeEnum status : values()) {
			list.add(status);
		}
		return list;
	}

	/**
	 * 获取全部枚举值码。
	 *
	 * @return 全部枚举值码。
	 */
	public static List<String> getAllCode() {
		List<String> list = new ArrayList<String>();
		for (PointRuleValidTypeTypeEnum status : values()) {
			list.add(status.code());
		}
		return list;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String code() {
		return code;
	}

	public String message() {
		return message;
	}

	@Override
	public String toString() {
		return String.format("%s:%s", this.code, this.message);
	}
}
