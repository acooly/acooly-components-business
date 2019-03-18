/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by cuifuq
 * date:2019-03-05
 *
 */
package com.acooly.module.redpack.service;

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

	RedPack lockById(Long redPackId);

	// 发布事件
	void pushEvent(RedPack redPack);

	void checkRedPackOverdue(RedPack redPack);
}
