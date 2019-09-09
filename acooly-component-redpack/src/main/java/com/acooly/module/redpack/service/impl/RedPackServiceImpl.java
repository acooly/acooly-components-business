/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by cuifuq
 * date:2019-03-05
 */
package com.acooly.module.redpack.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.event.EventBus;
import com.acooly.module.redpack.business.event.dto.RedPackEvent;
import com.acooly.module.redpack.business.event.dto.RedPackOverdueEvent;
import com.acooly.module.redpack.dao.RedPackDao;
import com.acooly.module.redpack.entity.RedPack;
import com.acooly.module.redpack.enums.RedPackStatusEnum;
import com.acooly.module.redpack.service.RedPackService;

/**
 * 红包 Service实现
 *
 * Date: 2019-03-05 18:18:09
 *
 * @author cuifuq
 *
 */
@Service("redPackService")
public class RedPackServiceImpl extends EntityServiceImpl<RedPack, RedPackDao> implements RedPackService {

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	@Transactional
	public RedPack lockById(Long redPackId) {
		RedPack redPack = getEntityDao().lockById(redPackId);
		return redPack;
	}

	@Override
	@Transactional
	public void checkRedPackOverdue(RedPack redPack) {
		redPack = lockById(redPack.getId());
		RedPackStatusEnum status = redPack.getStatus();
		if (status == RedPackStatusEnum.PROCESSING || status == RedPackStatusEnum.INIT) {
			// 校验过期
			Date overdueDateTime = redPack.getOverdueTime();
			if (overdueDateTime != null) {
				Long overdueTime = redPack.getOverdueTime().getTime();
				Long currentTime = (new Date()).getTime();
				if (currentTime > overdueTime) {
					redPack.setStatus(RedPackStatusEnum.OVERDUE_SUCCESS);
					getEntityDao().update(redPack);
				}
			}
		}
	}

	@Override
	public void pushEvent(RedPack redPack) {
		RedPackEvent event = new RedPackEvent();
		event.setRedPackId(redPack.getId());
		event.setTitle(redPack.getTitle());
		event.setSendUserId(redPack.getSendUserId());
		event.setSendUserName(redPack.getSendUserName());
		event.setOverdueTime(redPack.getOverdueTime());
		event.setTotalAmount(redPack.getTotalAmount());
		event.setTotalNum(redPack.getTotalNum());
		event.setSendOutAmount(redPack.getSendOutAmount());
		event.setSendOutNum(redPack.getSendOutNum());
		event.setPartakeNum(redPack.getPartakeNum());
		event.setStatus(redPack.getStatus());
		event.setBusinessId(redPack.getBusinessId());

		EventBus eventBus = applicationContext.getBean(EventBus.class);
		eventBus.publishAfterTransactionCommitted(event);
	}

	@Override
	public void pushEventOverdue(RedPack redPack) {
		RedPackOverdueEvent event = new RedPackOverdueEvent();
		event.setRedPackId(redPack.getId());
		event.setOverdueTime(redPack.getOverdueTime());
		event.setBusinessId(redPack.getBusinessId());

		EventBus eventBus = applicationContext.getBean(EventBus.class);
		eventBus.publishAfterTransactionCommitted(event);
	}

}
