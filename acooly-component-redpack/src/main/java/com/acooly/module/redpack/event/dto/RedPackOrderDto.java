/*
* acooly.cn Inc.
* Copyright (c) 2019 All Rights Reserved.
* create by acooly
* date:2019-03-05
*/
package com.acooly.module.redpack.event.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
public class RedPackOrderDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 红包id */
	@NotNull
	private Long redPackId;

	/** 接受用户id */
	@NotNull
	private Long userId;

	/** 接受用户名称 */
	private String userName;

	/** 获取红包金额 */
	private Long amount;

	/** 获取红包金额 */
	private WhetherEnum isFirst = WhetherEnum.NO;

	/** 类型 */
	private RedPackOrderTypeEnum type = RedPackOrderTypeEnum.RED_PACK;

	/** 扩展数据，同步缓存-缓存列表(头像，昵称等) **/
	private Map<String, Object> dataMap;

	/** 创建时间 */
	private Date createTime;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
