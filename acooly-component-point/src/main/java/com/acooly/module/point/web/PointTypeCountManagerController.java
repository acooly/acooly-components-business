/*
* acooly.cn Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by acooly
* date:2018-08-06
*/
package com.acooly.module.point.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.module.point.entity.PointTypeCount;
import com.acooly.module.point.service.PointTypeCountService;

/**
 * 积分业务统计 管理控制器
 * 
 * @author acooly
 * Date: 2018-08-06 16:18:40
 */
@Controller
@RequestMapping(value = "/manage/point/pointTypeCount")
public class PointTypeCountManagerController extends AbstractPointManageController<PointTypeCount, PointTypeCountService> {
	

	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private PointTypeCountService pointTypeCountService;

	

}
