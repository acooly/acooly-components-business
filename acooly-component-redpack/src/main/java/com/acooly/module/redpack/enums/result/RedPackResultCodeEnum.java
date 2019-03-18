/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by cuifuq
 * date:2019-03-05
 *
 */
package com.acooly.module.redpack.enums.result;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.acooly.core.utils.enums.Messageable;

/**
 * 红包返回码定义 枚举定义
 * 
 * @author cuifuq Date: 2019-03-05 18:18:09
 */
public enum RedPackResultCodeEnum implements Messageable {

	/** 红包不存在 **/
	RED_PACK_NOT_EXISTING("RED_PACK_NOT_EXISTING", "红包不存在"),

	/** 红包已经完结 **/
	RED_PACK_ALREADY_FINISH("RED_PACK_ALREADY_FINISH", "红包已经完结"),

	/** 红包退款中 **/
	RED_PACK_REFUNDING("RED_PACK_REFUNDING", "红包退款中"),

	/** 红包过期 */
	RED_PACK_OVERDUE("RED_PACK_OVERDUE", "红包过期"),

	/** 红包剩余总余额小于0 **/
	RED_PACK_SURPLUS_LESS_0("RED_PACK_SURPLUS_LESS_0", "红包剩余总余额小于0"),

	/** 红包剩余金额为0 **/
	RED_PACK_SURPLUS_EQUAL_0("RED_PACK_SURPLUS_EQUAL_0", "红包剩余金额为0"),

	/** 红包剩余个数为0 **/
	RED_PACK_NUM_EQUAL_0("RED_PACK_NUM_EQUAL_0", "红包剩余个数为0"),

	/** 红包总额不足,小于包括剩余个数 **/
	RED_PACK_NOT_ENOUGH_NUM("RED_PACK_NOT_ENOUGH_NUM", "红包总额不足,小于包括剩余个数"),

	/** 红包总额小于剩余金额 **/
	RED_PACK_NOT_ENOUGH_SURPLUS("RED_PACK_NOT_ENOUGH_SURPLUS", "红包总额小于剩余金额"),

	/** 红包剩余数量小于0 **/
	RED_PACK_NUM_LESS_0("RED_PACK_NUM_LESS_0", "红包剩余数量小于0"),

	/** 领取超过最大领取次数,(已经领取红包) **/
	RED_PACK_PASS_MAX_NUM("RED_PACK_PASS_MAX_NUM", "领取超过最大领取次数"),

	;

	private final String code;
	private final String message;

	private RedPackResultCodeEnum(String code, String message) {
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
		for (RedPackResultCodeEnum type : values()) {
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
	public static RedPackResultCodeEnum find(String code) {
		for (RedPackResultCodeEnum status : values()) {
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
	public static List<RedPackResultCodeEnum> getAll() {
		List<RedPackResultCodeEnum> list = new ArrayList<RedPackResultCodeEnum>();
		for (RedPackResultCodeEnum status : values()) {
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
		for (RedPackResultCodeEnum status : values()) {
			list.add(status.code());
		}
		return list;
	}

}
