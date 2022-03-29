/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by acooly
 * date:2017-03-13
 */
package com.acooly.module.point.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.Dates;
import com.acooly.module.point.dao.PointStatisticsDao;
import com.acooly.module.point.dao.PointStatisticsExtDao;
import com.acooly.module.point.dto.PointTradeInfoDto;
import com.acooly.module.point.entity.PointAccount;
import com.acooly.module.point.entity.PointStatistics;
import com.acooly.module.point.enums.PointStaticsStatus;
import com.acooly.module.point.enums.PointTradeType;
import com.acooly.module.point.service.PointAccountService;
import com.acooly.module.point.service.PointStatisticsService;
import com.acooly.module.point.service.PointTradeService;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * 积分统计 Service实现
 *
 * <p>
 * Date: 2017-03-13 11:51:11
 *
 * @author acooly
 */
@Slf4j
@Service("pointStatisticsService")
public class PointStatisticsServiceImpl extends EntityServiceImpl<PointStatistics, PointStatisticsDao>
		implements PointStatisticsService {

	@Autowired
	private PointStatisticsExtDao pointStatisticsExtDao;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	private PointTradeService pointTradeService;
	@Autowired
	private PointAccountService pointAccountService;

	@Override
	@Transactional
	public void pointByCountAndClear() {
		Date systemDate = new Date();
		Date yesterday = Dates.addDay(systemDate, -1);
		String overdueDate = Dates.format(yesterday, Dates.CHINESE_DATE_FORMAT_LINE);

		// 独立统计
		pointClearCountByOverdueDate(overdueDate);

		// 统计完成后，积分清零
		pointClearByOverdueDate(overdueDate);
	}

	/**
	 * 积分清零统计
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void pointClearCountByOverdueDate(String overdueDate) {
		pointStatisticsExtDao.pointClearCountByOverdueDate(overdueDate);
	}

	/**
	 * 积分清零操作
	 */
	public void pointClearByOverdueDate(String overdueDate) {
		long id = 0;
		PageInfo<PointStatistics> pageInfo = new PageInfo<PointStatistics>();
		pageInfo.setCountOfCurrentPage(1000);
		Map<String, Object> map = Maps.newHashMap();
		map.put("EQ_status", PointStaticsStatus.init);

		if (Strings.isNotBlank(overdueDate)) {
			map.put("EQ_overdueDate", overdueDate);
		}

		PageInfo<PointStatistics> pages = this.query(pageInfo, map);
		long totalPages = pages.getTotalPage();
		long totalCount = pages.getTotalCount();
		log.info("启动-积分清零处理:需要处理总页数:{},总条数:{}", totalPages, totalCount);
		long k = 1;
		for (int i = 1; i <= totalPages; i++) {
			pageInfo.setCurrentPage(1);
			if (i > 1) {
				// 小于
				map.put("LT_id", id);
			}
			PageInfo<PointStatistics> pageLists = this.query(pageInfo, map);
			List<PointStatistics> pageResults = pageLists.getPageResults();
			// 最后一条数据的id，作为分页的起始点
			int size = pageResults.size();
			if (size > 0) {
				id = pageResults.get(size - 1).getId();
			}
			for (PointStatistics pointStatistics : pageResults) {
				try {
					String userNo = pointStatistics.getUserNo();
					log.info("userNo:{}执行-积分清零处理,时间:{},总条数:{},当前执行第:{}条,总页数:{},执行页数:{}", userNo, totalCount, k,
							totalPages, i);
					taskExecutor.execute(() -> {
						doPointClear(pointStatistics.getId());
					});
					k = k + 1;
				} catch (Exception e) {
					log.info("{}执行-积分清零处理---失败:pointStatisticsId:{},{}", pointStatistics.getId(), e);
				}
			}
		}
		log.info("启动-积分清零处理完成");
	}

	@Transactional
	private void doPointClear(long pointStatisticsId) {
		PointStatistics pointStatistics = getEntityDao().lockById(pointStatisticsId);
		if (pointStatistics.getStatus() == PointStaticsStatus.finish) {
			return;
		}

		String userNo = pointStatistics.getUserNo();
		String userName = pointStatistics.getUserName();
		PointTradeInfoDto pointTradeDto = new PointTradeInfoDto();
		pointTradeDto.setBusiId("" + pointStatistics.getId());
		pointTradeDto.setBusiType(PointTradeType.overdue_clear.code());
		pointTradeDto.setBusiTypeText(PointTradeType.overdue_clear.message());

		// 积分账户
		PointAccount pointAccount = pointAccountService.lockByUserNo(userNo);
		// 本次清零金额（预计清零）
		long totalClearPoint = pointStatistics.getTotalClearPoint();
		// 本次清零积分
		long cleanPoint = pointAccountService.cleanPointByCount(userNo, totalClearPoint);

		log.info("执行-积分清零[规则[A：本次清零云豆 > 有效积分 :清零[有效积分]；<br/>B:本次清零云豆< 有效积分 :清零[本次清零积分]]] 处理:userNo:{},  [清零积分:{}=过期统计积分:{}-已消费积分:{}-有效积分:{}]",
				userNo, cleanPoint, pointAccount.getCountOverduePoint(), pointAccount.getTotalExpensePoint(),
				pointAccount.getAvailable());

		// 清零前的账户信息
		pointStatistics.setCurrentPoint(pointAccount.getBalance());
		pointStatistics.setCurrentFreezePoint(pointAccount.getFreeze());
		pointStatistics.setCurrentTotalOverduePoint(pointAccount.getTotalOverduePoint());

		pointStatistics.setCurrentCountOverduePoint(pointAccount.getCountOverduePoint());
		pointStatistics.setCurrentTotalExpensePoint(pointAccount.getTotalExpensePoint());

		// 真实清零积分
		pointStatistics.setRealClearPoint(cleanPoint);

		// 清零后的账户信息
		pointStatistics.setEndBalancePoint(pointAccount.getBalance() - cleanPoint);
		pointStatistics.setEndTotalOverduePoint(pointAccount.getTotalOverduePoint() + cleanPoint);
		pointStatistics.setStatus(PointStaticsStatus.finish);
		getEntityDao().update(pointStatistics);

		// 积分清零
		pointTradeService.pointClear(userNo, userName, totalClearPoint, pointTradeDto);

	}

}
