/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by cuifuq
 * date:2019-03-05
 *
 */
package com.acooly.module.countnum.enums.result;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.acooly.core.utils.enums.Messageable;

/**
 * 游戏返回码定义 枚举定义
 * 
 * @author cuifuq Date: 2019-03-05 18:18:09
 */
public enum CountNumGameResultCodeEnum implements Messageable {

	/** 创建游戏失败 **/
	COUNT_NUM_CREATE_ERROR("COUNT_NUM_CREATE_ERROR", "创建游戏失败"),

	/** 游戏不存在 **/
	COUNT_NUM_NOT_EXISTING("COUNT_NUM_NOT_EXISTING", "游戏不存在"),

	/** 游戏已经完结 **/
	COUNT_NUM_ALREADY_FINISH("COUNT_NUM_ALREADY_FINISH", "游戏已结束"),

	/** 超过参与游戏次数 **/
	COUNT_NUM_PLAY_LIMIT_ALREADY("COUNT_NUM_PLAY_LIMIT_ALREADY", "超过参与游戏次数"),

	/** 游戏过期 */
	COUNT_NUM_OVERDUE("COUNT_NUM_OVERDUE", "游戏过期"),

	/** 超过游戏参与最大数量 **/
	COUNT_NUM_MAX_NUM("COUNT_NUM_MAX_NUM", "超过游戏参与最大数量"),

	;

	private final String code;
	private final String message;

	private CountNumGameResultCodeEnum(String code, String message) {
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
		for (CountNumGameResultCodeEnum type : values()) {
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
	public static CountNumGameResultCodeEnum find(String code) {
		for (CountNumGameResultCodeEnum status : values()) {
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
	public static List<CountNumGameResultCodeEnum> getAll() {
		List<CountNumGameResultCodeEnum> list = new ArrayList<CountNumGameResultCodeEnum>();
		for (CountNumGameResultCodeEnum status : values()) {
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
		for (CountNumGameResultCodeEnum status : values()) {
			list.add(status.code());
		}
		return list;
	}

}
