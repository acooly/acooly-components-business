/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-06-06
 */
package com.acooly.module.account.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.core.common.web.MappingMethod;
import com.acooly.core.common.web.support.JsonEntityResult;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.Ids;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.enums.SimpleStatus;
import com.acooly.module.account.AccountProperties;
import com.acooly.module.account.dto.AccountInfo;
import com.acooly.module.account.entity.Account;
import com.acooly.module.account.enums.AccountTypeEnum;
import com.acooly.module.account.manage.AccountService;
import com.acooly.module.account.service.AccountManageService;
import com.acooly.module.account.service.tradecode.TradeCodeLoader;

/**
 * 账户信息 管理控制器
 *
 * @author acooly Date: 2018-06-06 10:40:46
 */
@Controller
@RequestMapping(value = "/manage/component/account/account")
public class AccountManagerController extends AbstractJsonEntityController<Account, AccountService> {

	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountManageService accountManageService;
	@Resource(name = "tradeCodeLoader")
	private TradeCodeLoader tradeCodeLoader;
	
	
	@Autowired
	private AccountProperties accountProperties;

	@Override
	public JsonEntityResult<Account> saveJson(HttpServletRequest request, HttpServletResponse response) {
		JsonEntityResult<Account> result = new JsonEntityResult();
		this.allow(request, response, MappingMethod.create);
		try {
			String userId = request.getParameter("userId");
			String userNo = request.getParameter("userNo");
			if (!Strings.isNumeric(userId)) {
				throw new RuntimeException("用户ID不能为空,只能为数字。");
			}
			if (Strings.isBlank(userNo)) {
				userNo = Ids.getDid();
			}
			AccountInfo accountInfo = new AccountInfo();
			accountInfo.setUserId(Long.parseLong(userId));
			accountInfo.setUserNo(userNo);
			accountInfo.setComments(request.getParameter("comments"));
			accountInfo.setUsername(request.getParameter("username"));
			accountInfo.setAccountType(request.getParameter("accountType"));

			Account account = accountManageService.openAccount(accountInfo);
			result.setEntity(account);
			result.setMessage("新增成功");
		} catch (Exception var5) {
			this.handleException(result, "新增", var5);
		}
		return result;
	}

	@Override
	public JsonEntityResult<Account> updateJson(HttpServletRequest request, HttpServletResponse response) {
		this.allow(request, response, MappingMethod.update);
		JsonEntityResult result = new JsonEntityResult();
		try {
			Account account = loadEntity(request);
			account.setComments(request.getParameter("comments"));
			account.setUsername(request.getParameter("username"));
			accountService.update(account);
			result.setEntity(account);
			result.setMessage("更新成功");
		} catch (Exception var5) {
			this.handleException(result, "更新", var5);
		}
		return result;
	}

	@RequestMapping({ "statusChange" })
	@ResponseBody
	public JsonResult statusChange(HttpServletRequest request, HttpServletResponse response) {
		JsonResult result = new JsonResult();
		this.allow(request, response, MappingMethod.delete);

		try {
			String id = request.getParameter("id");
			String status = request.getParameter("status");
			accountManageService.statusChange(Long.valueOf(id), SimpleStatus.findStatus(status));
			result.setMessage("操作成功");
		} catch (Exception e) {
			this.handleException(result, "状态变更", e);
		}

		return result;
	}



	/**
	 * 关联账户创建页面
	 */
	@RequestMapping({ "accountRelationCreate" })
	public String accountRelationCreate(HttpServletRequest request, HttpServletResponse response, Model model) {
		this.allow(request, response, MappingMethod.list);
		model.addAllAttributes(this.referenceData(request));
		try {
			String userId = request.getParameter("userId");
			String userNo = request.getParameter("userNo");
			String username = request.getParameter("username");
			model.addAttribute("userId", userId);
			model.addAttribute("userNo", userNo);
			model.addAttribute("username", username);
		} catch (Exception var5) {
			this.handleException("关联账户创建", var5, request);
		}
		return "/manage/component/account/accountRelationCreate";
	}

	/**
	 * 查询关联账户
	 */
	@RequestMapping({ "accountRelationList" })
	public String accountRelationList(HttpServletRequest request, HttpServletResponse response, Model model) {
		this.allow(request, response, MappingMethod.list);
		try {
			String userId = request.getParameter("userId");
			String userNo = request.getParameter("userNo");
			String username = request.getParameter("username");
			model.addAttribute("userId", userId);
			model.addAttribute("userNo", userNo);
			model.addAttribute("username", username);
		} catch (Exception e) {
			this.handleException("查询关联账户", e, request);
		}

		return "/manage/component/account/accountRelationList";
	}

	
	
	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("allAccountTypes", accountProperties.getAccountType());
		model.put("allStatuss", SimpleStatus.mapping());
        model.put("isCreateAccount", accountProperties.isCreateAccount());

	}
}
