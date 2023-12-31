/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by acooly
 * date:2019-03-05
 *
 */
package com.acooly.module.redpack.business.service;

import java.util.Date;
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
	 * 红包游戏过期时间设置，(到达过期时间，自动结束)
	 * 
	 * 
	 * <li>游戏结束后，不再处理提交的游戏结果
	 * 
	 * @return
	 */
	RedPackDto setRedPackOverdueTime(long redPackId, Date overdueDate);

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
	@Deprecated
	List<RedPackOrderDto> findRedPackOrder(Long redPackId);

	/**
	 * 查询红包列表
	 *
	 * @param redPackId 红包id
	 * @param sort      true:降序(从大到小);flase:升序(从小到大);null:（默认id倒序）
	 * @return
	 */
	List<RedPackOrderDto> findRedPackOrderSort(Long redPackId, Boolean sort);

	/**
	 * 统计红包领取个数
	 * 
	 * @param userId
	 * @param redPackId
	 * @return
	 */
	Long coutRedOrderNum(long userId, long redPackId);

}
