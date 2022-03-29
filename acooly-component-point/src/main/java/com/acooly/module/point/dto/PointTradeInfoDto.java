package com.acooly.module.point.dto;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;

/**
 * 积分交易信息dto
 *
 * @author cuifuqiang
 */
@Setter
@Getter
public class PointTradeInfoDto {

	/**
	 * 幂等性校验： 是否重复交易积分(判断纬度：userNo,busiId,busiType), 支持（积分产生，积分消费，积分冻结，积分解冻）
	 * <li>true: 支持重复业务属性，重复操作积分
	 * <li>false: 不支持重复业务操作积分
	 */
	private boolean repeatTrade = false;

	/**
	 * 相关业务Id
	 * <li>交易场景退款时，userNo,busiId,busiType 确定统计数据，进行扣减积分
	 */
	private String busiId;

	/**
	 * 参考配置文件 PointProperties.busiTypeEnumClassName 业务系统枚举：需要在积分规则中配置
	 */
	private String busiType;

	/**
	 * 参考配置文件 PointProperties.busiTypeEnumClassName 业务系统枚举：需要在积分规则中配置
	 */
	private String busiTypeText;

	/**
	 * 相关业务数据 *
	 */
	private String busiData;

	/**
	 * 备注 *
	 */
	private String comments;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
