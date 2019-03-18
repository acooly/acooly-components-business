package com.acooly.module.redpack.event.handle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.acooly.core.utils.Ids;
import com.acooly.core.utils.Money;
import com.acooly.module.event.EventHandler;
import com.acooly.module.redpack.entity.RedPack;
import com.acooly.module.redpack.entity.RedPackOrder;
import com.acooly.module.redpack.enums.RedPackOrderTypeEnum;
import com.acooly.module.redpack.enums.RedPackStatusEnum;
import com.acooly.module.redpack.event.dto.RedPackOrderDto;
import com.acooly.module.redpack.event.order.InsideRedPackOrderEvent;
import com.acooly.module.redpack.service.RedPackOrderService;
import com.acooly.module.redpack.service.RedPackService;

import lombok.extern.slf4j.Slf4j;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Invoke;

@Slf4j
@EventHandler
public class InsideRedPackOrderEventHandle {

	@Autowired
	private RedPackService redPackService;

	@Autowired
	private RedPackOrderService redPackOrderService;

	// 异步事件处理器
	@Handler(delivery = Invoke.Asynchronously)
	@Transactional
	public void handleInsideRedPackOrderEventAsyn(InsideRedPackOrderEvent insideRedPackOrderEvent) {
		RedPackOrderDto redPackOrderDto = insideRedPackOrderEvent.getRedPackOrderDto();

		RedPackOrderTypeEnum type = redPackOrderDto.getType();

		RedPack redPack = redPackService.lockById(redPackOrderDto.getRedPackId());
		long totalAmount = redPack.getTotalAmount();

		// 红包订单
		RedPackOrder order = new RedPackOrder();
		order.setOrderNo(Ids.oid());
		order.setRedPackId(redPackOrderDto.getRedPackId());
		order.setRedPackTitle(redPack.getTitle());
		order.setSendUserId(redPack.getSendUserId());
		order.setSendUserName(redPack.getSendUserName());
		order.setUserId(redPackOrderDto.getUserId());
		order.setUserName(redPackOrderDto.getUserName());
		order.setAmount(redPackOrderDto.getAmount());
		order.setType(type);
		redPackOrderService.save(order);

		// 红包支付
		if (type == RedPackOrderTypeEnum.RED_PACK) {
			redPack.setSendOutAmount(redPack.getSendOutAmount() + redPackOrderDto.getAmount());
			redPack.setSendOutNum(redPack.getSendOutNum() + 1);
			redPack.setStatus(RedPackStatusEnum.PROCESSING);
		}

		// 退款
		if (type == RedPackOrderTypeEnum.REFUND) {
			redPack.setRefundAmount(redPackOrderDto.getAmount());
			redPack.setStatus(RedPackStatusEnum.REFUNDING);
		}
		redPackService.update(redPack);

		// 发布红包事件
//		redPackService.pushEvent(redPack);
		redPackOrderService.pushEvent(redPack, order);

		// 剩余数量
		long surplusAmount = totalAmount - redPack.getSendOutAmount();
		long surplusNum = redPack.getTotalNum() - redPack.getSendOutNum();

		log.info("更新红包记录-redPackId:{},总金额 :{},已发送金额:{},剩余金额:{},剩余数量:{},红包金额：{}", redPackOrderDto.getRedPackId(),
				Money.cent(totalAmount), Money.cent(redPack.getSendOutAmount()), Money.cent(surplusAmount), surplusNum,
				Money.cent(redPackOrderDto.getAmount()));

	}

}
