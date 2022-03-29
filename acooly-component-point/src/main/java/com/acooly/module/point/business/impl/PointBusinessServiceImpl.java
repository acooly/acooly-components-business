package com.acooly.module.point.business.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.Dates;
import com.acooly.core.utils.Money;
import com.acooly.module.point.business.PointBusinessService;
import com.acooly.module.point.dto.PointTradeInfoDto;
import com.acooly.module.point.entity.PointAccount;
import com.acooly.module.point.entity.PointRule;
import com.acooly.module.point.entity.PointTrade;
import com.acooly.module.point.enums.PointRuleValidTypeTypeEnum;
import com.acooly.module.point.service.PointAccountService;
import com.acooly.module.point.service.PointRuleService;
import com.acooly.module.point.service.PointTradeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("pointBusinessService")
public class PointBusinessServiceImpl implements PointBusinessService {

	@Autowired
	private PointTradeService pointTradeService;

	@Autowired
	private PointRuleService pointRuleService;

	@Autowired
	private PointAccountService pointAccountService;

	@Override
	public PointAccount findByUserNo(String userNo) {
		return pointAccountService.findByUserNo(userNo);
	}

	@Override
	@Transactional
	public PointAccount syncPointAccount(String userNo, String userName) {
		return pointAccountService.syncPointAccount(userNo, userName);
	}

	@Override
	public long cleanPointByCount(String userNo, long totalClearPoint) {
		return pointAccountService.cleanPointByCount(userNo, totalClearPoint);
	}

	@Override
	@Transactional
	public PointTrade pointProduceByRule(String userNo, String userName, Money amount,
			PointTradeInfoDto pointTradeDto) {
		// 业务规则编码
		String busiTypeCode = pointTradeDto.getBusiType();
		PointRule pointRule = pointRuleService.findByBusiTypeCode(busiTypeCode);
		if (pointRule == null) {
			throw new BusinessException(pointTradeDto + "没有配置有效积分规则,无法处理产生积分");
		}
		// 积分计算
		long point = 0L;
		long amountDB = pointRule.getAmount();
		if (amountDB != 0) {
			point = ((amount.getCent() / 100) / amountDB) * pointRule.getPoint();
		} else {
			point = pointRule.getPoint();
		}

		// 积分有效期
		PointRuleValidTypeTypeEnum pointValidType = pointRule.getValidType();

		// 当前系统时间
		Date systemDate = new Date();
		// 第二天时间
		Date secondYear = Dates.addYear(systemDate, 1);
		String overdueDateStr = "";

		// 有效一年 格式：yyyy-MM-dd
		if (pointValidType == PointRuleValidTypeTypeEnum.only_one_year) {
			overdueDateStr = Dates.format(secondYear, Dates.CHINESE_DATE_FORMAT_LINE);
		}
		// 次年年底 格式：yyyy-MM-dd
		if (pointValidType == PointRuleValidTypeTypeEnum.next_year_over) {
			overdueDateStr = Dates.format(secondYear, "yyyy") + "-12-31";
		}
		// 备注
		pointTradeDto.setComments("rule:" + pointRule.getComments());
		return pointProduce(userNo, userName, point, overdueDateStr, pointTradeDto);
	}

	@Override
	@Transactional
	public PointTrade pointExpenseByRule(String userNo, String busiId, String busiType, String busiComments) {
		PointAccount pointAccount = findByUserNo(userNo);
		long pointExpense = pointTradeService.countProducePointByBusi(userNo, busiId, busiType);
		PointTradeInfoDto dto = new PointTradeInfoDto();
		dto.setRepeatTrade(true);
		dto.setBusiId(busiId);
		dto.setBusiType(busiType);
		dto.setBusiTypeText("积分取消");
		dto.setComments(busiComments);
		return pointTradeService.pointExpenseByCancel(userNo, pointAccount.getUserName(), pointExpense, dto);
	}

	@Override
	@Transactional
	public PointTrade pointProduce(String userNo, String userName, long point, String overdueDate,
			PointTradeInfoDto pointTradeDto) {
		return pointTradeService.pointProduce(userNo, userName, point, overdueDate, pointTradeDto);
	}

	@Override
	@Transactional
	public PointTrade pointExpense(String userNo, String userName, long point, boolean isFreeze,
			PointTradeInfoDto pointTradeDto) {
		return pointTradeService.pointExpense(userNo, userName, point, isFreeze, pointTradeDto);
	}

	@Override
	@Transactional
	public PointTrade pointFreeze(String userNo, String userName, long point, PointTradeInfoDto pointTradeDto) {
		return pointTradeService.pointFreeze(userNo, userName, point, pointTradeDto);
	}

	@Override
	@Transactional
	public PointTrade pointUnfreeze(String userNo, String userName, long point, PointTradeInfoDto pointTradeDto) {
		return pointTradeService.pointUnfreeze(userNo, userName, point, pointTradeDto);
	}

}
