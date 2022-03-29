/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by cuifuqiang
 * date:2017-02-03
 */
package com.acooly.module.point.entity;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.module.point.enums.PointAccountStatus;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.persistence.*;

/**
 * 积分账户 Entity
 *
 * @author cuifuqiang Date: 2017-02-03 22:45:13
 */
@Setter
@Getter
@Entity
@Table(name = "pt_point_account")
public class PointAccount extends AbstractEntity {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/** ID */

	/**
	 * 用户号(系统唯一)
	 */
	private String userNo;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 积分总额
	 */
	private Long balance = 0l;
	/**
	 * 冻结
	 */
	private Long freeze = 0l;
	
	
	/**
	 * 负债积分账户 (（1.产生积分，2.积分取消）)
	 */
	private Long debtPoint = 0l;
	
	/**
	 * 可用余额
	 */
	@Transient
	private Long available = 0l;
	
	/**
	 * 真实处理总过期积分 *
	 */
	private Long totalOverduePoint = 0l;
	
	
	/**
	 * 累计总过期积分(累计值,非实际扣除积分)
	 */
	private Long countOverduePoint = 0l;
	
	
	/**
	 * 本次清零积分（积分清零时，才有返回值）
	 */
	@Transient
	private long cleanPoint = 0l;
	
	
	/**
	 * 总消费积分 *
	 */
	private Long totalExpensePoint = 0l;
	/**
	 * 总产生积分 =(积分总额+过期积分+消费总额)
	 */
	private Long totalProducePoint = 0l;
	
	/**
	 * 状态
	 */
	@Enumerated(EnumType.STRING)
	private PointAccountStatus status = PointAccountStatus.valid;
	/**
	 * 积分等级 *
	 */
	private Long gradeId;

	/**
	 * 等级图标
	 */
	@Transient
	private String gradePicture;

	/**
	 * 备注
	 */
	private String comments;

	public Long getAvailable() {
		return this.balance - this.freeze;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
