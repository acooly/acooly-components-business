/*
* acooly.cn Inc.
* Copyright (c) 2019 All Rights Reserved.
* create by acooly
* date:2019-03-05
*/
package com.acooly.module.redpack.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

/**
 * 红包 Entity
 *
 * @author acooly Date: 2019-03-05 11:58:52
 */
@Getter
@Setter
public class SendRedPackDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 红包id */
	@NotNull
	private Long redPackId;

	/** 用户id */
	@NotNull
	private Long userId;

	/** 用户名称 */
	@Size(max = 64)
	private String userName;
	
	/** 扩展数据，同步缓存-缓存列表(头像，昵称等) **/
	private Map<String, Object> dataMap;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
