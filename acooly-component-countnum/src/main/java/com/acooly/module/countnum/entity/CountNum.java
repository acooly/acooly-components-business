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
import com.acooly.module.countnum.enums.CountNumIsCoverEnum;
import com.acooly.module.countnum.enums.CountNumStatusEnum;
import com.acooly.module.countnum.enums.CountNumTypeEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * 游戏-计数 Entity
 *
 * @author cuifuq Date: 2019-07-03 11:48:59
 */
@Entity
@Table(name = "game_count_num")
@Getter
@Setter
public class CountNum extends AbstractEntity {

	/**
	 * 名称
	 */
	@NotEmpty
	@Size(max = 64)
	private String title;

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
	 * 过期时间
	 */
	private Date overdueTime;

	/**
	 * 类型
	 */
	@Enumerated(EnumType.STRING)
	private CountNumTypeEnum type = CountNumTypeEnum.TIME_LIMIT;

	/**
	 * 状态
	 */
	@Enumerated(EnumType.STRING)
	@NotNull
	private CountNumStatusEnum status = CountNumStatusEnum.INIT;


	/**
	 * 最大参与人数
	 */
	@Max(2147483646)
	private Long maxNum = 0L;

	/**
	 * 可参与次数
	 */
	@Max(2147483646)
	private Long limitNum = 0L;

	/**
	 * 业务id
	 */
	@Size(max = 64)
	private String businessId;

	/**
	 * 业务参数
	 */
	@Size(max = 255)
	private String businessData;

	/**
	 * 备注
	 */
	@Size(max = 64)
	private String comments;

}
