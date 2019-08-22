package com.acooly.module.countnum.business.service.cache;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.Dates;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("countNumCacheDataService")
public class CountNumCacheDataService {

	/** 计数游戏 redis 缓存时间 **/
	public static long COUNT_NUM_REDIS_TIME = 10L;

	/** 计数游戏 单个用户 redis 缓存时间 5分钟 **/
	public static long COUNT_NUM_ORDER_ONE_REDIS_TIME = 5L;

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
	 * 
	 * game_count_num_key_redis_lock_xxxx
	 * 
	 * 获取Redis key计数游戏锁
	 * 
	 * @param countNumId
	 * @return
	 */
	public String getCountNumRedisLockKey(long countNumId) {
		return countNumProperties.getCountNumDistributedLockKey() + "_redis_lock_" + countNumId;
	}

	/**
	 * 设置计数游戏 redis有效时间
	 * 
	 * @return
	 */
	private long setRedisLifeTime() {
		long countNumRedisTime = countNumProperties.getCountNumRedisTime();
		if (countNumRedisTime < COUNT_NUM_REDIS_TIME) {
			countNumRedisTime = COUNT_NUM_REDIS_TIME;
		}
		return countNumRedisTime;
	}

	/**
	 * 
	 * game_count_num_key_redis_lock_listener_xxxx
	 * 
	 * 获取Redis key计数游戏 监听器锁
	 * 
	 * @param countNumId
	 * @return
	 */
	public String getListenerCountNumRedisLockKey(long countNumId) {
		return countNumProperties.getCountNumDistributedLockKey() + "_redis_lock_listener_" + countNumId;
	}

	/**
	 * 
	 * game_count_num_key_redis_lock_listener_xxxx
	 * 
	 * 获取Redis key计数游戏 监听器锁
	 * 
	 * @param countNumId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void setListenerCountNumRedisLockKey(long countNumId, CountNumGameDto dto, Date overdueTime) {
		String listenerKey = getListenerCountNumRedisLockKey(countNumId);
		Date currentDate = new Date();
		long times = overdueTime.getTime() - currentDate.getTime();
		String overdueTimeStr = Dates.format(overdueTime);
		String currentDateStr = Dates.format(currentDate);
		log.info("计数游戏组件:[设置游戏过期时间],计数游戏id:{},过期时间:{},当前时间:{}有效时间:{}毫秒", countNumId, overdueTimeStr, currentDateStr,
				times);
		if (times > 0) {
			redisTemplate.opsForValue().set(listenerKey, dto, times, TimeUnit.MILLISECONDS);
		}
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
	public String getCountNumRedisKey(long countNumId) {
		return countNumProperties.getCountNumDistributedLockKey() + "_redis_" + countNumId;
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
	 * 获取redis缓存数据（计数游戏）
	 * 
	 * @param CountNumId
	 * @return
	 */
	public CountNumGameDto getCountNumRedisDataByKey(long countNumId) {
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
	 * 
	 * game_count_num_key_redis_one_xxxx_
	 * 
	 * 获取Redis key计数游戏锁（计数游戏列表数据）
	 * 
	 * @param CountNumId
	 * @return
	 */
	public String getCountNumOrderOneKey(long countNumId, long userId) {
		return countNumProperties.getCountNumDistributedLockKey() + "_redis_one_" + countNumId + "_" + userId;
	}

	/**
	 * 设置计数游戏订单缓存数据（计数游戏列表数据）
	 * 
	 * @param eventDto
	 */
	@SuppressWarnings({ "unchecked" })
	public void setCountNumOrderOneRedisData(CountNumGameOrderDto orderDto) {
		long countNumId = orderDto.getCountNumId();
		long userId = orderDto.getUserId();
		String redisOneDatatKey = getCountNumOrderOneKey(countNumId, userId);
		redisTemplate.opsForValue().set(redisOneDatatKey, orderDto, COUNT_NUM_ORDER_ONE_REDIS_TIME, TimeUnit.MINUTES);
		log.debug("设置redis计数游戏订单数据redisOneDatatKey:{}", redisOneDatatKey);
	}

	@SuppressWarnings({ "rawtypes" })
	public CountNumGameOrderDto getCountNumOrderOneRedisData(long countNumId, long userId) {
		String redisOneDatatKey = getCountNumOrderOneKey(countNumId, userId);
		ValueOperations redisOneData = redisTemplate.opsForValue();
		CountNumGameOrderDto orderDto = (CountNumGameOrderDto) redisOneData.get(redisOneDatatKey);
		if (orderDto == null) {
			orderDto = new CountNumGameOrderDto();
			CountNumOrder countNumOrder = countNumOrderService.findByCountNumIdAndUserIdOne(countNumId, userId);
			if (countNumOrder != null) {
				orderDto = CountNumEntityConverDto.converCountNumOrderDto(countNumOrder, orderDto);
			}
		}
		return orderDto;

	}

	/**
	 * 
	 * game_count_num_key_redis_map_xxxxxxxxx
	 * 
	 * 获取Redis key计数游戏锁（计数游戏列表数据）
	 * 
	 * @param redPackId
	 * @return
	 */
	public String getCountNumRedisMapKey(long countNumId) {
		return countNumProperties.getCountNumDistributedLockKey() + "_redis_map_" + countNumId;
	}

	/**
	 * 设置计数游戏订单统计记录
	 * 
	 * @param eventDto
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setCountNumReisMapData(CountNumGameOrderDto orderDto) {
		long countNumId = orderDto.getCountNumId();
		String mapKey = getCountNumRedisMapKey(countNumId);
		String hashKey = String.valueOf(orderDto.getUserId());
		long currentNum = orderDto.getNum();

		CountNumGameDto countNumGame = getCountNumRedisDataByKey(countNumId);
		CountNumTypeEnum type = countNumGame.getType();

		HashOperations redisMap = redisTemplate.opsForHash();

		// 获取缓存数据
		getCountNumReisMapData(countNumGame);

		CountNumGameOrderDto redisOrderDto = (CountNumGameOrderDto) redisMap.get(mapKey, hashKey);
		if (redisOrderDto != null) {
			long redisNum = redisOrderDto.getNum();
			if (type == CountNumTypeEnum.NUM_LIMIT) {
				// 次数限制
				if ((currentNum < redisNum) && (currentNum > 0)) {
					redisMap.put(mapKey, hashKey, orderDto);
				}
			} else {
				// 时间限制---num 升序(由小到大)
				if (currentNum > redisNum) {
					redisMap.put(mapKey, hashKey, orderDto);
				}
			}
		} else {
			if (currentNum > 0) {
				redisMap.put(mapKey, hashKey, orderDto);
			}
		}

		// 删除N后 排名记录
		long redisMapSize = redisMap.size(mapKey);
		long countNumSize = countNumProperties.getCountNumRedisOrderNum();
		if (redisMapSize > countNumSize) {
			List<CountNumGameOrderDto> lists = countNumRedisMapSort(countNumId, type);
			int lastIndex = lists.size() - 1;
			CountNumGameOrderDto dto = lists.get(lastIndex);

			String hashKeyStr = String.valueOf(dto.getUserId());
			redisMap.delete(mapKey, hashKeyStr);

			log.info("删除[计数组件]排名记录最后位,mapKey:{},hashKey:{},num:{}", mapKey, hashKeyStr, dto.getNum());
		}
		redisTemplate.expire(mapKey, setRedisLifeTime(), TimeUnit.MINUTES);
		log.debug("设置redis[计数组件]统计数据mapKey:{},hashKey:{}", mapKey, hashKey);
	}

	/**
	 * 获取key所有缓存数据
	 * 
	 * @param countNumId
	 * @param mapKey
	 * @param redisDataHash
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getCountNumReisMapData(CountNumGameDto countNumGame) {
		long countNumId = countNumGame.getCountNumId();
		String mapKey = getCountNumRedisMapKey(countNumId);

		HashOperations redisMap = redisTemplate.opsForHash();
		List<CountNumGameOrderDto> orderDtoList = redisMap.values(mapKey);
		if (orderDtoList.isEmpty()) {
			// 删除已缓存数据
			redisTemplate.delete(mapKey);

			List<CountNumOrder> countNumOrderList = countNumOrderService.findByCountNumIdAndLimit(
					countNumGame.getCountNumId(), countNumProperties.getCountNumRedisOrderNum(),
					countNumGame.getType());

			for (CountNumOrder countNumOrder : countNumOrderList) {
				CountNumGameOrderDto orderDto = new CountNumGameOrderDto();
				CountNumEntityConverDto.converCountNumOrderDto(countNumOrder, orderDto);
				redisMap.put(mapKey, String.valueOf(countNumOrder.getUserId()), orderDto);
			}
		}
		redisTemplate.expire(mapKey, setRedisLifeTime(), TimeUnit.MINUTES);
	}

	/**
	 * 获取用户排序列表
	 * 
	 * @param mapKey
	 * @param type
	 * @return
	 */
	public List<CountNumGameOrderDto> findCountNumRedisMapSort(long countNumId) {
		CountNumGameDto countNumGame = getCountNumRedisDataByKey(countNumId);
		// 获取key所有缓存数据
		getCountNumReisMapData(countNumGame);
		List<CountNumGameOrderDto> orderDtoList = countNumRedisMapSort(countNumId, countNumGame.getType());
		return orderDtoList;
	}

	/**
	 * 排序
	 * 
	 * @param countNumId
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CountNumGameOrderDto> countNumRedisMapSort(long countNumId, CountNumTypeEnum type) {
		String mapKey = getCountNumRedisMapKey(countNumId);
		List<CountNumGameOrderDto> orderDtoList = redisTemplate.opsForHash().values(mapKey);
		if (type == CountNumTypeEnum.NUM_LIMIT) {
			// 次数限制
			orderDtoList.sort(
					Comparator.comparing(CountNumGameOrderDto::getNum).thenComparing(CountNumGameOrderDto::getValidTime)
							.thenComparing(CountNumGameOrderDto::getCountNumOrderId));
		} else {
			// 时间限制
			orderDtoList.sort(Comparator.comparing(CountNumGameOrderDto::getNum).reversed()
					.thenComparing(CountNumGameOrderDto::getValidTime)
					.thenComparing(CountNumGameOrderDto::getCountNumOrderId));
		}
		return orderDtoList;
	}

}
