/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by acooly
 * date:2019-03-05
 *
 */
package com.acooly.module.countnum.business.service;

import java.util.Date;
import java.util.List;

import com.acooly.module.countnum.dto.CountNumGameDto;
import com.acooly.module.countnum.dto.CountNumGameOrderDto;
import com.acooly.module.countnum.dto.CountNumGameOrderRankDto;
import com.acooly.module.countnum.dto.order.CountNumGameResultDto;
import com.acooly.module.countnum.dto.order.CreateCountNumGameDto;

/**
 * 计数游戏-服务接口
 * 
 * @author CuiFuQ
 *
 */
public interface CountNumGameService {

	/**
	 * 查询计数游戏
	 * 
	 * @return
	 */
	CountNumGameDto findCountNum(Long countNumId);

	/**
	 * 创建计数游戏
	 * 
	 * @return
	 */
	CountNumGameDto createCountNumGame(CreateCountNumGameDto dto);

	/**
	 * 提交游戏结果
	 * 
	 * @return
	 */
	CountNumGameOrderDto submitCountNumGameResult(CountNumGameResultDto countNumGameResultDto);

	/**
	 * 计数游戏结束(立即结束)
	 * 
	 * <li>游戏结束后，不再处理提交的游戏结果
	 * 
	 * @return
	 */
	CountNumGameDto countNumGameFinish(long countNumId);

	/**
	 * 计数游戏结束(到达过期时间，自动结束)
	 * 
	 * <li>游戏结束后，不再处理提交的游戏结果
	 * 
	 * @return
	 */
	CountNumGameDto countNumGameOverdueFinish(long countNumId, Date overdueDate);

	/**
	 * 查询计数游戏记录列表
	 * 
	 */
	List<CountNumGameOrderDto> findCountNumGameOrder(long countNumId);

	/**
	 * 用户游戏排名(返回用户名次)
	 * 
	 * @param userId
	 * @param countNumId
	 * @param isOverstep
	 * 
	 *                   true:计算用户排名比例，false:不计算比例）
	 * @return
	 */
	CountNumGameOrderRankDto userRanking(long userId, long countNumId, boolean isOverstep);

	/**
	 * 统计用户参与的次数
	 * 
	 * @param userId
	 * @param countNumId
	 * @return
	 */
	Long countNumGameOrderNum(long userId, long countNumId);

}
