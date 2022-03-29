/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by acooly
 * date:2017-03-13
 */
package com.acooly.module.point.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acooly.core.common.web.support.JsonResult;
import com.acooly.module.point.entity.PointStatistics;
import com.acooly.module.point.enums.PointStaticsStatus;
import com.acooly.module.point.service.PointStatisticsService;

/**
 * 积分统计 管理控制器
 *
 * @author acooly Date: 2017-03-13 11:51:10
 */
@Controller
@RequestMapping(value = "/manage/point/pointStatistics")
public class PointStatisticsManagerController
		extends AbstractPointManageController<PointStatistics, PointStatisticsService> {

	@Autowired
	private PointStatisticsService pointStatisticsService;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	{
		allowMapping = "*";
	}
	
	/**
	 * 积分清零
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "clear")
	public String clear(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			model.addAllAttributes(referenceData(request));
			String doType=request.getParameter("doType");
			model.addAttribute("doType", doType);
		} catch (Exception e) {
			handleException("积分统计+清零", e, request);
		}
		return getListView() + "Clear";
	}
	

	/**
	 * 积分清零统计
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "clearCountByOverdueDate")
	@ResponseBody
	public JsonResult clearCountByOverdueDate(HttpServletRequest request, HttpServletResponse response) {
		JsonResult result = new JsonResult();
		try {
			// 积分清零统计
			String overdueDate = request.getParameter("overdueDate");
			pointStatisticsService.pointClearCountByOverdueDate(overdueDate);
			result.setMessage("积分清零统计  正在处理中,之后刷新查看结果");
		} catch (Exception e) {
			handleException(result, "积分清零统计", e);
		}
		return result;
	}

	/**
	 * 积分清零事务
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "clearByOverdueDate")
	@ResponseBody
	public JsonResult clearByOverdueDate(HttpServletRequest request, HttpServletResponse response) {
		JsonResult result = new JsonResult();
		try {
			// 积分清零统计
			String overdueDate = request.getParameter("overdueDate");
			// 积分清零事务
			taskExecutor.execute(() -> {
				pointStatisticsService.pointClearByOverdueDate(overdueDate);
			});

			result.setMessage("积分清零事务 正在处理中,之后刷新查看结果");
		} catch (Exception e) {
			handleException(result, "积分清零事务", e);
		}
		return result;
	}

	/**
	 * 积分清零统计+清零
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "pointByCountAndClear")
	@ResponseBody
	public JsonResult pointByCountAndClear(HttpServletRequest request, HttpServletResponse response) {
		JsonResult result = new JsonResult();
		try {
			// 积分清零统计+清零
			pointStatisticsService.pointByCountAndClear();
			result.setMessage("积分清零统计+清零  正在处理中,之后刷新查看结果");
		} catch (Exception e) {
			handleException(result, "积分清零统计+清零", e);
		}
		return result;
	}

	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		super.referenceData(request, model);
		model.put("allStatuss", PointStaticsStatus.mapping());
	}
}
