/*
* acooly.cn Inc.
* Copyright (c) 2019 All Rights Reserved.
* create by cuifuq
* date:2019-03-05
*/
package com.acooly.module.redpack.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.module.redpack.enums.RedPackOrderStatusEnum;
import com.acooly.module.redpack.enums.RedPackOrderTypeEnum;
import com.acooly.module.redpack.enums.WhetherEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * 红包订单 Entity
 *
 * @author cuifuq Date: 2019-03-05 18:18:09
 */
@Entity
@Table(name = "red_red_pack_order")
@Getter
@Setter
public class RedPackOrder extends AbstractEntity {

	/** 订单号 */
	@NotEmpty
	@Size(max = 64)
	private String orderNo;

	/** 红包id */
	@NotNull
	private Long redPackId;

	/** 红包名称 */
	@Size(max = 64)
	private String redPackTitle;

	/** 红包发送者id */
	private Long sendUserId;

	/** 红包发送者名称 */
	@Size(max = 64)
	private String sendUserName;

	/** 用户id */
	@NotNull
	private Long userId;

	/** 用户名称 */
	@Size(max = 64)
	private String userName;

	/** 是否第一 */
	@Size(max = 64)
	@Enumerated(EnumType.STRING)
	private WhetherEnum isFirst = WhetherEnum.NO;

	/** 类型 */
	@Enumerated(EnumType.STRING)
	private RedPackOrderTypeEnum type = RedPackOrderTypeEnum.RED_PACK;

	/** 状态 */
	@Enumerated(EnumType.STRING)
	private RedPackOrderStatusEnum status = RedPackOrderStatusEnum.INIT;

	/** 金额 */
	private Long amount = 0l;

	/** 扩展数据，同步缓存-缓存列表(头像，昵称等) */
	private String dataMap;

	/** 备注 */
	@Size(max = 64)
	private String comments;

}
