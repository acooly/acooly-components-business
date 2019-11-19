/*
* acooly.cn Inc.
* Copyright (c) 2019 All Rights Reserved.
* create by acooly
* date:2019-03-05
*/
package com.acooly.module.countnum.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

/**
 * 红包 Entity
 *
 * @author acooly Date: 2019-03-05 11:58:52
 */
@Getter
@Setter
public class CountNumGameOrderDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 计数游戏Id */
	@NotNull
	private Long countNumId;

	/** 计数游戏订单id */
	@NotNull
	private Long countNumOrderId;

	/** 名称 */
	@NotEmpty
	private String title;

	/** 订单号 */
	private String orderNo;

	/** 用户id */
	@NotNull
	private Long userId;

	/** 用户名 */
	@Size(max = 64)
	private String userName;

	/** 游戏有效值 **/
	private Long num;
	
	/** 游戏有效值 **/
	private Long time=0L;

	/** 创建时间 */
	private Date createTime;

	/**
	 * 用户参与的次数
	 */
	private Long joinNum = 0L;

	/**
	 * 有效值更新时间
	 */
	private Date validTime;

//	/** 扩展数据，字符串格式 **/
//	private String dataMapStr;

	/** 扩展数据，同步缓存-缓存列表(头像，昵称等) **/
	private Map<String, Object> dataMap;

//	public void setDataMap(Map<String, Object> dataMap) {
//		String dataMapStr = getDataMapStr();
//		if (Strings.isNotBlank(dataMapStr)) {
//			Map<String, Object> toDataMap = JSON.parseObject(dataMapStr);
//			this.dataMap = toDataMap;
//			return;
//		}
//		this.dataMap = dataMap;
//	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
