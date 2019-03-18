/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by acooly
 * date:2019-03-05
 *
 */
package com.acooly.module.redpack.business.service;

import java.util.List;

import com.acooly.module.redpack.dto.CreateRedPackDto;
import com.acooly.module.redpack.dto.SendRedPackDto;
import com.acooly.module.redpack.event.dto.RedPackDto;
import com.acooly.module.redpack.event.dto.RedPackOrderDto;

/**
 * 红包 Service接口
 *
 * Date: 2019-03-05 11:58:52
 * 
 * @author acooly
 *
 */
public interface RedPackTradeService {

	/**
	 * 查询红包
	 * 
	 * <li>redis缓存
	 * 
	 * @return
	 */
	RedPackDto findRedPack(Long redPackId);

	/**
	 * 创建红包
	 * 
	 * <li>redis缓存
	 * <li>发布事件 RedPackEvent
	 * 
	 * @return
	 */
	RedPackDto createRedPack(CreateRedPackDto dto);

	/**
	 * 发送红包
	 * 
	 * <li>redis缓存
	 * <li>发布事件：RedPackOrderEvent
	 * 
	 * @return
	 */
	RedPackOrderDto sendRedPack(SendRedPackDto sendRedPackDto);

	/**
	 * 红包退款
	 * 
	 * <li>redis缓存
	 * <li>发布事件：RedPackOrderEvent
	 * 
	 * @return
	 */
	RedPackOrderDto refundRedPack(Long redPackId);

	/**
	 * 查询红包列表
	 * 
	 * <li>redis缓存
	 * 
	 * @return
	 */
	List<RedPackOrderDto> findRedPackOrder(Long redPackId);

}
