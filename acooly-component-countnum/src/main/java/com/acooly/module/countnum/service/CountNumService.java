/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by cuifuq
 * date:2019-07-03
 *
 */
package com.acooly.module.countnum.service;

import java.util.Date;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.countnum.entity.CountNum;

/**
 * 游戏-计数 Service接口
 *
 * Date: 2019-07-03 11:48:59
 * 
 * @author cuifuq
 *
 */
public interface CountNumService extends EntityService<CountNum> {

	/**
	 * 查询计数实体
	 * 
	 * @param countNumId
	 * @return
	 */
	CountNum lockById(Long countNumId);

	/**
	 * 校验过期时间
	 * 
	 * @param countNum
	 */
	void checkCountNumOverdue(CountNum countNum);

	/**
	 * 游戏结束
	 * 
	 * @param countNumId
	 * @return
	 */
	CountNum countNumGameFinish(Long countNumId);

	/**
	 * 设置过期时间
	 * 
	 * @param countNumId
	 * @return
	 */
	CountNum setOverdueDate(Long countNumId, Date overdueDate);

}
