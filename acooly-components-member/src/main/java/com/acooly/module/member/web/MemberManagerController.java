/*
* acooly.cn Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by acooly
* date:2018-07-23
*/
package com.acooly.module.member.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
	
	private static Map<Integer, String> allUserTypes = Maps.newLinkedHashMap();
	static {
		allUserTypes.put(1, "个人用户");
		allUserTypes.put(2, "企业用户");
	}
	private static Map<Integer, String> allGrades = Maps.newLinkedHashMap();
	static {
		allGrades.put(1, "普通");
		allGrades.put(2, "VIP");
	}

	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private MemberEntityService memberEntityService;

	
	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("allUserTypes", allUserTypes);
		model.put("allGrades", allGrades);
		model.put("allStatuss", MemberStatusEnum.mapping());
	}

}
