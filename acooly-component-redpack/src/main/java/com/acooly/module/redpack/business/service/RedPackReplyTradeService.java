/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by acooly
 * date:2019-03-05
 *
 */
package com.acooly.module.redpack.business.service;

import com.acooly.module.redpack.entity.RedPackOrder;

/**
 * 红包结果处理
 *
 * Date: 2019-03-05 11:58:52
 * 
 * @author acooly
 *
 */
public interface RedPackReplyTradeService {

	/**
	 * 
	 * 红包支付中
	 * 
	 * <li>业务系统标记红包记录
	 * 
	 * @return
	 */
	RedPackOrder redPackPaying(Long redPackOrderId);

	/**
	 * 
	 * 红包支付完成
	 * 
	 * <li>业务系统标记红包记录
	 * 
	 * @return
	 */
	RedPackOrder redPackPaySuccess(Long redPackOrderId);

}
