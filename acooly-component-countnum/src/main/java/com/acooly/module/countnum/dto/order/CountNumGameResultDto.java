/*
* acooly.cn Inc.
* Copyright (c) 2019 All Rights Reserved.
* create by acooly
* date:2019-03-05
*/
package com.acooly.module.countnum.dto.order;

import java.io.Serializable;

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

	/** 游戏有效值 **/
	private Long num;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
