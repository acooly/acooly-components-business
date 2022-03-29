/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by cuifuqiang
 * date:2017-02-03
 */
package com.acooly.module.point.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.event.EventBus;
import com.acooly.module.point.PointProperties;
import com.acooly.module.point.dao.PointGradeDao;
import com.acooly.module.point.entity.PointAccount;
import com.acooly.module.point.entity.PointGrade;
import com.acooly.module.point.event.PointAccountChangeEvent;
import com.acooly.module.point.service.PointGradeService;

/**
 * 积分等级 Service实现
 *
 * <p>
 * Date: 2017-02-03 22:47:28
 *
 * @author cuifuqiang
 */
@Service("pointGradeService")
public class PointGradeServiceImpl extends EntityServiceImpl<PointGrade, PointGradeDao> implements PointGradeService {

	@Autowired
	private EventBus eventBus;

	@Autowired
	private PointProperties pointProperties;

	@Override
	public PointGrade getSectionPoint(PointAccount pointAccount) {
		String pointGradeMark = pointProperties.getPointGradeBasis();
		long point = pointAccount.getBalance();
		if (pointGradeMark.equals("totalPoint")) {
			point = pointAccount.getTotalProducePoint();
		}
		PointGrade pointGrade = getEntityDao().getSectionPoint(point);
		if (pointGrade == null) {
			throw new BusinessException("未找到匹配的积分用户等级");
		}
		PointAccountChangeEvent pointEvent = new PointAccountChangeEvent();
		pointEvent.setUserNo(pointAccount.getUserNo());
		pointEvent.setUserName(pointAccount.getUserName());
		pointEvent.setAvailable(pointAccount.getAvailable());
		pointEvent.setBalance(pointAccount.getBalance());
		pointEvent.setFreeze(pointAccount.getFreeze());
		pointEvent.setTotalExpensePoint(pointAccount.getTotalExpensePoint());
		pointEvent.setTotalProducePoint(pointAccount.getTotalProducePoint());
		pointEvent.setGradeId(pointGrade.getId());
		pointEvent.setGradeTitle(pointGrade.getTitle());
		if (pointAccount.getDebtPoint() > 0) {
			pointEvent.setAvailable(pointAccount.getAvailable() - pointAccount.getDebtPoint());
		}
		eventBus.publishAfterTransactionCommitted(pointEvent);
		return pointGrade;
	}
}
