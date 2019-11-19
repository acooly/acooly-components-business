/*
* acooly.cn Inc.
* Copyright (c) 2019 All Rights Reserved.
* create by acooly
* date:2019-03-05
*/
package com.acooly.module.countnum.dto.order;

import java.io.Serializable;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;

/**
 * 计数结果
 * 
 * @author CuiFuQ
 *
 */
@Getter
@Setter
public class CountNumGameResultDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 计数游戏Id */
	@NotNull
	private Long countNumId;

	/** 用户id */
	@NotNull
	private Long userId;

	/** 用户名 */
	@Size(max = 64)
	private String userName;

	/**
	 * 排序因子-计数有效值
	 * 
	 * <li>支持类型 NUM_DESC: num 降序(由大到小)
	 * <li>支持类型 NUM_ASC: num 升序(由小到大)
	 **/
	private Long num;

	/**
	 * 排序因子2-计数有效值
	 * 
	 * <li>支持类型 NUM_DESC_TIME_ASC: NUM 降序(由大到小)，TIME 升序(由小到大) 
	 **/
	private Long time = 0L;

	/** 扩展数据，同步缓存-缓存列表(头像，昵称等) **/
	private Map<String, Object> dataMap;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
