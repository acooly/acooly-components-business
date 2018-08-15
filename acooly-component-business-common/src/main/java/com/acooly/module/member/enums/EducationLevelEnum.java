/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-07-23
 *
 */
package com.acooly.module.member.enums;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.acooly.core.utils.enums.Messageable;

/**
 * 会员个人信息 EducationLevelEnum 枚举定义
 * 
 * @author acooly
 * Date: 2018-07-23 00:05:26
 */
public enum EducationLevelEnum implements Messageable {

	primary("primary", "小学"),

	middle("middle", "中学"),

	college("college", "大专"),

	university("university", "本科"),

	master("master", "硕士"),

	doctor("doctor", "博士"),

	other("other", "其他"),

	;

	private final String code;
	private final String message;

	private EducationLevelEnum(String code, String message) {
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
		for (EducationLevelEnum type : values()) {
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
	public static EducationLevelEnum find(String code) {
		for (EducationLevelEnum status : values()) {
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
	public static List<EducationLevelEnum> getAll() {
		List<EducationLevelEnum> list = new ArrayList<EducationLevelEnum>();
		for (EducationLevelEnum status : values()) {
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
		for (EducationLevelEnum status : values()) {
			list.add(status.code());
		}
		return list;
	}

}
