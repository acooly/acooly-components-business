/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by cuifuq
 * date:2019-07-03
 *
 */
package com.acooly.module.countnum.service;

import java.util.List;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.countnum.dto.CountNumGameDto;
import com.acooly.module.countnum.dto.CountNumGameOrderRankDto;
import com.acooly.module.countnum.dto.order.CountNumGameResultDto;
import com.acooly.module.countnum.entity.CountNumOrder;
import com.acooly.module.countnum.enums.CountNumTypeEnum;

/**
 * 游戏-计数用户订单 Service接口
 *
 * Date: 2019-07-03 11:48:59
 * 
 * @author cuifuq
 *
 */
public interface CountNumOrderService extends EntityService<CountNumOrder> {

	List<CountNumOrder> findByCountNumIdAndLimit(long countNumId, long limitNum, CountNumTypeEnum type);

	List<CountNumOrder> findByCountNumIdAndUserId(long countNumId, long userId);

	CountNumOrder findByCountNumIdAndUserIdOne(long countNumId, long userId);

	long countByCountId(long countNumId);

	/**
	 * 保存更新用户成绩
	 * 
	 * @param dto
	 * @param countNumGameDto
	 * @return
	 */
	CountNumOrder saveCountNumOrderResult(CountNumGameResultDto dto, CountNumGameDto countNumGameDto);

	/**
	 * 发送事件
	 * 
	 * @param order
	 */
	public void pushEvent(CountNumOrder order);

	/**
	 * 用户排名
	 * 
	 * @param countNumId
	 * @return
	 */
	CountNumGameOrderRankDto userRankByCountNumId(long userId, long countNumId, CountNumTypeEnum type);
}
