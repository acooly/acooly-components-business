/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-07-31
 *
 */
package com.acooly.module.member.enums;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.acooly.core.utils.enums.Messageable;

/**
 * 企业客户实名认证 HoldingEnumEnum 枚举定义
 * 
 * @author acooly
 * Date: 2018-07-31 20:01:45
 */
public enum HoldingEnumEnum implements Messageable {

	PERSON("PERSON", "个人"),

	BUSINESS("BUSINESS", "企业用户"),

	INDIVIDUAL("INDIVIDUAL", "个体户"),

	MIDDLE_ACCOUNT("MIDDLE_ACCOUNT", "中间账户"),

	OPERATOR("OPERATOR", "运营商"),

	;

	private final String code;
	private final String message;

	private HoldingEnumEnum(String code, String message) {
		this.code = code;
		this.message = message;
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

	public static Map<String, String> mapping() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (HoldingEnumEnum type : values()) {
			map.put(type.getCode(), type.getMessage());
		}
		return map;
	}

	/**
	 * 通过枚举值码查找枚举值。
	 * 
	 * @param code
	 *            查找枚举值的枚举值码。
	 * @return 枚举值码对应的枚举值。
	 * @throws IllegalArgumentException
	 *             如果 code 没有对应的 Status 。
	 */
	public static HoldingEnumEnum find(String code) {
		for (HoldingEnumEnum status : values()) {
			if (status.getCode().equals(code)) {
				return status;
			}
		}
		return null;
	}

	/**
	 * 获取全部枚举值。
	 * 
	 * @return 全部枚举值。
	 */
	public static List<HoldingEnumEnum> getAll() {
		List<HoldingEnumEnum> list = new ArrayList<HoldingEnumEnum>();
		for (HoldingEnumEnum status : values()) {
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
		for (HoldingEnumEnum status : values()) {
			list.add(status.code());
		}
		return list;
	}

}
