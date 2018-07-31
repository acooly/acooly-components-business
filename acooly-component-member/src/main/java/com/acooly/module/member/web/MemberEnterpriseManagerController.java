/*
* acooly.cn Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by acooly
* date:2018-07-31
*/
package com.acooly.module.member.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.acooly.module.member.enums.CertTypeEnum;
import com.acooly.module.member.enums.MemberUserTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.module.member.entity.MemberEnterprise;
import com.acooly.module.member.manage.MemberEnterpriseService;
import com.acooly.module.member.enums.HoldingEnumEnum;

/**
 * 企业客户实名认证 管理控制器
 * 
 * @author acooly
 * Date: 2018-07-31 20:01:45
 */
@Controller
@RequestMapping(value = "/manage/component/member/memberEnterprise")
public class MemberEnterpriseManagerController extends AbstractJQueryEntityController<MemberEnterprise, MemberEnterpriseService> {
	

	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private MemberEnterpriseService memberEnterpriseService;

	
	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("allEntTypes", MemberUserTypeEnum.mapping());
		model.put("allHoldingEnums", HoldingEnumEnum.mapping());
		model.put("allHoldingCertTypes", CertTypeEnum.mapping());
	}

}
