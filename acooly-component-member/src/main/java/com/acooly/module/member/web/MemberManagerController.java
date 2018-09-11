/*
* acooly.cn Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by acooly
* date:2018-07-23
*/
package com.acooly.module.member.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.acooly.module.member.enums.MemberGradeEnum;
import com.acooly.module.member.enums.MemberUserTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.module.member.entity.Member;
import com.acooly.module.member.manage.MemberEntityService;
import com.acooly.module.member.enums.MemberStatusEnum;

import com.google.common.collect.Maps;

/**
 * 会员信息 管理控制器
 * 
 * @author acooly
 * Date: 2018-07-23 00:05:26
 */
@Controller
@RequestMapping(value = "/manage/component/member/member")
public class MemberManagerController extends AbstractJQueryEntityController<Member, MemberEntityService> {

	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private MemberEntityService memberEntityService;

	
	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("allUserTypes", MemberUserTypeEnum.mapping());
		model.put("allGrades", MemberGradeEnum.mapping());
		model.put("allStatuss", MemberStatusEnum.mapping());
	}

}
