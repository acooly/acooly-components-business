package com.acooly.module.countnum.business.service.cache.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acooly.module.countnum.CountNumProperties;
import com.acooly.module.countnum.entity.CountNum;
import com.acooly.module.countnum.service.CountNumService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("countNumRedisListener")
public class CountNumRedisListener implements MessageListener {

	@Autowired
	private CountNumProperties countNumProperties;

	@Autowired
	private CountNumService countNumService;

	@Override
	@Transactional
	public void onMessage(Message message, byte[] pattern) {
		String redisKey = message.toString();
		String countNumKey = countNumProperties.getCountNumDistributedLockKey() + "_redis_lock_listener_";
		if (redisKey.contains(countNumKey)) {
			log.info("[计数游戏组件]即将要失效的Key:{}", redisKey);
			long countNumId = Long.parseLong(redisKey.replaceAll(countNumKey, ""));
			CountNum countNum = countNumService.countNumGameFinish(countNumId);
			countNumService.pushEvent(countNum);
		}
	}
}
