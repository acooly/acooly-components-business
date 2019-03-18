package com.acooly.module.redpack.business.event.handle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.acooly.module.event.EventHandler;
import com.acooly.module.redpack.business.event.dto.RedPackOrderEvent;
import com.acooly.module.redpack.business.service.cache.RedPackCacheDataService;
import com.acooly.module.redpack.entity.RedPack;
import com.acooly.module.redpack.service.RedPackOrderService;
import com.acooly.module.redpack.service.RedPackService;

import lombok.extern.slf4j.Slf4j;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Invoke;

@Slf4j
@EventHandler
public class RedPackOrderEventHandle {

	@Autowired
	private RedPackService redPackService;

	@Autowired
	private RedPackOrderService redPackOrderService;

	@Autowired
	private RedPackCacheDataService redPackCacheDataService;

	// 异步事件处理器
	@Handler(delivery = Invoke.Asynchronously)
	@Transactional
	public void handleRedPackOrderEventAsyn(RedPackOrderEvent event) {

		RedPack redPack = redPackService.lockById(event.getRedPackId());

		if ((redPack.getTotalAmount() - redPack.getSendOutAmount() - redPack.getRefundAmount()) == 0) {
			redPackOrderService.updateIsFirst(event.getRedPackId());
		}
		redPackCacheDataService.setRedPackOrderRedisDataDelete(event.getRedPackId());
	}

}
