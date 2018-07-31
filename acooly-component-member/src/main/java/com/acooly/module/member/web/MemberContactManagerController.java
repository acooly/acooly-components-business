/*
* acooly.cn Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by acooly
* date:2018-07-23
*/
package com.acooly.module.member.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.module.member.entity.MemberContact;
import com.acooly.module.member.manage.MemberContactEntityService;

/**
 * 客户联系信息 管理控制器
 * 
 * @author acooly
 * Date: 2018-07-23 00:05:26
 */
@Controller
@RequestMapping(value = "/manage/component/member/memberContact")
public class MemberContactManagerController extends AbstractJQueryEntityController<MemberContact, MemberContactEntityService> {
	

	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private MemberContactEntityService memberContactEntityService;

	

}
