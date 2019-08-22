/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by cuifuq
 * date:2019-07-03
 */
package com.acooly.module.countnum.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

import lombok.extern.slf4j.Slf4j;

/**
 * 游戏-计数用户订单 Service实现
 *
 * Date: 2019-07-03 11:49:00
 *
 * @author cuifuq
 *
 */
@Slf4j
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
	public CountNumOrder findByCountNumIdAndUserIdOne(long countNumId, long userId) {
		return getEntityDao().findByCountNumIdAndUserIdOne(countNumId, userId);
	}

	@Override
	public long countByCountId(long countNumId) {
		return getEntityDao().countByCountId(countNumId);
	}

	@Override
	@Transactional
	public CountNumOrder saveCountNumOrderResult(CountNumGameResultDto dto, CountNumGameDto countNumGameDto) {
		long userId = dto.getUserId();
		long countNumId = dto.getCountNumId();
		long currentNum = dto.getNum();
		CountNumTypeEnum type = countNumGameDto.getType();

		CountNumOrder order = getEntityDao().lockByUserIdAndCountId(userId, countNumId);
		if (order == null) {
			order = new CountNumOrder();
			order.setOrderNo(Ids.oid());
			order.setCountId(countNumId);
			order.setCountTitle(countNumGameDto.getTitle());
			order.setCountType(countNumGameDto.getType());
			order.setCreateUserId(countNumGameDto.getCreateUserId());
			order.setCreateUserName(countNumGameDto.getCreateUserName());
			order.setUserId(userId);
			order.setUserName(dto.getUserName());
			order.setNum(currentNum);
			getEntityDao().create(order);
		} else {
			long dbNum = order.getNum();
			// 时间限制----num 降序(由大到小)
			if (type == CountNumTypeEnum.TIME_LIMIT) {
				if (currentNum > dbNum) {
					order.setNum(currentNum);
					order.setValidTime(new Date());
				}
			}
			// 数量限制---num 升序(由小到大)
			if (type == CountNumTypeEnum.NUM_LIMIT) {
				if ((currentNum < dbNum)&&(currentNum>0)) {
					order.setNum(currentNum);
					order.setValidTime(new Date());
				}
			}
			// 参与次数
			order.setJoinNum(order.getJoinNum() + 1);
			getEntityDao().update(order);
		}
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
		event.setJoinNum(order.getJoinNum());
		event.setValidTime(order.getValidTime());
		eventBus.publishAfterTransactionCommitted(event);
	}

	@Override
	public CountNumGameOrderRankDto userRankByCountNumId(long userId, long countNumId, CountNumTypeEnum type) {
		if (type == CountNumTypeEnum.TIME_LIMIT) {
			return getEntityDao().userRankingByCountNumIdGroupNum(countNumId, userId);
		} else {
			return getEntityDao().userRankingByCountNumIdGroupTime(countNumId, userId);
		}
	}

}
