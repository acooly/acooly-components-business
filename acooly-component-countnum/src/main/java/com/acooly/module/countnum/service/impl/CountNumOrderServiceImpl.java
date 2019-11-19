/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by cuifuq
 * date:2019-07-03
 */
package com.acooly.module.countnum.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.acooly.module.countnum.enums.CountNumSortEnum;
import com.acooly.module.countnum.enums.CountNumTypeEnum;
import com.acooly.module.countnum.service.CountNumOrderService;
import com.acooly.module.event.EventBus;
import com.alibaba.fastjson.JSON;

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
		if (type == CountNumTypeEnum.NUM_DESC) {
			return getEntityDao().findByCountNumIdAndByNumSort(countNumId, limitNum, CountNumSortEnum.DESC.code());
		}

		if (type == CountNumTypeEnum.NUM_ASC) {
			return getEntityDao().findByCountNumIdAndByNumSort(countNumId, limitNum, CountNumSortEnum.ASC.code());
		}

		if (type == CountNumTypeEnum.NUM_DESC_TIME_ASC) {
			return getEntityDao().findByCountNumIdAndByNumTimeSort(countNumId, limitNum, CountNumSortEnum.DESC.code(),
					CountNumSortEnum.ASC.code());
		}

		return null;
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
		long currentTime = dto.getTime();
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
			order.setTime(currentTime);

			Map<String, Object> dataMapStr = dto.getDataMap();
			if (dataMapStr != null && dataMapStr.size() > 0) {
				order.setDataMap(JSON.toJSONString(dataMapStr));
			}

			getEntityDao().create(order);
		} else {
			long dbNum = order.getNum();
			long dbTime = order.getTime();
			// 时间限制----num 降序(由大到小)
			if (type == CountNumTypeEnum.NUM_DESC) {
				if (currentNum > dbNum) {
					order.setNum(currentNum);
					order.setValidTime(new Date());
				}
			}

			// 数量限制---num 升序(由小到大)
			if (type == CountNumTypeEnum.NUM_ASC) {
				if ((currentNum < dbNum) && (currentNum > 0)) {
					order.setNum(currentNum);
					order.setValidTime(new Date());
				}
			}

			// 数量限制---NUM 降序(由大到小)，TIME 升序(由小到大)
			if (type == CountNumTypeEnum.NUM_DESC_TIME_ASC) {
				if ((currentNum > dbNum) && (currentNum > 0) && (currentTime < dbTime) && (currentTime > 0)) {
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
		event.setTime(order.getTime());
		event.setCreateTime(order.getCreateTime());
		event.setJoinNum(order.getJoinNum());
		event.setValidTime(order.getValidTime());
		eventBus.publishAfterTransactionCommitted(event);
	}

	@Override
	public CountNumGameOrderRankDto userRankByCountNumId(long userId, long countNumId, CountNumTypeEnum type) {
		if (type == CountNumTypeEnum.NUM_DESC) {
			return getEntityDao().userRankingByCountNumIdGroupNumSort(countNumId, userId, CountNumSortEnum.DESC.code());
		}
		if (type == CountNumTypeEnum.NUM_ASC) {
			return getEntityDao().userRankingByCountNumIdGroupNumSort(countNumId, userId, CountNumSortEnum.ASC.code());
		}
		if (type == CountNumTypeEnum.NUM_DESC_TIME_ASC) {
			return getEntityDao().userRankingByCountNumIdGroupNumTimeSort(countNumId, userId,
					CountNumSortEnum.DESC.code(), CountNumSortEnum.ASC.code());
		}
		return null;
	}

}
