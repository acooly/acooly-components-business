package com.acooly.module.countnum.business.service.cache.listener;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acooly.module.countnum.CountNumProperties;
import com.acooly.module.countnum.dto.CountNumGameDto;
import com.acooly.module.countnum.entity.CountNum;
import com.acooly.module.countnum.service.CountNumService;
import com.acooly.module.distributedlock.DistributedLockFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("countNumRedisListener")
public class CountNumRedisListener implements MessageListener {

	/** redis尝试锁，时间设置 **/
	public static Integer COUNT_NUM_TRY_LOCK_TIME = 1;

	@Autowired
	private CountNumProperties countNumProperties;

	@Autowired
	private CountNumService countNumService;

	@Autowired
	private DistributedLockFactory factory;

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	@Transactional
	public void onMessage(Message message, byte[] pattern) {
		String redisKey = message.toString();
		String countNumKey = countNumProperties.getCountNumDistributedLockKey() + "_redis_lock_listener_";
		if (redisKey.contains(countNumKey)) {
			log.info("[计数游戏组件]即将要失效的Key:{}", redisKey);
			long countNumId = Long.parseLong(redisKey.replaceAll(countNumKey, ""));
			if (isSendEvent(countNumId)) {
				CountNum countNum = countNumService.countNumGameFinish(countNumId);
				countNumService.pushEvent(countNum);
			}
		}

		String countNumKeyId = countNumProperties.getCountNumDistributedLockKey() + "_redis_listener_";
		if (redisKey.contains(countNumKeyId)) {
			log.info("[计数游戏组件]即将要失效的Key:{}", redisKey);
			long countNumId = Long.parseLong(redisKey.replaceAll(countNumKeyId, ""));
			if (isSendEvent(countNumId)) {
				CountNum countNum = countNumService.countNumGameFinish(countNumId);
				countNumService.pushEvent(countNum);
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
	public boolean isSendEvent(Long countNumKeyId) {
		String listenerMarkKey = countNumProperties.getCountNumDistributedLockKey() + "_redis_listener_mark_"
				+ countNumKeyId;
		String listenerLockKey = listenerMarkKey + "_lock";
		// 分布式 部署通知
		if (countNumProperties.isOverdueMoreNotify()) {
			redisTemplate.delete(listenerMarkKey);
			return true;
		}

		Lock lock = factory.newLock(listenerLockKey);
		try {
			if (lock.tryLock(COUNT_NUM_TRY_LOCK_TIME, TimeUnit.SECONDS)) {
				try {
					CountNumGameDto dto = (CountNumGameDto) redisTemplate.opsForValue().get(listenerMarkKey);
					if (dto != null) {
						redisTemplate.delete(listenerMarkKey);
						return true;
					}
				} finally {
					lock.unlock();
				}
			} else {
				if (redisTemplate.hasKey(listenerLockKey)) {
					log.info("[计数游戏组件],当前key未释放,系统删除:lockKey:{}", listenerLockKey);
					redisTemplate.delete(listenerLockKey);
				}
				log.info("[计数游戏组件]--,获取锁失败,lockKey:{}", listenerLockKey);
			}
		} catch (Exception e) {
			log.error("[计数游戏组件]是否发布失效事件,处理失败:id:{},{}", countNumKeyId, e);
		}
		return false;
	}

}
