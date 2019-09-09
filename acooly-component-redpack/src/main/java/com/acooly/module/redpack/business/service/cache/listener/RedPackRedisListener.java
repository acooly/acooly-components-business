package com.acooly.module.redpack.business.service.cache.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acooly.module.redpack.RedPackProperties;
import com.acooly.module.redpack.entity.RedPack;
import com.acooly.module.redpack.service.RedPackService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("redPackRedisListener")
public class RedPackRedisListener implements MessageListener {

	@Autowired
	private RedPackProperties redPackProperties;

	@Autowired
	private RedPackService redPackService;

	@Override
	@Transactional
	public void onMessage(Message message, byte[] pattern) {
		String redisKey = message.toString();
		String redPackKey = redPackProperties.getRedPackDistributedLockKey() + "_redis_lock_listener_";
		if (redisKey.contains(redPackKey)) {
			log.info("[红包组件]即将要失效的Key:{}", redisKey);
			long redPackId = Long.parseLong(redisKey.replaceAll(redPackKey, ""));
			RedPack redPack = redPackService.get(redPackId);
			redPackService.pushEventOverdue(redPack);
		}
	}
}
