/*
* acooly.cn Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by acooly
* date:2018-10-16
*/
package com.acooly.module.member.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.acooly.module.member.enums.LoginStatusEnum;
import com.acooly.module.member.enums.AuthStatusEnum;
import com.acooly.module.member.enums.AuthTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.module.member.entity.MemberAuth;
import com.acooly.module.member.manage.MemberAuthEntityService;
import com.acooly.module.member.enums.AuthRoleEnum;

/**
 * b_member_auth 管理控制器
 * 
 * @author acooly
 * Date: 2018-10-16 15:57:37
 */
@Controller
@RequestMapping(value = "/manage/component/member/memberAuth")
public class MemberAuthManagerController extends AbstractJsonEntityController<MemberAuth, MemberAuthEntityService> {
	

	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private MemberAuthEntityService memberAuthEntityService;

	
	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("allAuthTypes", AuthTypeEnum.mapping());
		model.put("allAuthRoles", AuthRoleEnum.mapping());
		model.put("allLoginStatuss", LoginStatusEnum.mapping());
		model.put("allAuthStatuss", AuthStatusEnum.mapping());
	}

}
