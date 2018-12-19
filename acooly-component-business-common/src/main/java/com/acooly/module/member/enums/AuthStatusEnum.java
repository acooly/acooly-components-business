/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-10-16
 *
 */
package com.acooly.module.member.enums;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.acooly.core.utils.enums.Messageable;

/**
 * b_member_auth AuthStatusEnum 枚举定义
 * 
 * @author acooly
 * Date: 2018-10-16 15:57:37
 */
public enum AuthStatusEnum implements Messageable {

	enable("enable", "有效"),

	invalid_expired("invalid_expired", "过期"),

	invalid_locked("invalid_locked", "锁定"),

	invalid_disabled("invalid_disabled", "禁用");

	private final String code;
	private final String message;

	private AuthStatusEnum(String code, String message) {
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
		for (AuthStatusEnum type : values()) {
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
	public static AuthStatusEnum find(String code) {
		for (AuthStatusEnum status : values()) {
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
	public static List<AuthStatusEnum> getAll() {
		List<AuthStatusEnum> list = new ArrayList<AuthStatusEnum>();
		for (AuthStatusEnum status : values()) {
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
		for (AuthStatusEnum status : values()) {
			list.add(status.code());
		}
		return list;
	}

}
