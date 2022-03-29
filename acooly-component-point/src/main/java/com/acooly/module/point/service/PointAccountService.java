/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by cuifuqiang
 * date:2017-02-03
 *
 */
package com.acooly.module.point.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.point.entity.PointAccount;

/**
 * 积分账户 Service接口
 *
 * <p>
 * Date: 2017-02-03 22:45:12
 *
 * @author cuifuqiang
 */
public interface PointAccountService extends EntityService<PointAccount> {

	/**
	 * 同步积分账户
	 * 
	 * @param userNo
	 * @param userName
	 * @return
	 */
	PointAccount syncPointAccount(String userNo, String userName);

	PointAccount findByUserNo(String userNo);

	/**
	 * 积分产生
	 * 
	 * @param userNo
	 * @param userName
	 * @param point
	 * @return
	 */
	PointAccount pointProduce(String userNo, String userName, long point);

	/**
	 * 积分到期清零
	 * 
	 * @param userNo
	 * @param userName
	 * @param totalClearPoint 本期清零积分
	 * @return
	 */
	PointAccount pointOverdueClear(String userNo, String userName, long totalClearPoint);

	/**
	 * 积分消费
	 * 
	 * @param userNo
	 * @param userName
	 * @param point
	 * @param isFreeze
	 * @return
	 */
	PointAccount pointExpense(String userNo, String userName, long point, boolean isFreeze);

	/**
	 * 积分消费-退款
	 * 
	 * @param userNo
	 * @param userName
	 * @param point
	 * @return
	 */
	PointAccount pointExpenseByCancel(String userNo, String userName, long point);

	/**
	 * 积分冻结
	 * 
	 * @param userNo
	 * @param userName
	 * @param point
	 * @return
	 */
	PointAccount pointFreeze(String userNo, String userName, long point);

	/**
	 * 积分解冻
	 * 
	 * @param userNo
	 * @param userName
	 * @param point
	 * @return
	 */
	PointAccount pointUnfreeze(String userNo, String userName, long point);

	/**
	 * 同一个等级的排名(用户所在等级的位置)
	 *
	 * @param userName
	 * @param gradeId
	 * @return
	 */
	public int pointRank(String userNo, Long gradeId);

	/**
	 * 根据用户名查询积分账户 并上锁
	 *
	 * @param userName
	 * @return
	 */
	PointAccount lockByUserNo(String userNo);

	/**
	 * 计算用户清零积分
	 * 
	 * @param userNo
	 * @param totalClearPoint
	 * @return
	 */
	public long cleanPointByCount(String userNo, long totalClearPoint);

}
