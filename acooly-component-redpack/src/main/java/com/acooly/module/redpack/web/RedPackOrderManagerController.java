/*
* acooly.cn Inc.
* Copyright (c) 2019 All Rights Reserved.
* create by cuifuq
* date:2019-03-05
*/
package com.acooly.module.redpack.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.module.redpack.entity.RedPackOrder;
import com.acooly.module.redpack.service.RedPackOrderService;
import com.acooly.module.redpack.enums.RedPackOrderTypeEnum;
import com.acooly.module.redpack.enums.RedPackOrderStatusEnum;

import com.google.common.collect.Maps;

/**
 * 红包订单 管理控制器
 * 
 * @author cuifuq
 * Date: 2019-03-05 18:18:09
 */
@Controller
@RequestMapping(value = "/manage/component/redpack/redPackOrder")
public class RedPackOrderManagerController extends AbstractJsonEntityController<RedPackOrder, RedPackOrderService> {
	

	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private RedPackOrderService redPackOrderService;

	
	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("allTypes", RedPackOrderTypeEnum.mapping());
		model.put("allStatuss", RedPackOrderStatusEnum.mapping());
	}

}
