/*
* acooly.cn Inc.
* Copyright (c) 2019 All Rights Reserved.
* create by acooly
* date:2019-03-05
*/
package com.acooly.module.redpack.event.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import javax.validation.constraints.NotEmpty;

import com.acooly.module.redpack.enums.RedPackStatusEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * 红包 Entity
 *
 * @author acooly Date: 2019-03-05 11:58:52
 */
@Getter
@Setter
public class RedPackDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 红包id */
	@NotNull
	private Long redPackId;

	/** 名称 */
	@NotEmpty
	private String title;

	/** 发送用户id */
	@NotNull
	private Long sendUserId;

	/** 发送用户名称 */
	@Size(max = 64)
	private String sendUserName;

	/** 过期时间 */
	private Date overdueTime;

	/** 红包金额 */
	@NotNull
	private Long totalAmount = 0L;

	/** 红包退款金额 */
	@NotNull
	private Long refundAmount = 0L;

	/** 红包个数 */
	@NotNull
	private Long totalNum = 0L;

	/** 已经发送金额 */
	@NotNull
	private Long sendOutAmount = 0L;

	/** 已经发送数量 */
	@NotNull
	private Long sendOutNum = 0L;

	@NotNull
	/** 可以参与次数 */
	private Long partakeNum = 1l;

	/** 红包状态 */
	private RedPackStatusEnum status;

	/** 业务id */
	@Size(max = 64)
	private String businessId;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
