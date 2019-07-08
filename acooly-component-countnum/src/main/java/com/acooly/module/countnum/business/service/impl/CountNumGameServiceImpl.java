package com.acooly.module.countnum.business.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.springframework.beans.factory.annotation.Autowired;
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
	public static Integer REDIS_TRY_LOCK_TIME = 1;

	@Autowired
	private CountNumService countNumService;

	@Autowired
	private CountNumOrderService countNumOrderService;

	@Autowired
	private CountNumCacheDataService countNumCacheDataService;

	@Autowired
	private DistributedLockFactory factory;

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
//		countNum.setIsCover(createDto.getIsCover());
		countNum.setMaxNum(createDto.getMaxNum());
		countNum.setLimitNum(createDto.getLimitNum());
		countNum.setBusinessId(createDto.getBusinessId());
		countNum.setBusinessData(createDto.getBusinessData());
		countNumService.save(countNum);

		// 设置缓存
		CountNumGameDto dto = new CountNumGameDto();
		CountNumEntityConverDto.converCountNumDto(countNum, dto);
		countNumCacheDataService.setCountNumRedisData(dto);

		return dto;
	}

	@Override
	public CountNumGameOrderDto submitCountNumGameResult(CountNumGameResultDto dto) {
		CountNumGameOrderDto orderDto = new CountNumGameOrderDto();

		Long countNumId = dto.getCountNumId();
		Long userId = dto.getUserId();

		String countNumLockKey = countNumCacheDataService.getCountNumRedisLockKey(countNumId);
		Lock lock = factory.newLock(countNumLockKey);
		try {
			log.info("计数游戏组件:[上传游戏结果],开始获取锁,lockKey:{},计数游戏id:{}", countNumLockKey, countNumId);
			if (lock.tryLock(REDIS_TRY_LOCK_TIME, TimeUnit.SECONDS)) {
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
					countNumCacheDataService.setCountNumOrderRedisData(orderDto);
					countNumCacheDataService.setCountNumOrderOneRedisData(orderDto);

					// 发送事件
					countNumOrderService.pushEvent(order);

				} catch (BusinessException e) {
					log.info("计数游戏组件:[上传游戏结果],业务处理失败,lockKey:{},计数游戏id:{},{}", countNumLockKey, countNumId, e);
					throw new BusinessException(e.getMessage(), e.getCode());
				} finally {
					lock.unlock();
				}
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
		return dto;
	}

	@Override
	public List<CountNumGameOrderDto> findCountNumGameOrder(long countNumId) {
		List<CountNumGameOrderDto> dtoList = countNumCacheDataService.getCountNumRedisDataListByKey(countNumId);
		return dtoList;
	}

	@Override
	public Long countNumGameOrderNum(long userId, long countNumId) {
		List<CountNumGameOrderDto> countNumGameOrderDtoList = countNumCacheDataService
				.getCountNumOrderOneRedisData(countNumId, userId);
		int orderSize = countNumGameOrderDtoList.size();
		return (long) orderSize;
	}

	/**
	 * 校验用户参与游戏次数判断
	 * 
	 * @param countNumId
	 * @param userId
	 * @param countNumGameDto
	 */
	public void verifyUserLimieNum(Long countNumId, Long userId, CountNumGameDto countNumGameDto) {
		List<CountNumGameOrderDto> countNumGameOrderDtoList = countNumCacheDataService
				.getCountNumOrderOneRedisData(countNumId, userId);
		int orderSize = countNumGameOrderDtoList.size();
		log.info("[计数游戏组件],userId:{},countId:{},用户参与游戏次数：{}", userId, countNumId, orderSize);

		Long limitNum = countNumGameDto.getLimitNum();
		if ((limitNum > 0) && (limitNum >= orderSize)) {
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
				throw new BusinessException(CountNumGameResultCodeEnum.COUNT_NUM_OVERDUE.message(),
						CountNumGameResultCodeEnum.COUNT_NUM_OVERDUE.code());
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
			long totalNum = countNumOrderService.countByCountId(countNumId);
			double overstep = ((totalNum - rank) * 100.00 / totalNum);
			String overstepRate = String.format("%.2f", overstep);
			rankDto.setOverstepRate(overstepRate);
		}
		log.info("[计数游戏组件],countId:{},userId:{},用户排名：{}", countNumId, userId, rankDto);
		return rankDto;
	}

}
