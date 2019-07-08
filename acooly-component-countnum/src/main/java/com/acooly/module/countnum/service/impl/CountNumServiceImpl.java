/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by cuifuq
 * date:2019-07-03
 */
package com.acooly.module.countnum.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.countnum.dao.CountNumDao;
import com.acooly.module.countnum.entity.CountNum;
import com.acooly.module.countnum.enums.CountNumStatusEnum;
import com.acooly.module.countnum.service.CountNumService;

/**
 * 游戏-计数 Service实现
 *
 * Date: 2019-07-03 11:48:59
 *
 * @author cuifuq
 *
 */
@Service("countNumService")
public class CountNumServiceImpl extends EntityServiceImpl<CountNum, CountNumDao> implements CountNumService {

	@Override
	@Transactional
	public CountNum lockById(Long countNumId) {
		return getEntityDao().lockById(countNumId);
	}

	@Override
	@Transactional
	public void checkCountNumOverdue(CountNum countNum) {
		countNum = lockById(countNum.getId());
		CountNumStatusEnum status = countNum.getStatus();
		if (status == CountNumStatusEnum.PROCESSING || status == CountNumStatusEnum.INIT) {
			// 校验过期
			Date overdueDateTime = countNum.getOverdueTime();
			if (overdueDateTime != null) {
				Long overdueTime = countNum.getOverdueTime().getTime();
				Long currentTime = (new Date()).getTime();
				if (currentTime > overdueTime) {
					countNum.setStatus(CountNumStatusEnum.FINISH);
					getEntityDao().update(countNum);
				}
			}
		}
	}

	@Override
	@Transactional
	public CountNum countNumGameFinish(Long countNumId) {
		CountNum countNum = lockById(countNumId);
		countNum.setStatus(CountNumStatusEnum.FINISH);
		getEntityDao().update(countNum);
		return countNum;
	}

	@Override
	public CountNum setOverdueDate(Long countNumId, Date overdueDate) {
		CountNum countNum = lockById(countNumId);
		countNum.setOverdueTime(overdueDate);
		getEntityDao().update(countNum);
		return countNum;
	}

}
