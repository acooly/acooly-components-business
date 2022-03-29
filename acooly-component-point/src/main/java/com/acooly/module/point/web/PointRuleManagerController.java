/*
* acooly.cn Inc.
* Copyright (c) 2022 All Rights Reserved.
* create by acooly
* date:2022-02-22
*/
package com.acooly.module.point.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.utils.enums.AbleStatus;
import com.acooly.module.point.entity.PointRule;
import com.acooly.module.point.enums.PointRuleValidTypeTypeEnum;
import com.acooly.module.point.service.PointRuleService;

/**
 * 积分规则配置 管理控制器
 * 
 * @author acooly Date: 2022-02-22 17:32:57
 */
@Controller
@RequestMapping(value = "/manage/point/pointRule")
public class PointRuleManagerController extends AbstractPointManageController<PointRule, PointRuleService> {

	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private PointRuleService pointRuleService;

	@Override
	protected PointRule onSave(HttpServletRequest request, HttpServletResponse response, Model model, PointRule entity,
			boolean isCreate) throws Exception {
		String busiTypeCode=request.getParameter("busiTypeCode");
		entity.setBusiTypeCode(busiTypeCode);
		entity.setBusiTypeText(getBusiTypeText(busiTypeCode));
		return super.onSave(request, response, model, entity, isCreate);
	}

	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		super.referenceData(request, model);
		model.put("allValidTypes", PointRuleValidTypeTypeEnum.mapping());
		model.put("allStatuss", AbleStatus.mapping());
		model.put("allBusiTypeEnumss", getBusiTypeMap());
	}

}
