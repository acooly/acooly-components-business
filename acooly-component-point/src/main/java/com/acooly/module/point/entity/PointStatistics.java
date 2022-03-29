/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by acooly
 * date:2017-03-13
 */
package com.acooly.module.point.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.module.point.enums.PointStaticsStatus;

import lombok.Getter;
import lombok.Setter;

/**
 * 积分统计 Entity
 *
 * @author acooly Date: 2017-03-13 11:51:10
 */
@Getter
@Setter
@Entity
@Table(name = "pt_point_statistics")
public class PointStatistics extends AbstractEntity {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户号
	 */
	private String userNo;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 统计条数
	 */
	private long num;

	/**
	 * 预计清零积分
	 */
	private long totalClearPoint = 0L;

	/**
	 * 清零前账户积分
	 */
	private long currentPoint = 0L;

	/**
	 * 清零前冻结积分
	 */
	private long currentFreezePoint = 0L;

	/**
	 * 清零前账户过期总额(真实执行)
	 */
	private long currentTotalOverduePoint = 0L;

	/**
	 * 清零前账户过期总额(统计)
	 */
	private long currentCountOverduePoint = 0L;

	/**
	 * 清零前账户消费总额
	 */
	private long currentTotalExpensePoint = 0L;

	/**
	 * 本次清零积分
	 */
	private long realClearPoint = 0L;

	/**
	 * 清零后总额
	 */
	private long endBalancePoint = 0L;

	/**
	 * 清零后账户过期总额
	 */
	private long endTotalOverduePoint = 0L;

	/**
	 * 状态
	 */
	@Enumerated(EnumType.STRING)
	private PointStaticsStatus status;

	/**
	 * 过期时间
	 */
	private String overdueDate;

	/**
	 * 备注
	 */
	private String comments;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
