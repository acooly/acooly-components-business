/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by cuifuqiang
 * date:2017-02-03
 */
package com.acooly.module.point.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.web.support.JsonListResult;
import com.acooly.module.ofile.domain.OnlineFile;
import com.acooly.module.ofile.portal.OfilePortalController;
import com.acooly.module.point.domain.PointGrade;
import com.acooly.module.point.service.PointGradeService;

/**
 * 积分等级 管理控制器
 *
 * @author cuifuqiang Date: 2017-02-03 22:47:28
 */
@Controller
@RequestMapping(value = "/manage/point/pointGrade")
public class PointGradeManagerController extends AbstractPointManageController<PointGrade, PointGradeService> {

	@SuppressWarnings("unused")
	@Autowired
	private PointGradeService pointGradeService;

	@Autowired
	private OfilePortalController ofilePortalController;

	{
		allowMapping = "*";
	}

	@Override
	protected PointGrade onSave(HttpServletRequest request, HttpServletResponse response, Model model,
			PointGrade entity, boolean isCreate) throws Exception {
		JsonListResult<OnlineFile> ofiles = ofilePortalController.upload(request, response);
		OnlineFile ofile = ofiles.getRows().get(0);
		if (ofile != null) {
			entity.setPicture(ofile.getAccessUrl());
		}
		return entity;
	}

}
