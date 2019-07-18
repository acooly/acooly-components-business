package com.acooly.module.countnum.business.event.handle;

import org.springframework.transaction.annotation.Transactional;

import com.acooly.module.countnum.business.event.dto.CountNumEvent;
import com.acooly.module.event.EventHandler;

import lombok.extern.slf4j.Slf4j;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Invoke;

@Slf4j
@EventHandler
public class CountNumEventHandle {

	// 异步事件处理器
	@Handler(delivery = Invoke.Asynchronously)
	@Transactional
	public void handleCountNumEventAsyn(CountNumEvent event) {
		log.info("[计数游戏组件]CountNumEvent异步事件处理:{}", event);
	}

}
