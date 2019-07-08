/*
* acooly.cn Inc.
* Copyright (c) 2019 All Rights Reserved.
* create by acooly
* date:2019-03-05
*/
package com.acooly.module.countnum.dto.order;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;

import com.acooly.module.countnum.enums.CountNumIsCoverEnum;
import com.acooly.module.countnum.enums.CountNumTypeEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * 创建游戏
 * 
 * @author CuiFuQ
 *
 */
@Getter
@Setter
public class CreateCountNumGameDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	private CountNumTypeEnum type = CountNumTypeEnum.TIME_LIMIT;

	/** 过期时间 */
	private Date overdueTime;

	/**
	 * 是否参与覆盖
	 */
	private CountNumIsCoverEnum isCover = CountNumIsCoverEnum.NO;

	/** 最大参与人数(0:无限次) */
	private Long maxNum = 0L;

	/** 可参与次数(0:无限次) */
	private Long limitNum = 0L;

	/** 业务id */
	@Size(max = 64)
	private String businessId;

	/** 业务参数 */
	@Size(max = 255)
	private String businessData;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
