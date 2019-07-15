/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by cuifuqiang
 * date:2017-02-03
 */
package com.acooly.module.point.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.Ids;
import com.acooly.module.point.dao.PointTradeDao;
import com.acooly.module.point.domain.PointAccount;
import com.acooly.module.point.domain.PointStatistics;
import com.acooly.module.point.domain.PointTrade;
import com.acooly.module.point.dto.PointTradeDto;
import com.acooly.module.point.enums.PointStaticsStatus;
import com.acooly.module.point.enums.PointTradeType;
import com.acooly.module.point.service.PointAccountService;
import com.acooly.module.point.service.PointStatisticsService;
import com.acooly.module.point.service.PointTradeService;
import com.acooly.module.point.service.PointTypeCountService;
import com.google.common.collect.Maps;

/**
 * 积分交易信息 Service实现
 * <p>
 * <p>
 * Date: 2017-02-03 22:50:14
 *
 * @author cuifuqiang
 */
@Service("pointTradeService")
public class PointTradeServiceImpl extends EntityServiceImpl<PointTrade, PointTradeDao> implements PointTradeService {

	private static final Logger logger = LoggerFactory.getLogger(PointTradeServiceImpl.class);

	@Autowired
	private PointAccountService pointAccountService;
	@Autowired
	private PointStatisticsService pointStatisticsService;
	@Autowired
	private PointTypeCountService pointTypeCountService;

    @Autowired
    @Qualifier("commonTaskExecutor")
    private TaskExecutor taskExecutor;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public PointTrade pointProduce(String userName, long point, PointTradeDto pointTradeDto) {
		PointAccount pointAccount = pointAccountService.pointProduce(userName, point);
		PointTrade pointTrade = saveTrade(pointAccount, point, PointTradeType.produce, pointTradeDto);
		return pointTrade;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public PointTrade pointExpense(String userName, long point, boolean isFreeze, PointTradeDto pointTradeDto) {
		PointAccount pointAccount = pointAccountService.pointExpense(userName, point, isFreeze);
		if (isFreeze) {
			saveTrade(pointAccount, point, PointTradeType.unfreeze, pointTradeDto);
		}
		PointTrade pointTrade = saveTrade(pointAccount, point, PointTradeType.expense, pointTradeDto);
		return pointTrade;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public PointTrade pointFreeze(String userName, long point, PointTradeDto pointTradeDto) {
		PointAccount pointAccount = pointAccountService.pointFreeze(userName, point);
		PointTrade pointTrade = saveTrade(pointAccount, point, PointTradeType.freeze, pointTradeDto);
		return pointTrade;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public PointTrade pointUnfreeze(String userName, long point, PointTradeDto pointTradeDto) {
		PointAccount pointAccount = pointAccountService.pointUnfreeze(userName, point);
		PointTrade pointTrade = saveTrade(pointAccount, point, PointTradeType.unfreeze, pointTradeDto);
		return pointTrade;
	}

	public long getClearPoint(String userName, String startTime, String endTime) {
		return getEntityDao().getClearPoint(userName, startTime, endTime);
	}

	public long getProducePoint(String userName, String startTime, String endTime) {
		return getEntityDao().getProducePoint(userName, startTime, endTime);
	}

	public long getExpensePoint(String userName, String startTime, String endTime) {
		return getEntityDao().getExpensePoint(userName, startTime, endTime);
	}

	public void pointClearThread(String startTime, String endTime, PointTradeDto pointTradeDto) {
		logger.info("启动新建线程,处理积分清零");
		taskExecutor.execute(() -> {
			try {
				Thread.sleep(3 * 1000);
			} catch (Exception e) {
				logger.warn("启动新建线程,处理积分清零失败");
			}
			pointClear(startTime, endTime, pointTradeDto);
		});
	}

	public void pointClear(String startTime, String endTime, PointTradeDto pointTradeDto) {
		pointStatisticsService.pointStatistics(startTime, endTime);
		PageInfo<PointStatistics> pageInfo = new PageInfo<PointStatistics>();
		Map<String, Object> maps = Maps.newHashMap();
		maps.put("GTE_startTime", startTime);
		maps.put("LTE_endTime", endTime);
		PageInfo<PointStatistics> pageInfos = pointStatisticsService.query(pageInfo, maps);
		long totalPage = pageInfos.getTotalPage();
		for (int i = 0; i < totalPage + 1; i++) {
			pageInfo.setCurrentPage(1);
			PageInfo<PointStatistics> pages = pointStatisticsService.query(pageInfo, maps);
			for (PointStatistics pointStatistics : pages.getPageResults()) {
				if (pointStatistics.getStatus() == PointStaticsStatus.init) {
					Long point = pointStatistics.getPoint();
					pointStatistics.setStatus(PointStaticsStatus.finish);
					PointAccount pointAccount = pointAccountService.findByUserName(pointStatistics.getUserName());
					Long availablePoint = pointAccount.getAvailable();
					if (availablePoint <= point) {
						pointAccount.setBalance(pointAccount.getBalance() - availablePoint);
						pointStatistics.setActualPoint(availablePoint);
					} else {
						pointAccount.setBalance(pointAccount.getBalance() - point);
						pointStatistics.setActualPoint(point);
					}
					pointAccountService.update(pointAccount);
					pointStatisticsService.update(pointStatistics);
					saveTrade(pointAccount, pointStatistics.getActualPoint(), PointTradeType.clear, pointTradeDto);
				}
			}
		}
	}

	private PointTrade saveTrade(PointAccount pointAccount, long point, PointTradeType tradeType,
			PointTradeDto pointTradeDto) {
		PointTrade pointTrade = new PointTrade();
		pointTrade.setTradeNo(Ids.oid());
		pointTrade.setAccountId(pointAccount.getId());
		pointTrade.setUserName(pointAccount.getUserName());
		pointTrade.setTradeType(tradeType);
		pointTrade.setAmount(point);
		pointTrade.setEndFreeze(pointAccount.getFreeze());
		pointTrade.setEndBalance(pointAccount.getBalance());
		pointTrade.setEndAvailable(pointAccount.getAvailable());
		if (null != pointTradeDto) {
			pointTrade.setBusiId(pointTradeDto.getBusiId());
			pointTrade.setBusiType(pointTradeDto.getBusiType());
			pointTrade.setBusiTypeText(pointTradeDto.getBusiTypeText());
			pointTrade.setBusiData(pointTradeDto.getBusiData());
			pointTrade.setMemo(pointTradeDto.getMemo());
		}
		getEntityDao().create(pointTrade);

		// 保存统计信息
		if (pointTrade.getTradeType() == PointTradeType.produce) {
			pointTypeCountService.savePointTypeCount(pointTrade);
		}

		return pointTrade;
	}
}
