/*
* acooly.cn Inc.
* Copyright (c) 2019 All Rights Reserved.
* create by acooly
* date:2019-03-05
*/
package com.acooly.module.countnum.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;

import com.acooly.module.countnum.enums.CountNumTypeEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * 红包 Entity
 *
 * @author acooly Date: 2019-03-05 11:58:52
 */
@Getter
@Setter
public class CountNumGameOrderDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 计数游戏Id */
	@NotNull
	private Long countNumId;

	/** 计数游戏订单id */
	@NotNull
	private Long countNumOrderId;

	/** 名称 */
	@NotEmpty
	private String title;

	/** 订单号 */
	private String orderNo;

	/** 用户id */
	@NotNull
	private Long userId;

	/** 用户名 */
	@Size(max = 64)
	private String userName;

	/** 游戏有效值 **/
	private Long num;

	/** 创建时间 */
	private Date createTime;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}