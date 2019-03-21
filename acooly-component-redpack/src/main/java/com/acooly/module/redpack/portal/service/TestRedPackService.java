/*
* acooly.cn Inc.
* Copyright (c) 2019 All Rights Reserved.
* create by cuifuq
* date:2019-03-05
*/
package com.acooly.module.redpack.portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acooly.core.utils.Ids;
import com.acooly.core.utils.Money;
import com.acooly.module.redpack.dto.SendRedPackDto;
import com.acooly.module.redpack.entity.RedPack;
import com.acooly.module.redpack.entity.RedPackOrder;
import com.acooly.module.redpack.enums.RedPackOrderTypeEnum;
import com.acooly.module.redpack.enums.RedPackStatusEnum;
import com.acooly.module.redpack.event.dto.RedPackOrderDto;
import com.acooly.module.redpack.event.order.InsideRedPackOrderEvent;
import com.acooly.module.redpack.service.RedPackOrderService;
import com.acooly.module.redpack.service.RedPackService;
import com.acooly.module.redpack.utils.RedPackUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 红包 管理控制器
 * 
 * @author cuifuq Date: 2019-03-05 18:18:08
 */
@Profile("!online")
@Slf4j
@Service("testRedPackService")
public class TestRedPackService {

	@Autowired
	private RedPackService redPackService;

	@Autowired
	private RedPackOrderService redPackOrderService;

	@Transactional
	public void sendRedPack(SendRedPackDto dto) {

		RedPack redPack = redPackService.lockById(dto.getRedPackId());
		long totalAmount = redPack.getTotalAmount();

		long surplusAmount = totalAmount - redPack.getSendOutAmount();
		long surplusNum = redPack.getTotalNum() - redPack.getSendOutNum();
		long redAmount = RedPackUtils.redPack("" + dto.getRedPackId(), totalAmount, surplusAmount, surplusNum);

		// 红包订单
		RedPackOrder order = new RedPackOrder();
		order.setOrderNo(Ids.oid());
		order.setRedPackId(dto.getRedPackId());
		order.setRedPackTitle(redPack.getTitle());
		order.setSendUserId(redPack.getSendUserId());
		order.setSendUserName(redPack.getSendUserName());
		order.setUserId(dto.getUserId());
		order.setUserName(dto.getUserName());
		order.setAmount(redAmount);
		order.setType(RedPackOrderTypeEnum.RED_PACK);
		redPackOrderService.save(order);

		// 红包支付
		redPack.setSendOutAmount(redPack.getSendOutAmount() + redAmount);
		redPack.setSendOutNum(redPack.getSendOutNum() + 1);
		redPack.setStatus(RedPackStatusEnum.PROCESSING);
		redPackService.update(redPack);

		log.info("更新红包记录-redPackId:{},总金额 :{},已发送金额:{},剩余金额:{},剩余数量:{},红包金额：{}", order.getRedPackId(),
				Money.cent(totalAmount), Money.cent(redPack.getSendOutAmount()), Money.cent(surplusAmount), surplusNum,
				Money.cent(order.getAmount()));

	}

}
