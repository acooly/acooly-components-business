package com.acooly.module.countnum.business.service.cache;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.module.countnum.CountNumProperties;
import com.acooly.module.countnum.business.service.conver.CountNumEntityConverDto;
import com.acooly.module.countnum.dto.CountNumGameDto;
import com.acooly.module.countnum.dto.CountNumGameOrderDto;
import com.acooly.module.countnum.entity.CountNum;
import com.acooly.module.countnum.entity.CountNumOrder;
import com.acooly.module.countnum.enums.CountNumTypeEnum;
import com.acooly.module.countnum.enums.result.CountNumGameResultCodeEnum;
import com.acooly.module.countnum.service.CountNumOrderService;
import com.acooly.module.countnum.service.CountNumService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("countNumCacheDataService")
public class CountNumCacheDataService {

	/** 计数游戏 redis 缓存时间 **/
	public static Long COUNT_NUM_REDIS_TIME = 10L;

	/** 计数游戏 单个用户 redis 缓存时间 5分钟 **/
	public static Long COUNT_NUM_ORDER_ONE_REDIS_TIME = 5L;

	@Autowired
	private CountNumService countNumService;

	@Autowired
	private CountNumOrderService countNumOrderService;

	@Autowired
	private CountNumProperties countNumProperties;

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 设置计数游戏 redis有效时间
	 * 
	 * @return
	 */
	private Long setRedisLifeTime() {
		Long countNumRedisTime = countNumProperties.getCountNumRedisTime();
		if (countNumRedisTime < COUNT_NUM_REDIS_TIME) {
			countNumRedisTime = COUNT_NUM_REDIS_TIME;
		}
		return countNumRedisTime;
	}

	/**
	 * 
	 * game_count_num_key_redis_lock_xxxx
	 * 
	 * 获取Redis key计数游戏锁
	 * 
	 * @param countNumId
	 * @return
	 */
	public String getCountNumRedisLockKey(Long countNumId) {
		return countNumProperties.getCountNumDistributedLockKey() + "_redis_lock_" + countNumId;
	}

	/**
	 * 
	 * game_count_num_key_redis_xxxx
	 * 
	 * 获取Redis key计数游戏锁
	 * 
	 * @param countNumId
	 * @return
	 */
	public String getCountNumRedisKey(Long countNumId) {
		return countNumProperties.getCountNumDistributedLockKey() + "_redis_" + countNumId;
	}

	/**
	 * 获取redis缓存数据（计数游戏）
	 * 
	 * @param CountNumId
	 * @return
	 */
	public CountNumGameDto getCountNumRedisDataByKey(Long countNumId) {
		String countNumRedisKey = getCountNumRedisKey(countNumId);
		CountNumGameDto dto = (CountNumGameDto) redisTemplate.opsForValue().get(countNumRedisKey);
		if (dto == null) {
			dto = new CountNumGameDto();
			CountNum countNum = countNumService.lockById(countNumId);
			if (null == countNum) {
				throw new BusinessException(CountNumGameResultCodeEnum.COUNT_NUM_NOT_EXISTING.message(),
						CountNumGameResultCodeEnum.COUNT_NUM_NOT_EXISTING.code());
			}
			countNumService.checkCountNumOverdue(countNum);
			CountNumEntityConverDto.converCountNumDto(countNum, dto);
			setCountNumRedisData(dto);
		}
		log.info("获取[计数游戏]：redis缓存数据:{}", dto);
		return dto;
	}

	/**
	 * 设置redis[计数游戏]数据
	 * 
	 * @param CountNumId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void setCountNumRedisData(CountNumGameDto dto) {
		String countNumRedisKey = getCountNumRedisKey(dto.getCountNumId());
		log.debug("设置redis[计数游戏]数据countNumRedisKey：{}", countNumRedisKey);
		redisTemplate.opsForValue().set(countNumRedisKey, dto, setRedisLifeTime(), TimeUnit.MINUTES);
	}

	/**
	 * 
	 * game_count_num_key_redis_list_xxxx
	 * 
	 * 获取Redis key计数游戏锁（计数游戏列表数据）
	 * 
	 * @param CountNumId
	 * @return
	 */
	public String getCountNumRedisListKey(Long CountNumId) {
		return countNumProperties.getCountNumDistributedLockKey() + "_redis_list_" + CountNumId;
	}

	@SuppressWarnings("unchecked")
	public void setCountNumOrderRedisDataDelete(Long countNumId) {
		String countNumRedisListKey = getCountNumRedisListKey(countNumId);
		log.info("计数游戏组件,计数游戏id:{},清空缓存列表,listKey:{}", countNumId, countNumRedisListKey);
		redisTemplate.delete(countNumRedisListKey);
	}

	/**
	 * 获取计数游戏订单 redis缓存数据（计数游戏列表数据）
	 * 
	 * @param CountNumId
	 * @param eventDto
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<CountNumGameOrderDto> getCountNumRedisDataListByKey(Long countNumId) {
		String countNumRedisListKey = getCountNumRedisListKey(countNumId);
		ListOperations redisDataList = redisTemplate.opsForList();
		List<CountNumGameOrderDto> orderDtoList = (List<CountNumGameOrderDto>) redisDataList.range(countNumRedisListKey,
				0, -1);
		if ((orderDtoList == null) || (orderDtoList.isEmpty())) {
			orderDtoList = Lists.newArrayList();

			CountNumGameDto countNumGame = getCountNumRedisDataByKey(countNumId);
			List<CountNumOrder> countNumOrderList = countNumOrderService.findByCountNumIdAndLimit(countNumId,
					countNumProperties.getCountNumRedisOrderNum(), countNumGame.getType());
			if ((countNumOrderList != null) && (!countNumOrderList.isEmpty())) {
				// 防止高并发重复缓存列表，需清空列表
				setCountNumOrderRedisDataDelete(countNumId);

				// 计数游戏记录-订单转化为dto
				orderDtoList = CountNumEntityConverDto.converCountNumOrderDtoList(countNumOrderList, orderDtoList);

				redisDataList.rightPushAll(countNumRedisListKey, orderDtoList);
				redisTemplate.expire(countNumRedisListKey, setRedisLifeTime(), TimeUnit.MINUTES);
			}
		}
		return orderDtoList;
	}

	/**
	 * 设置计数游戏订单缓存数据（计数游戏列表数据）
	 * 
	 * @param eventDto
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setCountNumOrderRedisData(CountNumGameOrderDto orderDto) {
		long countNumId = orderDto.getCountNumId();

		String redisDataListKey = getCountNumRedisListKey(countNumId);
		ListOperations redisDataList = redisTemplate.opsForList();
		Long expireTime = redisTemplate.getExpire(redisDataListKey, TimeUnit.MINUTES);
		if (expireTime > 0) {
			// 新增设置缓存数据
			redisDataList.rightPush(redisDataListKey, orderDto);
		} else {
			// 获取游戏列表
			getCountNumRedisDataListByKey(countNumId);
		}

		// 排序
		setCountNumOrderRedisDataSort(countNumId, redisDataListKey, redisDataList);

		log.debug("设置redis计数游戏订单数据redisDataListKey:{}", redisDataListKey);
	}

	/**
	 * 排序
	 * 
	 * @param countNumId
	 * @param redisDataListKey
	 * @param redisDataList
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setCountNumOrderRedisDataSort(long countNumId, String redisDataListKey, ListOperations redisDataList) {
		CountNumGameDto countNumGame = getCountNumRedisDataByKey(countNumId);
		List<CountNumGameOrderDto> orderDtoList = (List<CountNumGameOrderDto>) redisDataList.range(redisDataListKey, 0,
				countNumProperties.getCountNumRedisOrderNum());
		if (countNumGame.getType() == CountNumTypeEnum.NUM_LIMIT) {
			orderDtoList.sort((a, b) -> a.getNum().compareTo(b.getNum()));
		} else {
			orderDtoList.sort((a, b) -> b.getNum().compareTo(a.getNum()));
		}
		redisTemplate.delete(redisDataListKey);

		redisDataList.rightPushAll(redisDataListKey, orderDtoList);
		redisTemplate.expire(redisDataListKey, setRedisLifeTime(), TimeUnit.MINUTES);
	}

	/**
	 * 
	 * game_count_num_key_redis_one_xxxx_
	 * 
	 * 获取Redis key计数游戏锁（计数游戏列表数据）
	 * 
	 * @param CountNumId
	 * @return
	 */
	public String getCountNumOrderOneKey(Long countNumId, Long userId) {
		return countNumProperties.getCountNumDistributedLockKey() + "_redis_one_" + countNumId + "_" + userId;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<CountNumGameOrderDto> getCountNumOrderOneRedisData(long countNumId, long userId) {
		String redisOneDataListKey = getCountNumOrderOneKey(countNumId, userId);

		ListOperations redisDataList = redisTemplate.opsForList();
		List<CountNumGameOrderDto> orderDtoList = (List<CountNumGameOrderDto>) redisDataList.range(redisOneDataListKey,
				0, -1);

		if ((orderDtoList == null) || (orderDtoList.isEmpty())) {
			List<CountNumOrder> countNumOrderList = countNumOrderService.findByCountNumIdAndUserId(countNumId, userId);
			if ((countNumOrderList != null) && (!countNumOrderList.isEmpty())) {

				// 删除用户订单缓存列表
				redisTemplate.delete(redisOneDataListKey);

				// 计数游戏记录-订单转化为dto
				orderDtoList = CountNumEntityConverDto.converCountNumOrderDtoList(countNumOrderList, orderDtoList);

				// 开始设置缓存
				redisDataList.rightPushAll(redisOneDataListKey, orderDtoList);
				redisTemplate.expire(redisOneDataListKey, COUNT_NUM_ORDER_ONE_REDIS_TIME, TimeUnit.MINUTES);
			}
		}

		return orderDtoList;

	}

	/**
	 * 设置计数游戏订单缓存数据（计数游戏列表数据）
	 * 
	 * @param eventDto
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setCountNumOrderOneRedisData(CountNumGameOrderDto orderDto) {
		long countNumId = orderDto.getCountNumId();
		long userId = orderDto.getUserId();
		String redisOneDataListKey = getCountNumOrderOneKey(countNumId, userId);

		ListOperations redisDataList = redisTemplate.opsForList();
		Long expireTime = redisTemplate.getExpire(redisOneDataListKey, TimeUnit.MINUTES);

		if (expireTime > 0) {
			// 新增新的缓存值
			redisDataList.rightPush(redisOneDataListKey, orderDto);
		} else {
			// 获取游戏列表
			getCountNumOrderOneRedisData(countNumId, userId);
		}

		redisTemplate.expire(redisOneDataListKey, COUNT_NUM_ORDER_ONE_REDIS_TIME, TimeUnit.MINUTES);
		log.debug("设置redis计数游戏订单数据redisDataListKey:{}", redisOneDataListKey);
	}

}
