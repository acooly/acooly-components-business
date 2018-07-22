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
 * 会员个人信息 IncomeMonthEnum 枚举定义
 * 
 * @author acooly
 * Date: 2018-07-23 00:05:26
 */
public enum IncomeMonthEnum implements Messageable {

	below3k("below3k", "3000以下"),

	 bt3to5K(" bt3to5K", "3000-5000(含)"),

	bt5Kto1W("bt5Kto1W", "5000-10000(含)"),

	bt1Wto2W("bt1Wto2W", "10000-20000(含)"),

	bt2Wto5W("bt2Wto5W", "20000-50000"),

	above5W("above5W", "50000以上"),

	;

	private final String code;
	private final String message;

	private IncomeMonthEnum(String code, String message) {
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
		for (IncomeMonthEnum type : values()) {
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
	public static IncomeMonthEnum find(String code) {
		for (IncomeMonthEnum status : values()) {
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
	public static List<IncomeMonthEnum> getAll() {
		List<IncomeMonthEnum> list = new ArrayList<IncomeMonthEnum>();
		for (IncomeMonthEnum status : values()) {
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
		for (IncomeMonthEnum status : values()) {
			list.add(status.code());
		}
		return list;
	}

}
