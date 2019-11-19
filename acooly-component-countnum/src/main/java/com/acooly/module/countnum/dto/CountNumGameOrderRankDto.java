/*
* acooly.cn Inc.
* Copyright (c) 2019 All Rights Reserved.
* create by acooly
* date:2019-03-05
*/
package com.acooly.module.countnum.dto;

import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.acooly.core.utils.Strings;
import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

/**
 * 红包 Entity
 *
 * @author acooly Date: 2019-03-05 11:58:52
 */
@Getter
@Setter
public class CountNumGameOrderRankDto extends CountNumGameOrderDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 用户排名 */
	@NotNull
	private Long rank = 0L;

	/** 用户排名比例（百分比，2位小数） */
	private String overstepRate = "0.00";

	/** 扩展数据，字符串格式 **/
	private String dataMapStr;

	@Override
	public Map<String, Object> getDataMap() {
		String dataMapStr = getDataMapStr();
		if (Strings.isNotBlank(dataMapStr)) {
			Map<String, Object> toDataMap = JSON.parseObject(dataMapStr);
			return toDataMap;
		}
		return super.getDataMap();
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
