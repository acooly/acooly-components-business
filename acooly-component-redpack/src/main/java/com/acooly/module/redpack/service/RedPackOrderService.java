/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by cuifuq
 * date:2019-03-05
 *
 */
package com.acooly.module.redpack.service;

import java.util.List;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.redpack.entity.RedPack;
import com.acooly.module.redpack.entity.RedPackOrder;
import com.acooly.module.redpack.enums.RedPackOrderStatusEnum;
import com.acooly.module.redpack.enums.RedPackOrderTypeEnum;

/**
 * 红包订单 Service接口
 *
 * Date: 2019-03-05 18:18:09
 * 
 * @author cuifuq
 *
 */
public interface RedPackOrderService extends EntityService<RedPackOrder> {

	RedPackOrder lockById(long redPackOrderId);

	List<RedPackOrder> findByRedPackId(Long redPackId);

	List<RedPackOrder> findByRedPackIdAndUserId(Long redPackId, Long userId);

	/**
	 * 更新是否未（手气最佳）
	 * 
	 * @param redPackId
	 */
	void updateIsFirst(Long redPackId);

	// 发布事件
	void pushEvent(RedPack redPack, RedPackOrder redPackOrder);

	public Long countRedPackByRedPackIdAndStatusAndType(Long redPackId, RedPackOrderStatusEnum status,
			RedPackOrderTypeEnum type);

	Long sumRedPackByRedPackIdAndStatus(Long redPackId, RedPackOrderStatusEnum success);

	/**
	 * 统计所有订单记录金额总额,排除 redPackOrderId
	 * 
	 * @param redPackOrderId
	 * @param redPackId
	 * @param success
	 * @return
	 */
	Long sumRedPackByRedPackIdAndStatusNotId(Long redPackOrderId, Long redPackId, RedPackOrderStatusEnum success);

	/**
	 * 统计红包领取个数
	 * 
	 * @param userId
	 * @param redPackId
	 * @return
	 */
	Long coutRedOrderNum(long userId, long redPackId);

	void newUpdate(Long redPackOrderId);

}
