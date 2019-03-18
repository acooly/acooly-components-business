/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by cuifuq
 * date:2019-03-05
 *
 */
package com.acooly.module.redpack.enums;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.acooly.core.utils.enums.Messageable;

/**
 * 红包 RedPackStatusEnum 枚举定义
 * 
 * @author cuifuq Date: 2019-03-05 18:18:08
 */
public enum RedPackStatusEnum implements Messageable {

	INIT("INIT", "初始化"),

	PROCESSING("PROCESSING", "发送中"),

	REFUNDING("REFUNDING", "退款中"),

	REFUND_SUCCESS("REFUND_SUCCESS", "退款完结"),

	OVERDUE_SUCCESS("OVERDUE_SUCCESS", "过期完结"),

	SUCCESS("SUCCESS", "正常完结"),

	;

	private final String code;
	private final String message;

	private RedPackStatusEnum(String code, String message) {
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
		for (RedPackStatusEnum type : values()) {
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
	public static RedPackStatusEnum find(String code) {
		for (RedPackStatusEnum status : values()) {
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
	public static List<RedPackStatusEnum> getAll() {
		List<RedPackStatusEnum> list = new ArrayList<RedPackStatusEnum>();
		for (RedPackStatusEnum status : values()) {
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
		for (RedPackStatusEnum status : values()) {
			list.add(status.code());
		}
		return list;
	}

}
