/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by cuifuqiang
 * date:2017-02-03
 */
package com.acooly.module.point.portal.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acooly.module.point.PointProperties;
import com.acooly.module.point.business.PointBusinessService;
import com.acooly.module.point.dto.PointTradeInfoDto;
import com.acooly.module.point.service.PointStatisticsService;

/**
 * 积分账户 管理控制器
 *
 */
@Profile("!online")
@Controller
@RequestMapping(value = "/test/point")
public class PointBusinessTestController {

	@SuppressWarnings("unused")
	@Autowired
	private PointBusinessService pointBusinessService;

	@Autowired
	private PointProperties pointProperties;

	@Autowired
	private PointStatisticsService pointStatisticsService;

	@RequestMapping(value = "give")
	@ResponseBody
	public void grant(HttpServletRequest request, HttpServletResponse response, Model model) {

		String userNo = "3";
		String userName = "cuifuq7" + userNo;
		long amount = 50L;

		PointTradeInfoDto dto = new PointTradeInfoDto();
		dto.setBusiId("111");
		dto.setRepeatTrade(true);
		dto.setBusiType("boss_give_out");
		dto.setBusiTypeText("系统积分");
		dto.setBusiData("系统测试");

//		pointBusinessService.pointProduce(userNo, userName, 7L, "2022-03-11", dto);
//		pointBusinessService.pointProduceByRule(userNo, userName, amount, dto);

//		pointBusinessService.pointProduce(userNo, userName, 300L, "2022-03-11", dto);
//		pointBusinessService.pointExpense(userNo, userName, 200L, false, dto);

//		pointBusinessService.pointProduce(userNo, userName, 500L, "2022-03-12", dto);
		pointBusinessService.pointExpense(userNo, userName, 100L, false, dto);
		
		
//		pointBusinessService.pointProduce(userNo, userName, 600L, "2022-03-18", dto);
		pointBusinessService.pointExpense(userNo, userName, 200L, false, dto);
		
		
//		pointBusinessService.pointProduce(userNo, userName, 500L, "2022-03-19", dto);
//		pointBusinessService.pointExpense(userNo, userName, 100L, false, dto);
//
//		pointStatisticsService.pointClearByOverdueDate("2022-03-18");
//		pointStatisticsService.pointClearByOverdueDate("2022-03-19");

//		pointBusinessService.pointExpenseByRule(userNo, "111", "boss_give_out1111","0000");
	}

}
