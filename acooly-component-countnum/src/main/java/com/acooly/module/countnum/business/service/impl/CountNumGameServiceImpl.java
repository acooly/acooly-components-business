package com.acooly.module.countnum.business.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.module.countnum.business.service.CountNumGameService;
import com.acooly.module.countnum.business.service.cache.CountNumCacheDataService;
import com.acooly.module.countnum.business.service.conver.CountNumEntityConverDto;
import com.acooly.module.countnum.dto.CountNumGameDto;
import com.acooly.module.countnum.dto.CountNumGameOrderDto;
import com.acooly.module.countnum.dto.CountNumGameOrderRankDto;
import com.acooly.module.countnum.dto.order.CountNumGameResultDto;
import com.acooly.module.countnum.dto.order.CreateCountNumGameDto;
import com.acooly.module.countnum.entity.CountNum;
import com.acooly.module.countnum.entity.CountNumOrder;
import com.acooly.module.countnum.enums.CountNumStatusEnum;
import com.acooly.module.countnum.enums.result.CountNumGameResultCodeEnum;
import com.acooly.module.countnum.service.CountNumOrderService;
import com.acooly.module.countnum.service.CountNumService;
import com.acooly.module.distributedlock.DistributedLockFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("countNumGameService")
public class CountNumGameServiceImpl implements CountNumGameService {

	/** redis尝试锁，时间设置 **/
	public static Integer COUNT_NUM_TRY_LOCK_TIME = 1;

	@Autowired
	private CountNumService countNumService;

	@Autowired
	private CountNumOrderService countNumOrderService;

	@Autowired
	private CountNumCacheDataService countNumCacheDataService;

	@Autowired
	private DistributedLockFactory factory;

	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public CountNumGameDto findCountNum(Long countNumId) {
		return countNumCacheDataService.getCountNumRedisDataByKey(countNumId);
	}

	@Override
	public CountNumGameDto createCountNumGame(CreateCountNumGameDto createDto) {

		CountNum countNum = new CountNum();
		countNum.setTitle(createDto.getTitle());
		countNum.setCreateUserId(createDto.getCreateUserId());
		countNum.setCreateUserName(createDto.getCreateUserName());
		countNum.setOverdueTime(createDto.getOverdueTime());
		countNum.setType(createDto.getType());
		countNum.setMaxNum(createDto.getMaxNum());
		countNum.setLimitNum(createDto.getLimitNum());
		countNum.setBusinessId(createDto.getBusinessId());
		countNum.setBusinessData(createDto.getBusinessData());
		countNumService.save(countNum);

		// 设置缓存
		CountNumGameDto dto = new CountNumGameDto();
		CountNumEntityConverDto.converCountNumDto(countNum, dto);
		countNumCacheDataService.setCountNumRedisData(dto);

		// 设置缓存监听事件
		if (createDto.getOverdueTime() != null) {
			countNumCacheDataService.setListenerCountNumRedisLockKey(dto.getCountNumId(), dto,
					createDto.getOverdueTime());
		}

		return dto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CountNumGameOrderDto submitCountNumGameResult(CountNumGameResultDto dto) {
		CountNumGameOrderDto orderDto = new CountNumGameOrderDto();

		Long countNumId = dto.getCountNumId();
		Long userId = dto.getUserId();

		String countNumLockKey = countNumCacheDataService.getCountNumRedisLockKey(countNumId);
		Lock lock = factory.newLock(countNumLockKey);
		try {
			log.info("计数游戏组件:[上传游戏结果],开始获取锁,lockKey:{},计数游戏id:{}", countNumLockKey, countNumId);
			if (lock.tryLock(COUNT_NUM_TRY_LOCK_TIME, TimeUnit.SECONDS)) {
				try {

					CountNumGameDto countNumGameDto = countNumCacheDataService.getCountNumRedisDataByKey(countNumId);

					// 游戏状态判断
					verifyStatus(countNumGameDto);

					// 校验过期
					verifyOverdueDate(countNumGameDto);

					// 游戏已经参与最大数
					verifyMaxNum(countNumId, countNumGameDto);

					// 校验用户参与游戏次数判断
					verifyUserLimieNum(countNumId, userId, countNumGameDto);

					// 数据存储
					CountNumOrder order = countNumOrderService.saveCountNumOrderResult(dto, countNumGameDto);

					// 对象转化
					CountNumEntityConverDto.converCountNumOrderDto(order, orderDto);

					// 设置缓存数据
					countNumCacheDataService.setCountNumReisMapData(orderDto);
					countNumCacheDataService.setCountNumOrderOneRedisData(orderDto);

					// 发送事件
					countNumOrderService.pushEvent(order);

				} catch (BusinessException e) {
					log.info("计数游戏组件:[上传游戏结果],业务处理失败,lockKey:{},计数游戏id:{},{}", countNumLockKey, countNumId, e);
					throw new BusinessException(e.getMessage(), e.getCode());
				} finally {
					lock.unlock();
				}
			} else {
				if (redisTemplate.hasKey(countNumLockKey)) {
					log.info("[计数游戏组件],当前key未释放,系统删除:lockKey:{}", countNumLockKey);
					redisTemplate.delete(countNumLockKey);
				}

				log.info("[计数游戏组件]--,获取锁失败,lockKey:{},组件id:{}", countNumLockKey, countNumId);
				throw new BusinessException(CountNumGameResultCodeEnum.COUNT_NUM_LOCK_ERROR.message(),
						CountNumGameResultCodeEnum.COUNT_NUM_LOCK_ERROR.code());
			}
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			log.error("计数游戏组件:[上传游戏结果]失败,计数游戏id:{},{}", countNumId, e);
			throw new BusinessException("计数游戏组件:[上传游戏结果]失败");
		}
		return orderDto;
	}

	@Override
	public CountNumGameDto countNumGameFinish(long countNumId) {
		CountNum countNum = countNumService.countNumGameFinish(countNumId);

		// 设置缓存
		CountNumGameDto dto = new CountNumGameDto();
		CountNumEntityConverDto.converCountNumDto(countNum, dto);
		countNumCacheDataService.setCountNumRedisData(dto);
		return dto;
	}

	@Override
	public CountNumGameDto countNumGameOverdueFinish(long countNumId, Date overdueDate) {
		CountNum countNum = countNumService.setOverdueDate(countNumId, overdueDate);

		// 设置缓存
		CountNumGameDto dto = new CountNumGameDto();
		CountNumEntityConverDto.converCountNumDto(countNum, dto);
		countNumCacheDataService.setCountNumRedisData(dto);

		// 设置缓存监听事件
		countNumCacheDataService.setListenerCountNumRedisLockKey(countNumId, dto, overdueDate);
		return dto;
	}

	@Override
	public List<CountNumGameOrderDto> findCountNumGameOrder(long countNumId) {
		List<CountNumGameOrderDto> dtoList = countNumCacheDataService.findCountNumRedisMapSort(countNumId);
		return dtoList;
	}

	@Override
	public Long countNumGameOrderNum(long userId, long countNumId) {
		CountNumGameOrderDto orderDto = countNumCacheDataService.getCountNumOrderOneRedisData(countNumId, userId);
		return orderDto.getJoinNum();
	}

	/**
	 * 校验用户参与游戏次数判断
	 * 
	 * @param countNumId
	 * @param userId
	 * @param countNumGameDto
	 */
	public void verifyUserLimieNum(Long countNumId, Long userId, CountNumGameDto countNumGameDto) {
		CountNumGameOrderDto countNumGameOrderDto = countNumCacheDataService.getCountNumOrderOneRedisData(countNumId,
				userId);
		long joinNum = 0L;
		if (countNumGameOrderDto != null) {
			joinNum = countNumGameOrderDto.getJoinNum();
		}
		long limitNum = countNumGameDto.getLimitNum();
		log.info("[计数游戏组件],userId:{},countId:{},限制次数:{},用户参与游戏次数:{}", userId, countNumId, limitNum, joinNum);
		if ((limitNum > 0) && (joinNum >= limitNum)) {
			throw new BusinessException(CountNumGameResultCodeEnum.COUNT_NUM_PLAY_LIMIT_ALREADY.message(),
					CountNumGameResultCodeEnum.COUNT_NUM_PLAY_LIMIT_ALREADY.code());
		}
	}

	/**
	 * 校验过期
	 * 
	 * @param countNumGameDto
	 */
	public void verifyOverdueDate(CountNumGameDto countNumGameDto) {
		Long countNumId = countNumGameDto.getCountNumId();

		Date overdueDateTime = countNumGameDto.getOverdueTime();
		if (overdueDateTime != null) {
			Long overdueTime = countNumGameDto.getOverdueTime().getTime();
			Long currentTime = (new Date()).getTime();
			if (currentTime > overdueTime) {
				countNumGameFinish(countNumId);
				log.info("[计数游戏组件],countId:{},游戏已经过期", countNumId);
				throw new BusinessException(CountNumGameResultCodeEnum.COUNT_NUM_OVERDUE.message(),
						CountNumGameResultCodeEnum.COUNT_NUM_OVERDUE.code());
			}
		}
	}

	/**
	 * 游戏已经参与最大数
	 * 
	 * @param countNumId
	 * @param countNumGameDto
	 */
	public void verifyMaxNum(Long countNumId, CountNumGameDto countNumGameDto) {
		if (countNumGameDto.getMaxNum() != 0) {
			long countNumDB = countNumOrderService.countByCountId(countNumId);
			long maxNum = countNumGameDto.getMaxNum();
			if (countNumDB > maxNum) {
				log.info("[计数游戏组件],countId:{},参与人数已达到游戏最大数量限制", countNumId);
				throw new BusinessException(CountNumGameResultCodeEnum.COUNT_NUM_MAX_NUM.message(),
						CountNumGameResultCodeEnum.COUNT_NUM_MAX_NUM.code());
			}
		}
	}

	/**
	 * 游戏状态判断
	 * 
	 * @param countNumGameDto
	 */
	public void verifyStatus(CountNumGameDto countNumGameDto) {
		if (countNumGameDto.getStatus() == CountNumStatusEnum.FINISH) {
			log.info("[计数游戏组件],countId:{},当前游戏已经结束", countNumGameDto.getCountNumId());
			throw new BusinessException(CountNumGameResultCodeEnum.COUNT_NUM_ALREADY_FINISH.message(),
					CountNumGameResultCodeEnum.COUNT_NUM_ALREADY_FINISH.code());
		}
	}

	@Override
	public CountNumGameOrderRankDto userRanking(long userId, long countNumId, boolean isOverstep) {
		CountNumGameDto countNumGame = findCountNum(countNumId);
		CountNumGameOrderRankDto rankDto = countNumOrderService.userRankByCountNumId(userId, countNumId,
				countNumGame.getType());
		if (rankDto == null) {
			return rankDto;
		}

		if (isOverstep) {
			long rank = rankDto.getRank();
			if (rank == 1) {
				rankDto.setOverstepRate("100.00");
				return rankDto;
			}

			long totalNum = countNumOrderService.countByCountId(countNumId);
			if (rank == totalNum) {
				rankDto.setOverstepRate("0.00");
				return rankDto;
			}

			double overstep = ((totalNum - rank + 1) * 100.00 / totalNum);
			String overstepRate = String.format("%.2f", overstep);
			rankDto.setOverstepRate(overstepRate);
		}
		log.info("[计数游戏组件],countId:{},userId:{},有效值:{},用户排名:{},百分比:{}", countNumId, userId, rankDto.getNum(),
				rankDto.getRank(), rankDto.getOverstepRate());
		return rankDto;
	}

}
