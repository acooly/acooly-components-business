/*
* acooly.cn Inc.
* Copyright (c) 2019 All Rights Reserved.
* create by acooly
* date:2019-03-05
*/
package com.acooly.module.redpack.business.event.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;

import com.acooly.module.redpack.enums.RedPackOrderStatusEnum;
import com.acooly.module.redpack.enums.RedPackOrderTypeEnum;
import com.acooly.module.redpack.enums.WhetherEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * 红包 Entity
 *
 * @author acooly Date: 2019-03-05 11:58:52
 */
@Getter
@Setter
public class RedPackOrderEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 红包id */
	@NotNull
	private Long redPackId;

	/** 红包订单id */
	@NotNull
	private Long redPackOrderId;

	/** 业务id */
	@Size(max = 64)
	private String businessId;

	/** 名称 */
	@NotEmpty
	private String title;

	/** 订单号 */
	private String orderNo;

	/** 接受用户id */
	@NotNull
	private Long userId;

	/** 接受用户名称 */
	@Size(max = 64)
	private String userName;

	/** 获取红包金额 */
	@Size(max = 64)
	private Long amount;

	/** 获取红包金额 */
	private WhetherEnum isFirst = WhetherEnum.NO;

	/** 类型 */
	private RedPackOrderTypeEnum type = RedPackOrderTypeEnum.RED_PACK;

	/** 状态 */
	private RedPackOrderStatusEnum status = RedPackOrderStatusEnum.INIT;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
