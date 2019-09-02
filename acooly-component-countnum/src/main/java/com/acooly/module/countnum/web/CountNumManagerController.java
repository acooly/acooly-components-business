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

import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.module.countnum.entity.CountNum;
import com.acooly.module.countnum.service.CountNumService;
import com.acooly.module.countnum.enums.CountNumTypeEnum;
import com.acooly.module.countnum.enums.CountNumStatusEnum;
import com.acooly.module.countnum.enums.CountNumIsCoverEnum;

import com.google.common.collect.Maps;

/**
 * 游戏-计数 管理控制器
 * 
 * @author cuifuq
 * Date: 2019-07-03 11:48:59
 */
@Controller
@RequestMapping(value = "/manage/component/countnum/countNum")
public class CountNumManagerController extends AbstractJsonEntityController<CountNum, CountNumService> {
	

	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private CountNumService countNumService;

	
	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("allTypes", CountNumTypeEnum.mapping());
		model.put("allStatuss", CountNumStatusEnum.mapping());
		model.put("allIsCovers", CountNumIsCoverEnum.mapping());
	}

}
