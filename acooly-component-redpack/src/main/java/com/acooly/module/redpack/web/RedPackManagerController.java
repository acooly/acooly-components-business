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
import com.acooly.module.redpack.entity.RedPack;
import com.acooly.module.redpack.service.RedPackService;
import com.acooly.module.redpack.enums.RedPackStatusEnum;

import com.google.common.collect.Maps;

/**
 * 红包 管理控制器
 * 
 * @author cuifuq
 * Date: 2019-03-05 18:18:08
 */
@Controller
@RequestMapping(value = "/manage/component/redpack/redPack")
public class RedPackManagerController extends AbstractJsonEntityController<RedPack, RedPackService> {
	

	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private RedPackService redPackService;

	
	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("allStatuss", RedPackStatusEnum.mapping());
	}

}
