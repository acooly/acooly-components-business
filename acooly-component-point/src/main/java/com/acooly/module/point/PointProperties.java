/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-03-13 17:05 创建
 */
package com.acooly.module.point;

import java.util.Map;

import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.google.common.collect.Maps;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cuifuq
 */
@ConfigurationProperties(PointProperties.PREFIX)
@Data
@Slf4j
public class PointProperties {
	public static final String PREFIX = "acooly.point";

	/** 是否启用 **/
	private boolean enable = true;

	/** 组件名称(积分，经验值等：默认名称：积分) */
	private String pointModuleName = "积分";

	/**
	 * 积分账户等级-计算等级依据标识
	 * <li>totalPoint 用户总积分
	 * <li>balancePoint 用户余额积分
	 */
	private String pointGradeBasis = "totalPoint";

	/**
	 * 设置负债账户
	 * <li>true: 设置负债账户
	 * <li>false：不设置负债账户
	 * <li>场景描述：用户在平台商城购买商品后，产生了100个积分，在积分商城使用100积分兑换商品后并已收货，用户再平台商城退货，此时需要扣除用户100积分，及负债账户：100
	 */
	private boolean setDebt = false;

	/**
	 * 积分系统-业务系统业务扩充枚举
	 * <li>当业务系统定义的业务场景枚举无法覆盖实际场景，支持枚举扩充（例如：用户奖励，客服奖励等等）
	 * <li>格式：acooly.point.pointBusiTypeEnum[user_reward]=优秀用户奖励
	 */
	private Map<String, String> pointBusiTypeEnum = Maps.newHashMap();

	/**
	 * 业务系统定义的业务类型，
	 * <li>完整的java类路径，必须实现Messageable，请参考acooly相关文档, 重新toString方法[
	 * 格式如下：String.format("%s:%s", this.code, this.message);]
	 * <li>
	 * 
	 * <li>在积分系统场景应用：
	 * <li>1. 积分账户-发放积分，对应的业务场景
	 * <li>2. 配置积分规则，基于业务场景区分
	 * <li>格式：acooly.point.busiTypeEnumClassName=com.acooly.module.point.enums.PointBusinessTypeEnum
	 **/
	private String busiTypeEnumClassName;

	/**
	 * 解析业务配置的枚举类
	 * 
	 * @return
	 */
	public Map<String, String> getBusiTypeEnums() {
		Map<String, String> maps = Maps.newHashMap();
		String busiTypeClassName = getBusiTypeEnumClassName();
		if (Strings.isBlank(busiTypeClassName)) {
			return maps;
		}
		try {
			Class busiTypeEnumsClass = Class.forName(busiTypeClassName);
			Object[] enums = busiTypeEnumsClass.getEnumConstants();
			for (Object string : enums) {
				String[] enumString = string.toString().split(":");
				maps.put(enumString[0], enumString[1]);
			}
		} catch (Exception e) {
			log.info("积分组件[acooly-component-point]初始化业务枚举失败");
		}
		return maps;
	}

	public Map<String, String> getPointBusiTypeEnum() {
		String systemPointBusiTypeKey = "boss_give_out";

		// 加载配置文件：pointBusiTypeEnum
		Map<String, String> pointBusiTypes = pointBusiTypeEnum;

		// 业务系统枚举类
		pointBusiTypes.putAll(getBusiTypeEnums());
		// 系统内置
		pointBusiTypes.put(systemPointBusiTypeKey, "系统发放");

		return pointBusiTypes;
	}

}
