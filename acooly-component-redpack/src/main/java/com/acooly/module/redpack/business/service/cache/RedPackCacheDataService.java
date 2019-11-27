package com.acooly.module.redpack.business.service.cache;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.Dates;
import com.acooly.module.redpack.RedPackProperties;
import com.acooly.module.redpack.business.common.RedpackConstant;
import com.acooly.module.redpack.business.service.conver.RedPackEntityConverDto;
import com.acooly.module.redpack.entity.RedPack;
import com.acooly.module.redpack.entity.RedPackOrder;
import com.acooly.module.redpack.enums.result.RedPackResultCodeEnum;
import com.acooly.module.redpack.event.dto.RedPackDto;
import com.acooly.module.redpack.event.dto.RedPackOrderDto;
import com.acooly.module.redpack.service.RedPackOrderService;
import com.acooly.module.redpack.service.RedPackService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("redPackCacheDataService")
public class RedPackCacheDataService {

	/** 红包 redis 缓存时间 **/
	public static final Long RED_PACK_REDIS_TIME = 10L;

	@Autowired
	private RedPackService redPackService;
	@Autowired
	private RedPackOrderService redPackOrderService;

	@Autowired
	private RedPackProperties redPackProperties;

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 设置红包 redis有效时间
	 * 
	 * @return
	 */
	private Long setRedisLifeTime() {
		Long redPackRedisTime = redPackProperties.getRedPackRedisTime();
		if (redPackRedisTime < RED_PACK_REDIS_TIME) {
			redPackRedisTime = RED_PACK_REDIS_TIME;
		}
		return redPackRedisTime;
	}

	/**
	 * red_pack_lock_key_xxxxx
	 * 
	 * 获取红包 锁
	 * 
	 * @param redPackId
	 * @return
	 */
	public String getRedPackLockKey(Long redPackId) {
		return redPackProperties.getRedPackDistributedLockKey() + RedpackConstant.REDIS_LOCK + redPackId;
	}

	/**
	 * 
	 * red_pack_redis_xxxxx
	 * 
	 * 获取Redis key红包
	 * 
	 * @param redPackId
	 * @return
	 */
	public String getRedPackRedisKey(Long redPackId) {
		return redPackProperties.getRedPackDistributedLockKey() + RedpackConstant.REDIS_MAIN + redPackId;
	}

	/**
	 * 获取redis缓存数据（红包数据）
	 * 
	 * @param redPackId
	 * @return
	 */
	public RedPackDto getRedPackRedisDataByKey(Long redPackId) {
		RedPackDto dto = (RedPackDto) redisTemplate.opsForValue().get(getRedPackRedisKey(redPackId));
		if (dto == null) {
			dto = new RedPackDto();
			RedPack redPack = redPackService.lockById(redPackId);
			if (null == redPack) {
				throw new BusinessException(RedPackResultCodeEnum.RED_PACK_NOT_EXISTING.message(),
						RedPackResultCodeEnum.RED_PACK_NOT_EXISTING.code());
			}
			redPackService.checkRedPackOverdue(redPack);
			RedPackEntityConverDto.converRedPackDto(redPack, dto);
			setRedPackRedisData(dto);
		}
		log.info("获取红包：redis缓存数据:{}", dto);
		return dto;
	}

	/**
	 * 设置redis缓存数据（红包数据）
	 * 
	 * @param redPackId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void setRedPackRedisData(RedPackDto dto) {
		String redPackRedisKey = getRedPackRedisKey(dto.getRedPackId());
		log.debug("设置redis红包数据redPackRedisKey：{}", redPackRedisKey);
		redisTemplate.opsForValue().set(redPackRedisKey, dto, setRedisLifeTime(), TimeUnit.MINUTES);
	}

	/**
	 * 
	 * red_pack_lock_key_redis_list_xxxxx
	 * 
	 * 获取Redis key红包锁（红包列表数据）
	 * 
	 * @param redPackId
	 * @return
	 */
	public String getRedPackRedisListKey(Long redPackId) {
		return redPackProperties.getRedPackDistributedLockKey() + RedpackConstant.REDIS_LIST + redPackId;
	}

	@SuppressWarnings("unchecked")
	public void setRedPackOrderRedisDataDelete(Long redPackId) {
		String redPackRedisListKey = getRedPackRedisListKey(redPackId);
		log.info("红包组件,红包id:{},清空红包缓存列表,listKey:{}", redPackId, redPackRedisListKey);
		redisTemplate.delete(redPackRedisListKey);
	}

	/**
	 * 获取红包订单 redis缓存数据（红包列表数据）
	 * 
	 * @param redPackId
	 * @param eventDto
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<RedPackOrderDto> getRedPackRedisDataListByKey(Long redPackId) {
		String redPackRedisListKey = getRedPackRedisListKey(redPackId);
		ListOperations redisDataList = redisTemplate.opsForList();
		List<RedPackOrderDto> orderDtoList = (List<RedPackOrderDto>) redisDataList.range(redPackRedisListKey, 0, -1);
		if ((orderDtoList == null) || (orderDtoList.isEmpty())) {
			orderDtoList = Lists.newArrayList();
			List<RedPackOrder> redPackOrderList = redPackOrderService.findByRedPackId(redPackId);
			orderDtoList = RedPackEntityConverDto.converSendRedPackDto(redPackOrderList, orderDtoList);
			if ((orderDtoList != null) && (!orderDtoList.isEmpty())) {
				// 防止高并发重复缓存列表，需清空列表
				setRedPackOrderRedisDataDelete(redPackId);

				redisDataList.leftPushAll(redPackRedisListKey, orderDtoList);
				redisTemplate.expire(redPackRedisListKey, setRedisLifeTime(), TimeUnit.MINUTES);
			}
		}
		return orderDtoList;
	}

	/**
	 * 设置红包订单缓存数据（红包列表数据）
	 * 
	 * @param eventDto
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setRedPackOrderRedisData(RedPackOrderDto orderDto) {
		long redPackId = orderDto.getRedPackId();
		String redisDataListKey = getRedPackRedisListKey(redPackId);

		List<RedPackOrderDto> redPackOrderDtoList = Lists.newArrayList();

		ListOperations redisDataList = redisTemplate.opsForList();
		Long times = redisTemplate.getExpire(redisDataListKey, TimeUnit.MINUTES);

		if (times > 0) {
			redPackOrderDtoList = getRedPackRedisDataListByKey(redPackId);
		} else {
			List<RedPackOrder> redPackOrderList = redPackOrderService.findByRedPackIdAndUserId(redPackId,
					orderDto.getUserId());
			redPackOrderDtoList = RedPackEntityConverDto.converSendRedPackDto(redPackOrderList, redPackOrderDtoList);
			if ((redPackOrderDtoList != null) && (!redPackOrderDtoList.isEmpty())) {
				redisDataList.leftPushAll(redisDataListKey, redPackOrderDtoList);
			}
		}

		redisDataList.leftPush(redisDataListKey, orderDto);
		redisTemplate.expire(redisDataListKey, setRedisLifeTime(), TimeUnit.MINUTES);

		// 设置红包订单统计记录
		setRedPackRedisMapKey(orderDto);

		log.debug("设置redis红包订单数据redisDataListKey:{}", redisDataListKey);
	}

	/**
	 * 
	 * red_pack_lock_key_redis_map_xxxxx
	 * 
	 * 获取Redis key红包锁（红包列表数据）
	 * 
	 * @param redPackId
	 * @return
	 */
	public String getRedPackRedisMapKey(Long redPackId) {
		return redPackProperties.getRedPackDistributedLockKey() + RedpackConstant.REDIS_MAP + redPackId;
	}

	/**
	 * 设置红包订单统计记录
	 * 
	 * @param eventDto
	 */
	private void setRedPackRedisMapKey(RedPackOrderDto orderDto) {
		long redPackId = orderDto.getRedPackId();
		List<RedPackOrderDto> orderDtoList = getRedPackRedisDataMapByKey(redPackId, orderDto.getUserId());
		orderDtoList.add(orderDto);
		setRedPackRedisMapValues(getRedPackRedisMapKey(redPackId), String.valueOf(orderDto.getUserId()), orderDtoList);
	}

	/**
	 * 获取红包订单 redis缓存数据（红包列表数据）
	 * 
	 * @param redPackId
	 * @param eventDto
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<RedPackOrderDto> getRedPackRedisDataMapByKey(Long redPackId, Long userId) {
		String redPackRedisMapKey = getRedPackRedisMapKey(redPackId);
		String redPackRedisMapHashKey = String.valueOf(userId);

		List<RedPackOrderDto> orderDtoList = Lists.newArrayList();
		HashOperations redisDataHash = redisTemplate.opsForHash();

		Boolean isHaveKey = redisDataHash.hasKey(redPackRedisMapKey, redPackRedisMapHashKey);
		if (isHaveKey) {
			orderDtoList = (List<RedPackOrderDto>) redisDataHash.get(redPackRedisMapKey, redPackRedisMapHashKey);
		} else {
			List<RedPackOrder> redPackOrderList = redPackOrderService.findByRedPackIdAndUserId(redPackId, userId);
			orderDtoList = RedPackEntityConverDto.converSendRedPackDto(redPackOrderList, orderDtoList);
			setRedPackRedisMapValues(redPackRedisMapKey, redPackRedisMapHashKey, orderDtoList);
		}
		return orderDtoList;
	}

	/**
	 * 设置RedisMap 值
	 * 
	 * @param redPackRedisMapKey
	 * @param redPackRedisMapHashKey
	 * @param sendRedPackEventDtoList
	 */
	private void setRedPackRedisMapValues(String redPackRedisMapKey, String redPackRedisMapHashKey,
			List<RedPackOrderDto> orderDtoList) {
		HashOperations redisDataHash = redisTemplate.opsForHash();
		redisDataHash.put(redPackRedisMapKey, redPackRedisMapHashKey, orderDtoList);
		redisTemplate.expire(redPackRedisMapKey, setRedisLifeTime(), TimeUnit.MINUTES);
		log.debug("设置redis红包订单统计数据redPackRedisMapKey:{},redPackRedisMapHashKey:{}", redPackRedisMapKey,
				redPackRedisMapHashKey);
	}

	/**
	 * 
	 * red_pack_key
	 * 
	 * 获取Redis key红包游戏 监听器锁
	 * 
	 * @param countNumId
	 * @return
	 */
	public String getListenerRedPackRedisKey(long redPackId) {
		return redPackProperties.getRedPackDistributedLockKey() + RedpackConstant.REDIS_LISTENER + redPackId;
	}

	/**
	 * 
	 * red_pack_key
	 * 
	 * 获取Redis key红包游戏 监听器锁
	 * 
	 * @param countNumId
	 * @return
	 */
	public String getListenerRedPackRedisMarkKey(long redPackId) {
		return redPackProperties.getRedPackDistributedLockKey() + RedpackConstant.REDIS_LISTENER_MARK + redPackId;
	}

	/**
	 * 过期时间监听
	 * 
	 * @param redPackId
	 * @param dto
	 * @param overdueTime
	 */
	@SuppressWarnings("unchecked")
	public void setListenerMarkRedPackRedisKey(Long redPackId, RedPackDto dto, Date overdueTime) {
		String listenerKey = getListenerRedPackRedisKey(redPackId);
		String listenerMarkKey = getListenerRedPackRedisMarkKey(redPackId);
		Date currentDate = new Date();
		long times = overdueTime.getTime() - currentDate.getTime();
		String overdueTimeStr = Dates.format(overdueTime);
		String currentDateStr = Dates.format(currentDate);
		log.info("红包游戏组件:[设置游戏过期时间],监听id:{},红包游戏id:{},过期时间:{},当前时间:{}有效时间:{}毫秒", listenerKey, redPackId, overdueTimeStr,
				currentDateStr, times);
		if (times > 0) {
			redisTemplate.opsForValue().set(listenerKey, dto, times, TimeUnit.MILLISECONDS);

			// 新增监听锁，主要处理分布式部署，过期事件重复通知
			long addTime = Dates.addDate(overdueTime, 1, TimeUnit.HOURS).getTime();
			redisTemplate.opsForValue().set(listenerMarkKey, dto, times + addTime, TimeUnit.MILLISECONDS);
		}
	}

}
