/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-08-06
 */
package com.acooly.module.point.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.point.dao.PointTypeCountDao;
import com.acooly.module.point.entity.PointTrade;
import com.acooly.module.point.entity.PointTypeCount;
import com.acooly.module.point.enums.PointTradeType;
import com.acooly.module.point.service.PointTypeCountService;

/**
 * 积分业务统计 Service实现
 *
 * Date: 2018-08-06 16:18:40
 *
 * @author acooly
 *
 */
@Service("pointTypeCountService")
public class PointTypeCountServiceImpl extends EntityServiceImpl<PointTypeCount, PointTypeCountDao>
		implements PointTypeCountService {

	@Override
	@Transactional
	public void savePointTypeCount(PointTrade pointTrade) {
		String userNo = pointTrade.getUserNo();
		String userName = pointTrade.getUserName();
		String busiType = pointTrade.getBusiType();
		String busiTypeText = pointTrade.getBusiTypeText();

		if (pointTrade.getTradeType() == PointTradeType.produce && StringUtils.isNotBlank(busiType)
				&& StringUtils.isNotBlank(busiTypeText)) {
			PointTypeCount pointTypeCount = getEntityDao().lockUserNoAndBusiType(userNo, busiType);
			if (null == pointTypeCount) {
				pointTypeCount = new PointTypeCount();
				pointTypeCount.setUserNo(userNo);
				pointTypeCount.setUserName(userName);
				pointTypeCount.setNum(1L);
				pointTypeCount.setBusiType(busiType);
				pointTypeCount.setBusiTypeText(pointTrade.getBusiTypeText());
				pointTypeCount.setTotalPoint(pointTrade.getAmount());
				getEntityDao().create(pointTypeCount);
				return;
			}
			pointTypeCount.setNum(pointTypeCount.getNum() + 1);
			pointTypeCount.setTotalPoint(pointTypeCount.getTotalPoint() + pointTrade.getAmount());
			getEntityDao().update(pointTypeCount);
		}
	}

}
