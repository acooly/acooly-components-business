package com.acooly.module.redpack.business.event.handle;

import org.springframework.beans.factory.annotation.Autowired;

import com.acooly.module.event.EventHandler;
import com.acooly.module.redpack.business.event.dto.RedPackEvent;
import com.acooly.module.redpack.service.RedPackOrderService;
import com.acooly.module.redpack.service.RedPackService;

import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Invoke;

@EventHandler
public class RedPackEventHandle {

	@Autowired
	private RedPackService redPackService;

	@Autowired
	private RedPackOrderService redPackOrderService;

	// 异步事件处理器
	@Handler(delivery = Invoke.Asynchronously)
	public void handleRedPackEventAsyn(RedPackEvent event) {

	}

}
