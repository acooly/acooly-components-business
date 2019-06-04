package com.acooly.module.redpack.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acooly.module.redpack.business.service.RedPackReplyTradeService;
import com.acooly.module.redpack.entity.RedPack;
import com.acooly.module.redpack.entity.RedPackOrder;
import com.acooly.module.redpack.enums.RedPackOrderStatusEnum;
import com.acooly.module.redpack.enums.RedPackOrderTypeEnum;
import com.acooly.module.redpack.enums.RedPackStatusEnum;
import com.acooly.module.redpack.service.RedPackOrderService;
import com.acooly.module.redpack.service.RedPackService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("redPackReplyTradeService")
public class RedPackReplyTradeServiceImpl implements RedPackReplyTradeService {

	@Autowired
	private RedPackService redPackService;

	@Autowired
	private RedPackOrderService redPackOrderService;

	@Override
	@Transactional
	public RedPackOrder redPackPaying(Long redPackOrderId) {
		log.info("红包组件:[更新红包记录处理中]redPackOrderId：{}", redPackOrderId);
		RedPackOrder redPackOrder = redPackOrderService.lockById(redPackOrderId);
		redPackOrder.setStatus(RedPackOrderStatusEnum.PROCESSING);
		redPackOrderService.update(redPackOrder);
		return redPackOrder;
	}

	@Override
	@Transactional
	public RedPackOrder redPackPaySuccess(Long redPackOrderId) {
		log.info("红包组件:[更新红包记录为成功]redPackOrderId：{}", redPackOrderId);
		RedPackOrder redPackOrder = redPackOrderService.get(redPackOrderId);
		Long redPackId = redPackOrder.getRedPackId();

		// 锁表
		RedPack redPack = redPackService.lockById(redPackId);
		redPackOrder = redPackOrderService.lockById(redPackOrderId);
		redPackOrder.setStatus(RedPackOrderStatusEnum.SUCCESS);
		redPackOrderService.update(redPackOrder);

		RedPackStatusEnum oldStatus = redPack.getStatus();

		// 已经成功的条数
		long sumAmount = redPackOrderService.sumRedPackByRedPackIdAndStatus(redPackId, RedPackOrderStatusEnum.SUCCESS);
		log.info("红包组件:[更新红包记录为成功],redPackOrderId:{},红包总额:{},记录成功金额总和:{}", redPackId, redPack.getTotalAmount(),
				sumAmount);

		if (sumAmount == redPack.getTotalAmount()) {
			redPack.setStatus(RedPackStatusEnum.SUCCESS);

			// 退款红包数量
			Long count = redPackOrderService.countRedPackByRedPackIdAndStatusAndType(redPackId,
					RedPackOrderStatusEnum.SUCCESS, RedPackOrderTypeEnum.REFUND);
			log.info("红包组件:[更新红包记录为成功],redPackOrderId：{}redPackOrderId：成功退款的条数:{}", redPackId, redPackOrderId, count);
			if (count > 0) {
				redPack.setStatus(RedPackStatusEnum.REFUND_SUCCESS);
			}
		}

		log.info("红包组件:[更新红包记录为成功],redPack：{}", redPack);
		redPackService.update(redPack);

		// 是否发布事件
		if (oldStatus != redPack.getStatus()) {
			redPackService.pushEvent(redPack);
		}

		return redPackOrder;
	}

}
