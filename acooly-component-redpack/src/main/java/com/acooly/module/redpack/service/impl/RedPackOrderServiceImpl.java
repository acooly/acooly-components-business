/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by cuifuq
 * date:2019-03-05
 */
package com.acooly.module.redpack.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.event.EventBus;
import com.acooly.module.redpack.business.event.dto.RedPackOrderEvent;
import com.acooly.module.redpack.dao.RedPackOrderDao;
import com.acooly.module.redpack.entity.RedPack;
import com.acooly.module.redpack.entity.RedPackOrder;
import com.acooly.module.redpack.enums.RedPackOrderStatusEnum;
import com.acooly.module.redpack.enums.RedPackOrderTypeEnum;
import com.acooly.module.redpack.service.RedPackOrderService;

/**
 * 红包订单 Service实现
 *
 * Date: 2019-03-05 18:18:09
 *
 * @author cuifuq
 *
 */
@Service("redPackOrderService")
public class RedPackOrderServiceImpl extends EntityServiceImpl<RedPackOrder, RedPackOrderDao>
		implements RedPackOrderService {

	@Autowired
	private EventBus eventBus;

	@Override
	@Transactional
	public RedPackOrder lockById(long id) {
		return getEntityDao().lockById(id);
	}

	@Override
	public List<RedPackOrder> findByRedPackId(Long redPackId) {
		return getEntityDao().findByRedPackId(redPackId);
	}

	@Override
	public List<RedPackOrder> findByRedPackIdAndUserId(Long redPackId, Long userId) {
		return getEntityDao().findByRedPackIdAndUserId(redPackId, userId);
	}

	@Override
	public void updateIsFirst(Long redPackId) {
		Long id = getEntityDao().findByRedPackMaxId(redPackId);
		if (id != null) {
			getEntityDao().updateIsFirst(id);
		}
	}

	@Override
	public void pushEvent(RedPack redPack, RedPackOrder redPackOrder) {
		RedPackOrderEvent sendRedPackEvent = new RedPackOrderEvent();

		sendRedPackEvent.setRedPackId(redPack.getId());
		sendRedPackEvent.setTitle(redPack.getTitle());
		sendRedPackEvent.setType(redPackOrder.getType());
		sendRedPackEvent.setStatus(redPackOrder.getStatus());
		sendRedPackEvent.setBusinessId(redPack.getBusinessId());

		sendRedPackEvent.setRedPackOrderId(redPackOrder.getId());
		sendRedPackEvent.setOrderNo(redPackOrder.getOrderNo());
		sendRedPackEvent.setUserId(redPackOrder.getUserId());
		sendRedPackEvent.setUserName(redPackOrder.getUserName());
		sendRedPackEvent.setAmount(redPackOrder.getAmount());
		sendRedPackEvent.setType(redPackOrder.getType());
		sendRedPackEvent.setIsFirst(redPackOrder.getIsFirst());
		sendRedPackEvent.setCreateTime(redPackOrder.getCreateTime());
		eventBus.publishAfterTransactionCommitted(sendRedPackEvent);
	}

	@Override
	public Long countRedPackByRedPackIdAndStatusAndType(Long redPackId, RedPackOrderStatusEnum status,
			RedPackOrderTypeEnum type) {
		long count = getEntityDao().countRedPackByRedPackIdAndStatusAndType(redPackId, status.code(), type.code());
		return count;
	}

	@Override
	public Long sumRedPackByRedPackIdAndStatus(Long redPackId, RedPackOrderStatusEnum status) {
		long sum = getEntityDao().sumRedPackByRedPackIdAndStatus(redPackId, status.code());
		return sum;
	}

	@Override
	public Long coutRedOrderNum(long userId, long redPackId) {
		return getEntityDao().coutRedOrderNum(userId, redPackId);
	}

	@Override
	public Long sumRedPackByRedPackIdAndStatusNotId(Long redPackOrderId, Long redPackId,
			RedPackOrderStatusEnum status) {
		return getEntityDao().sumRedPackByRedPackIdAndStatusNotId(redPackOrderId, redPackId, status.code());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void newUpdate(Long redPackOrderId) {
		RedPackOrder redPackOrder = lockById(redPackOrderId);
		redPackOrder.setStatus(RedPackOrderStatusEnum.SUCCESS);
		getEntityDao().update(redPackOrder);
	}

}
