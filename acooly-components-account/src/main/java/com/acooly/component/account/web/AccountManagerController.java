/*
* acooly.cn Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by acooly
* date:2018-06-06
*/
package com.acooly.component.account.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.acooly.core.utils.enums.AbleStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.component.account.entity.Account;
import com.acooly.component.account.manage.AccountService;
import com.acooly.component.account.enums.AccountTypeEnum;

/**
 * 账户信息 管理控制器
 * 
 * @author acooly
 * Date: 2018-06-06 10:40:46
 */
@Controller
@RequestMapping(value = "/manage/component/account/account")
public class AccountManagerController extends AbstractJQueryEntityController<Account, AccountService> {
	

	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private AccountService accountService;

	
	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("allAccountTypes", AccountTypeEnum.mapping());
		model.put("allStatuss", AbleStatus.mapping());
	}

}
