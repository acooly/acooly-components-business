/*
* acooly.cn Inc.
* Copyright (c) 2019 All Rights Reserved.
* create by cuifuq
* date:2019-07-03
*/
package com.acooly.module.countnum.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.module.countnum.enums.CountNumTypeEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * 游戏-计数用户订单 Entity
 *
 * @author cuifuq Date: 2019-07-03 11:49:00
 */
@Entity
@Table(name = "game_count_num_order")
@Getter
@Setter
public class CountNumOrder extends AbstractEntity {

	/**
	 * 订单号
	 */
	@NotEmpty
	@Size(max = 64)
	private String orderNo;

	/**
	 * 游戏id
	 */
	@NotNull
	@Max(2147483646)
	private Long countId;

	/**
	 * 游戏名称
	 */
	@Size(max = 64)
	private String countTitle;

	/**
	 * 类型
	 */
	@Enumerated(EnumType.STRING)
	private CountNumTypeEnum countType;

	/**
	 * 创建者id
	 */
	@NotNull
	@Max(2147483646)
	private Long createUserId;

	/**
	 * 创建者用户名
	 */
	@Size(max = 64)
	private String createUserName;

	/**
	 * 用户id
	 */
	@NotNull
	private Long userId;

	/**
	 * 用户名称
	 */
	@Size(max = 64)
	private String userName;

	/**
	 * 有效值
	 */
	private Long num;

	/**
	 * 备注
	 */
	@Size(max = 64)
	private String comments;

	/**
	 * 用户参与活动的次数
	 */
	private Long joinNum = 1L;

	/**
	 * 活动成绩最终结果有效时间
	 */
	private Date validTime = new Date();

}
