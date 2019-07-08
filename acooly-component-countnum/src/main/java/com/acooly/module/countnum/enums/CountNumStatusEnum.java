/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by cuifuq
 * date:2019-07-03
 *
 */
package com.acooly.module.countnum.enums;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.acooly.core.utils.enums.Messageable;

/**
 * 游戏-计数 CountNumStatusEnum 枚举定义
 * 
 * @author cuifuq
 * Date: 2019-07-03 11:48:59
 */
public enum CountNumStatusEnum implements Messageable {

	INIT("INIT", "初始化"),
	PROCESSING("PROCESSING", "游戏中"),
	FINISH("FINISH", "结束"),
	;

	private final String code;
	private final String message;

	private CountNumStatusEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}


	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String code() {
		return code;
	}

	@Override
	public String message() {
		return message;
	}

	public static Map<String, String> mapping() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (CountNumStatusEnum type : values()) {
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
	public static CountNumStatusEnum find(String code) {
		for (CountNumStatusEnum status : values()) {
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
	public static List<CountNumStatusEnum> getAll() {
		List<CountNumStatusEnum> list = new ArrayList<CountNumStatusEnum>();
		for (CountNumStatusEnum status : values()) {
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
		for (CountNumStatusEnum status : values()) {
			list.add(status.code());
		}
		return list;
	}

}
