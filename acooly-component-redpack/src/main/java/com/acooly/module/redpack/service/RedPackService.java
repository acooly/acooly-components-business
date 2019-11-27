/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by cuifuq
 * date:2019-03-05
 *
 */
package com.acooly.module.redpack.service;

import java.util.Date;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.redpack.entity.RedPack;

/**
 * 红包 Service接口
 *
 * Date: 2019-03-05 18:18:08
 * 
 * @author cuifuq
 *
 */
public interface RedPackService extends EntityService<RedPack> {

	public RedPack lockById(Long redPackId);

	/**
	 * 设置过期时间
	 * 
	 * @param redPackId
	 * @param overdueDate
	 * @return
	 */
	public RedPack setOverdueDate(long redPackId, Date overdueDate);

	/** 发布事件 **/
	public void pushEvent(RedPack redPack);

	/** 过期事件 **/
	public void pushEventOverdue(RedPack redPack);

	public void checkRedPackOverdue(RedPack redPack);
}
