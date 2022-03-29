/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by cuifuqiang
 * date:2017-02-03
 */
package com.acooly.module.point.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.module.point.enums.PointTradeType;

import lombok.Getter;
import lombok.Setter;

/**
 * 积分交易信息 Entity
 *
 * @author cuifuqiang Date: 2017-02-03 22:50:14
 */
@Setter
@Getter
@Entity
@Table(name = "pt_point_trade")
public class PointTrade extends AbstractEntity {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/** id */
	/**
	 * 交易订单号
	 */
	private String tradeNo;
	/**
	 * 交易类型
	 */
	@Enumerated(EnumType.STRING)
	private PointTradeType tradeType;
	/**
	 * 用户号
	 */
	private String userNo;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 积分账户ID
	 */
	private Long accountId;
	/**
	 * 交易积分
	 */
	private Long amount = 0l;
	/**
	 * 交易后冻结积分
	 */
	private Long endFreeze = 0l;
	/**
	 * 交易后积分
	 */
	private Long endBalance = 0l;
	/**
	 * 交易后有效积分
	 */
	private Long endAvailable = 0l;

	/**
	 * 交易后负债积分账户
	 */
	private Long endDebtPoint = 0l;

	/**
	 * 过期时间
	 */
	private String overdueDate;

	/**
	 * 相关业务Id *
	 */
	private String busiId;
	/**
	 * 相关业务类型 *
	 */
	private String busiType;
	/**
	 * 相关业务类型描述 *
	 */
	private String busiTypeText;
	/**
	 * 相关业务数据 *
	 */
	private String busiData;

	/**
	 * 备注
	 */
	private String comments;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
