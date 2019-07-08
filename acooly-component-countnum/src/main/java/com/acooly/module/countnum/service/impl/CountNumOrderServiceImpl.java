/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by cuifuq
 * date:2019-07-03
 */
package com.acooly.module.countnum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.Ids;
import com.acooly.module.countnum.business.event.dto.CountNumOrderEvent;
import com.acooly.module.countnum.dao.CountNumOrderDao;
import com.acooly.module.countnum.dto.CountNumGameDto;
import com.acooly.module.countnum.dto.CountNumGameOrderRankDto;
import com.acooly.module.countnum.dto.order.CountNumGameResultDto;
import com.acooly.module.countnum.entity.CountNumOrder;
import com.acooly.module.countnum.enums.CountNumTypeEnum;
import com.acooly.module.countnum.service.CountNumOrderService;
import com.acooly.module.event.EventBus;

/**
 * 游戏-计数用户订单 Service实现
 *
 * Date: 2019-07-03 11:49:00
 *
 * @author cuifuq
 *
 */
@Service("countNumOrderService")
public class CountNumOrderServiceImpl extends EntityServiceImpl<CountNumOrder, CountNumOrderDao>
		implements CountNumOrderService {

	@Autowired
	private EventBus eventBus;

	@Override
	public List<CountNumOrder> findByCountNumIdAndLimit(long countNumId, long limitNum, CountNumTypeEnum type) {
		if (type == CountNumTypeEnum.NUM_LIMIT) {
			return getEntityDao().findByCountNumIdAndLimitByNum(countNumId, limitNum);
		} else {
			return getEntityDao().findByCountNumIdAndLimitByTime(countNumId, limitNum);
		}
	}

	@Override
	public List<CountNumOrder> findByCountNumIdAndUserId(long countNumId, long userId) {
		return getEntityDao().findByCountNumIdAndUserId(countNumId, userId);
	}

	@Override
	public long countByCountId(long countNumId) {
		return getEntityDao().countByCountId(countNumId);
	}

	@Override
	public CountNumOrder saveCountNumOrderResult(CountNumGameResultDto dto, CountNumGameDto countNumGameDto) {
		CountNumOrder order = new CountNumOrder();
		order.setOrderNo(Ids.oid());
		order.setCountId(dto.getCountNumId());
		order.setCountTitle(countNumGameDto.getTitle());
		order.setCountType(countNumGameDto.getType());
		order.setCreateUserId(countNumGameDto.getCreateUserId());
		order.setCreateUserName(countNumGameDto.getCreateUserName());
		order.setUserId(dto.getUserId());
		order.setUserName(dto.getUserName());
		order.setNum(dto.getNum());
		getEntityDao().create(order);
		return order;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void pushEvent(CountNumOrder order) {
		CountNumOrderEvent event = new CountNumOrderEvent();
		event.setCountNumId(order.getCountId());
		event.setCountNumOrderId(order.getId());
		event.setTitle(order.getCountTitle());
		event.setOrderNo(order.getOrderNo());
		event.setUserId(order.getUserId());
		event.setUserName(order.getUserName());
		event.setType(order.getCountType());
		event.setNum(order.getNum());
		event.setCreateTime(order.getCreateTime());
		eventBus.publishAfterTransactionCommitted(event);
	}

	@Override
	public CountNumGameOrderRankDto userRankByCountNumId(long userId, long countNumId, CountNumTypeEnum type) {
		if (type == CountNumTypeEnum.NUM_LIMIT) {
			return getEntityDao().userRankingByCountNumIdGroupNum(countNumId, userId);
		} else {
			return getEntityDao().userRankingByCountNumIdGroupTime(countNumId, userId);
		}
	}

}
