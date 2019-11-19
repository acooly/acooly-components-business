package com.acooly.module.redpack.business.service.cache.listener;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.acooly.module.distributedlock.DistributedLockFactory;
import com.acooly.module.redpack.RedPackProperties;
import com.acooly.module.redpack.entity.RedPack;
import com.acooly.module.redpack.event.dto.RedPackDto;
import com.acooly.module.redpack.service.RedPackService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("redPackRedisListener")
public class RedPackRedisListener implements MessageListener {

	/** redis尝试锁，时间设置 **/
	public static Integer REDIS_TRY_LOCK_TIME = 1;

	@Autowired
	private RedPackProperties redPackProperties;

	@Autowired
	private RedPackService redPackService;

	@Autowired
	private DistributedLockFactory factory;

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		String redisKey = message.toString();
		String redPackKey = redPackProperties.getRedPackDistributedLockKey() + "_redis_lock_listener_";
		if (redisKey.contains(redPackKey)) {
			log.info("[红包组件]即将要失效的Key:{}", redisKey);
			long redPackId = Long.parseLong(redisKey.replaceAll(redPackKey, ""));
			if (isSendEvent(redPackId)) {
				RedPack redPack = redPackService.get(redPackId);
				redPackService.pushEventOverdue(redPack);
			}
		}

		String redPackKeyId = redPackProperties.getRedPackDistributedLockKey() + "_redis_listener_";
		if (redisKey.contains(redPackKeyId)) {
			log.info("[红包组件]即将要失效的Key:{}", redisKey);
			long redPackId = Long.parseLong(redisKey.replaceAll(redPackKeyId, ""));

			if (isSendEvent(redPackId)) {
				RedPack redPack = redPackService.get(redPackId);
				redPackService.pushEventOverdue(redPack);
			}
		}
	}

	/**
	 * 是否发布失效事件
	 * 
	 * @param redPackId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean isSendEvent(Long redPackId) {
		String listenerMarkKey = redPackProperties.getRedPackDistributedLockKey() + "_redis_listener_mark_" + redPackId;
		String listenerLockKey = listenerMarkKey + "_lock";
		// 分布式 部署通知
		if (redPackProperties.isOverdueMoreNotify()) {
			redisTemplate.delete(listenerMarkKey);
			return true;
		}

		Lock lock = factory.newLock(listenerLockKey);
		try {
			if (lock.tryLock(REDIS_TRY_LOCK_TIME, TimeUnit.SECONDS)) {
				try {
					RedPackDto dto = (RedPackDto) redisTemplate.opsForValue().get(listenerMarkKey);
					if (dto != null) {
						redisTemplate.delete(listenerMarkKey);
						return true;
					}
				} finally {
					lock.unlock();
				}
			}else {
				if (redisTemplate.hasKey(listenerLockKey)) {
					log.info("[红包组件],失效事件当前key未释放,系统删除:lockKey:{}", listenerLockKey);
					redisTemplate.delete(listenerLockKey);
				}
			}
		} catch (Exception e) {
			log.error("红包组件:[发送红包]是否发布失效事件,处理失败:id:{},{}", redPackId, e);
		}
		return false;
	}
}
