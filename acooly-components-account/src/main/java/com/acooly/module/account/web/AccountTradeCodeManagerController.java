/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-06-06
 */
package com.acooly.module.account.web;

import com.acooly.module.account.entity.AccountTradeCode;
import com.acooly.module.account.enums.DirectionEnum;
import com.acooly.module.account.manage.AccountTradeCodeService;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 交易编码定义 管理控制器
 *
 * @author acooly
 * Date: 2018-06-06 10:40:46
 */
@Controller
@RequestMapping(value = "/manage/component/account/accountTradeCode")
public class AccountTradeCodeManagerController extends AbstractJQueryEntityController<AccountTradeCode, AccountTradeCodeService> {


    {
        allowMapping = "*";
    }

    @SuppressWarnings("unused")
    @Autowired
    private AccountTradeCodeService accountTradeCodeService;


    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {

        model.put("directions", DirectionEnum.mapping());
    }
}
