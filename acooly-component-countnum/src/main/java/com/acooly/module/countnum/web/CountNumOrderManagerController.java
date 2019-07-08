/*
* acooly.cn Inc.
* Copyright (c) 2019 All Rights Reserved.
* create by cuifuq
* date:2019-07-03
*/
package com.acooly.module.countnum.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.module.countnum.entity.CountNumOrder;
import com.acooly.module.countnum.enums.CountNumTypeEnum;
import com.acooly.module.countnum.service.CountNumOrderService;

/**
 * 游戏-计数用户订单 管理控制器
 * 
 * @author cuifuq
 * Date: 2019-07-03 11:48:59
 */
@Controller
@RequestMapping(value = "/manage/component/countnum/countNumOrder")
public class CountNumOrderManagerController extends AbstractJQueryEntityController<CountNumOrder, CountNumOrderService> {
	

	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private CountNumOrderService countNumOrderService;

	
	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("allCountTypes", CountNumTypeEnum.mapping());
	}

}
