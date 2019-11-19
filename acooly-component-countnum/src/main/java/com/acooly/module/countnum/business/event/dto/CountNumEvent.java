/*
* acooly.cn Inc.
* Copyright (c) 2019 All Rights Reserved.
* create by acooly
* date:2019-03-05
*/
package com.acooly.module.countnum.business.event.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;

import com.acooly.module.countnum.enums.CountNumStatusEnum;
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
public class CountNumEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 红包id */
	@NotNull
	private Long countNumId;

	/** 名称 */
	@NotEmpty
	private String title;

	/** 发送用户id */
	@NotNull
	private Long createUserId;

	/** 发送用户名称 */
	@Size(max = 64)
	private String createUserName;

	/** 类型 **/
	private CountNumTypeEnum type = CountNumTypeEnum.NUM_DESC;

	/** 过期时间 */
	private Date overdueTime;

	/** 计数游戏状态 */
	private CountNumStatusEnum status;

	/** 业务id */
	@Size(max = 64)
	private String businessId;


	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
