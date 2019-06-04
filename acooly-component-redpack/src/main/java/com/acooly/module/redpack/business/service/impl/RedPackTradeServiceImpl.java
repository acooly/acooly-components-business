package com.acooly.module.redpack.business.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.Money;
import com.acooly.module.distributedlock.DistributedLockFactory;
import com.acooly.module.event.EventBus;
import com.acooly.module.redpack.business.service.RedPackTradeService;
import com.acooly.module.redpack.business.service.cache.RedPackCacheDataService;
import com.acooly.module.redpack.business.service.conver.RedPackEntityConverDto;
import com.acooly.module.redpack.dto.CreateRedPackDto;
import com.acooly.module.redpack.dto.SendRedPackDto;
import com.acooly.module.redpack.entity.RedPack;
import com.acooly.module.redpack.enums.RedPackOrderTypeEnum;
import com.acooly.module.redpack.enums.RedPackStatusEnum;
import com.acooly.module.redpack.enums.result.RedPackResultCodeEnum;
import com.acooly.module.redpack.event.dto.RedPackDto;
import com.acooly.module.redpack.event.dto.RedPackOrderDto;
import com.acooly.module.redpack.event.order.InsideRedPackOrderEvent;
import com.acooly.module.redpack.service.RedPackOrderService;
import com.acooly.module.redpack.service.RedPackService;
import com.acooly.module.redpack.utils.RedPackUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("redPackTradeService")
public class RedPackTradeServiceImpl implements RedPackTradeService {

	/** redis尝试锁，时间设置 **/
	public static Integer REDIS_TRY_LOCK_TIME = 1;

	@Autowired
	private RedPackService redPackService;

	@Autowired
	private RedPackOrderService redPackOrderService;

	@Autowired
	private RedPackCacheDataService redPackCacheDataService;

	@Autowired
	private DistributedLockFactory factory;

	@SuppressWarnings({ "unused", "rawtypes" })
	@Autowired
	private RedisTemplate redisTemplate;

	@SuppressWarnings("rawtypes")
	@Autowired
	private EventBus eventBus;

	@Override
	public RedPackDto findRedPack(Long redPackId) {
		RedPackDto dto = redPackCacheDataService.getRedPackRedisDataByKey(redPackId);
		return dto;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public RedPackDto createRedPack(CreateRedPackDto createDto) {
		RedPackDto dto = new RedPackDto();

		// 红包总额小于红包数量
		if (createDto.getTotalAmount() < createDto.getTotalNum()) {
			throw new BusinessException(RedPackResultCodeEnum.RED_PACK_NOT_ENOUGH_NUM.message(),
					RedPackResultCodeEnum.RED_PACK_NOT_ENOUGH_NUM.code());
		}

		try {

			RedPack redPack = new RedPack();
			redPack.setTitle(createDto.getTitle());
			redPack.setSendUserId(createDto.getSendUserId());
			redPack.setSendUserName(createDto.getSendUserName());
			redPack.setOverdueTime(createDto.getOverdueTime());
			redPack.setTotalAmount(createDto.getTotalAmount());
			redPack.setTotalNum(createDto.getTotalNum());
			redPack.setRemark(createDto.getRemark());
			redPack.setPartakeNum(createDto.getPartakeNum());
			redPack.setBusinessId(createDto.getBusinessId());
			redPack.setBusinessData(createDto.getBusinessData());
			redPackService.save(redPack);

			// 设置红包缓存，并发布红包事件
			dto = RedPackEntityConverDto.converRedPackDto(redPack, dto);
			redPackCacheDataService.setRedPackRedisData(dto);

			// 发布事件
			redPackService.pushEvent(redPack);

		} catch (Exception e) {
			log.error("红包组件:创建红包失败:{}", e);
			throw new BusinessException(RedPackResultCodeEnum.RED_PACK_CREATE_ERROR.message(),
					RedPackResultCodeEnum.RED_PACK_CREATE_ERROR.code());
		}
		return dto;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public RedPackOrderDto sendRedPack(SendRedPackDto sendDto) {
		RedPackOrderDto orderDto = new RedPackOrderDto();
		Long redPackId = sendDto.getRedPackId();
		String redPackLockKey = redPackCacheDataService.getRedPackLockKey(redPackId);

		Lock lock = factory.newLock(redPackLockKey);
		try {
			log.info("红包组件:[发送红包],开始获取锁,lockKey:{},红包id:{}", redPackLockKey, redPackId);
			if (lock.tryLock(REDIS_TRY_LOCK_TIME, TimeUnit.SECONDS)) {
				try {
					RedPackDto redPackDto = redPackCacheDataService.getRedPackRedisDataByKey(redPackId);

					// 红包状态校验
					checkRedPackStatus(redPackDto);

					// 用户允许参与的最大次数
					checkUserPartakeNum(redPackDto, sendDto);

					// 校验过期
					Date overdueDateTime = redPackDto.getOverdueTime();
					if (overdueDateTime != null) {
						Long overdueTime = redPackDto.getOverdueTime().getTime();
						Long currentTime = (new Date()).getTime();
						if (currentTime > overdueTime) {
							throw new BusinessException(RedPackResultCodeEnum.RED_PACK_OVERDUE.message(),
									RedPackResultCodeEnum.RED_PACK_OVERDUE.code());
						}
					}

					// 红包算法
					long totalAmount = redPackDto.getTotalAmount();
					long surplusAmount = totalAmount - redPackDto.getSendOutAmount() - redPackDto.getRefundAmount();
					long surplusNum = redPackDto.getTotalNum() - redPackDto.getSendOutNum();

					Long redPackAmount = RedPackUtils.redPack(redPackCacheDataService.getRedPackRedisKey(redPackId),
							totalAmount, surplusAmount, surplusNum);

					redPackDto.setSendOutAmount(redPackDto.getSendOutAmount() + redPackAmount);
					redPackDto.setSendOutNum(redPackDto.getSendOutNum() + 1);
					redPackDto.setStatus(RedPackStatusEnum.PROCESSING);

					log.info("红包组件:[发送红包]lockKey:{},红包id:{},领取人 userId:{},userName:{},领取金额：{}", redPackLockKey,
							redPackId, sendDto.getUserId(), sendDto.getUserName(), Money.cent(redPackAmount));

					// 组建order订单
					orderDto = buildSendRedPackOrderDto(sendDto, redPackId, redPackAmount);

					// 设置红包缓存数据（红包，红包订单）
					redPackCacheDataService.setRedPackRedisData(redPackDto);
					redPackCacheDataService.setRedPackOrderRedisData(orderDto);

					// 发红包事件
					InsideRedPackOrderEvent insideEvent = new InsideRedPackOrderEvent(orderDto);
					eventBus.publishAfterTransactionCommitted(insideEvent);

				} catch (BusinessException e) {
					log.info("红包组件:[发送红包],业务处理失败,lockKey:{},红包id:{},{}", redPackLockKey, redPackId, e);
					throw new BusinessException(e.getMessage(), e.getCode());
				} finally {
					lock.unlock();
				}
			} else {
				log.info("红包组件:[发送红包],获取锁失败,lockKey:{},红包id:{}", redPackLockKey, redPackId);
				throw new BusinessException(RedPackResultCodeEnum.RED_PACK_SEND_LOCK_ERROR.message(),
						RedPackResultCodeEnum.RED_PACK_SEND_LOCK_ERROR.code());
			}
		} catch (BusinessException e) {
			throw new BusinessException(e.getMessage(), e.getCode());
		} catch (Exception e) {
			log.error("红包组件:[发送红包]发送红包失败,红包id:{},{}", redPackId, e);
			throw new BusinessException("红包组件:[发送红包]发送红包失败");
		}

		return orderDto;
	}

	/**
	 * 退款
	 */
	public RedPackOrderDto refundRedPack(Long redPackId) {
		RedPackOrderDto orderDto = new RedPackOrderDto();

		String redPackLockKey = redPackCacheDataService.getRedPackLockKey(redPackId);
		Lock lock = factory.newLock(redPackLockKey);
		try {

			log.info("红包组件:[红包退款],开始获取锁,lockKey:{},红包id:{}", redPackLockKey, redPackId);
			if (lock.tryLock(REDIS_TRY_LOCK_TIME, TimeUnit.SECONDS)) {

				try {
					RedPackDto redPackDto = redPackCacheDataService.getRedPackRedisDataByKey(redPackId);

					checkRedPackStatus(redPackDto);

					// 红包算法
					long totalAmount = redPackDto.getTotalAmount();
					long surplusAmount = totalAmount - redPackDto.getSendOutAmount() - redPackDto.getRefundAmount();

					// 红包已经完结
					if (surplusAmount == 0) {
						throw new BusinessException(RedPackResultCodeEnum.RED_PACK_ALREADY_FINISH.message(),
								RedPackResultCodeEnum.RED_PACK_ALREADY_FINISH.code());
					}

					redPackDto.setRefundAmount(surplusAmount);
					redPackDto.setStatus(RedPackStatusEnum.REFUNDING);

					log.info("红包组件:[退款]lockKey:{},红包id:{},退款人 userId:{},userName:{},退款金额：{}", redPackLockKey, redPackId,
							redPackDto.getSendUserId(), redPackDto.getSendUserName(), Money.cent(surplusAmount));

					// 组建order订单
					orderDto = buildRefundRedPackOrderDto(redPackDto);

					// 设置红包缓存数据（红包，红包订单）
					redPackCacheDataService.setRedPackRedisData(redPackDto);
					redPackCacheDataService.setRedPackOrderRedisData(orderDto);

					// 发红包事件
					InsideRedPackOrderEvent insideEvent = new InsideRedPackOrderEvent(orderDto);
					eventBus.publishAfterTransactionCommitted(insideEvent);

				} catch (BusinessException e) {
					throw new BusinessException(e.getMessage(), e.getCode());
				} finally {
					lock.unlock();
				}

			} else {
				log.info("红包组件:[红包退款],获取锁失败,lockKey:{},红包id:{}", redPackLockKey, redPackId);
				throw new BusinessException(RedPackResultCodeEnum.RED_PACK_SEND_LOCK_ERROR.message(),
						RedPackResultCodeEnum.RED_PACK_SEND_LOCK_ERROR.code());
			}

		} catch (BusinessException e) {
			throw new BusinessException(e.getMessage(), e.getCode());
		} catch (Exception e) {
			log.error("红包组件:[红包退款]红包退款失败,红包id:{},{}", redPackId, e);
			throw new BusinessException("红包组件:[红包退款]红包退款失败");
		}

		return orderDto;
	}

	@Override
	public List<RedPackOrderDto> findRedPackOrder(Long redPackId) {
		List<RedPackOrderDto> orderDtoList = redPackCacheDataService.getRedPackRedisDataListByKey(redPackId);
		return orderDtoList;
	}

	@Override
	public List<RedPackOrderDto> findRedPackOrderSort(Long redPackId, Boolean sort) {
		List<RedPackOrderDto> orderDtoList = findRedPackOrder(redPackId);
		if (sort == null) {
			return orderDtoList;
		}

		if (orderDtoList != null) {
			Collections.sort(orderDtoList, new Comparator<RedPackOrderDto>() {
				public int compare(RedPackOrderDto list1, RedPackOrderDto list2) {
					long amount1 = list1.getAmount();
					long amount2 = list2.getAmount();

					// 金额相同，按照时间先后 升序
					if (amount1 == amount2) {
						return list1.getCreateTime().compareTo(list2.getCreateTime());
					}

					if (sort) {
						// 降序
						return list2.getAmount().compareTo(list1.getAmount());
					} else {
						// 升序
						return list1.getAmount().compareTo(list2.getAmount());
					}
				}
			});
		}
		return orderDtoList;
	}

	/**
	 * 组建order订单
	 * 
	 * @param sendDto
	 * @param redPackId
	 * @param redPackAmount
	 * @return
	 */
	private RedPackOrderDto buildSendRedPackOrderDto(SendRedPackDto sendDto, Long redPackId, Long redPackAmount) {
		RedPackOrderDto orderDto;
		orderDto = new RedPackOrderDto();
		orderDto.setRedPackId(redPackId);
		orderDto.setUserId(sendDto.getUserId());
		orderDto.setUserName(sendDto.getUserName());
		orderDto.setAmount(redPackAmount);
		orderDto.setType(RedPackOrderTypeEnum.RED_PACK);
		orderDto.setCreateTime(new Date());
		return orderDto;
	}

	/**
	 * 组建order订单
	 * 
	 * @param sendDto
	 * @param redPackId
	 * @param redPackAmount
	 * @return
	 */
	private RedPackOrderDto buildRefundRedPackOrderDto(RedPackDto redPackDto) {
		RedPackOrderDto orderDto;
		orderDto = new RedPackOrderDto();
		orderDto.setRedPackId(redPackDto.getRedPackId());
		orderDto.setUserId(redPackDto.getSendUserId());
		orderDto.setUserName(redPackDto.getSendUserName());
		orderDto.setAmount(redPackDto.getRefundAmount());
		orderDto.setType(RedPackOrderTypeEnum.REFUND);
		return orderDto;
	}

	/**
	 * 用户允许参与的最大次数
	 * 
	 * @param redPackDto
	 * @param dto
	 */
	private void checkUserPartakeNum(RedPackDto redPackDto, SendRedPackDto sendRedPackDto) {
		if (redPackDto.getPartakeNum().equals(0L)) {
			return;
		}
		Long redPackId = redPackDto.getRedPackId();
		Long userId = sendRedPackDto.getUserId();

		List<RedPackOrderDto> sendRedPackEventList = redPackCacheDataService.getRedPackRedisDataMapByKey(redPackId,
				userId);
		int size = sendRedPackEventList.size();
		log.info("红包组件:redPackId:{}获取用户已经领取红包个数：{}", redPackId, size);
		if (size >= redPackDto.getPartakeNum()) {
			throw new BusinessException("领取超过最大领取次数", RedPackResultCodeEnum.RED_PACK_PASS_MAX_NUM.code());
		}
	}

	/**
	 * 红包状态校验
	 * 
	 * @param redPackDto
	 */
	private void checkRedPackStatus(RedPackDto redPackDto) {
		if (redPackDto == null) {
			throw new BusinessException("红包不存在", RedPackResultCodeEnum.RED_PACK_NOT_EXISTING.code());
		}

		// 红包支付中
		if (redPackDto.getStatus() == RedPackStatusEnum.REFUNDING) {
			throw new BusinessException(RedPackResultCodeEnum.RED_PACK_REFUNDING.message(),
					RedPackResultCodeEnum.RED_PACK_REFUNDING.code());
		}

		// 红包已经完结
		if (redPackDto.getStatus() == RedPackStatusEnum.SUCCESS
				|| redPackDto.getStatus() == RedPackStatusEnum.REFUND_SUCCESS) {
			throw new BusinessException(RedPackResultCodeEnum.RED_PACK_ALREADY_FINISH.message(),
					RedPackResultCodeEnum.RED_PACK_ALREADY_FINISH.code());
		}
	}

	@Override
	public Long coutRedOrderNum(long userId, long redPackId) {
		return redPackOrderService.coutRedOrderNum(userId, redPackId);
	}

}
