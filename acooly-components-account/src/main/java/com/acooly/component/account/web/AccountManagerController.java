/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-06-06
 */
package com.acooly.component.account.web;

import com.acooly.component.account.dto.AccountInfo;
import com.acooly.component.account.entity.Account;
import com.acooly.component.account.enums.AccountTypeEnum;
import com.acooly.component.account.manage.AccountService;
import com.acooly.component.account.service.AccountManageService;
import com.acooly.component.account.service.AccountTradeService;
import com.acooly.component.account.service.tradecode.TradeCodeLoader;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.MappingMethod;
import com.acooly.core.common.web.support.JsonEntityResult;
import com.acooly.core.utils.Ids;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.enums.SimpleStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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
    @Autowired
    private AccountTradeService accountTradeService;
    @Autowired
    private AccountManageService accountManageService;
    @Resource(name = "tradeCodeLoader")
    private TradeCodeLoader tradeCodeLoader;


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
            AccountInfo accountInfo = new AccountInfo(Long.valueOf(userId), userNo);
            accountInfo.setComments(request.getParameter("comments"));
            accountInfo.setUsername(request.getParameter("username"));
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
            result.setMessage("更新成功");
        } catch (Exception var5) {
            this.handleException(result, "更新", var5);
        }

        return result;
    }

    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allAccountTypes", AccountTypeEnum.mapping());
        model.put("allStatuss", SimpleStatus.mapping());
    }

}
