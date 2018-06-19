/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-06-06
 */
package com.acooly.component.account.web;

import com.acooly.component.account.entity.AccountBill;
import com.acooly.component.account.enums.DirectionEnum;
import com.acooly.component.account.manage.AccountBillService;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.utils.enums.AbleStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 账户进出账 管理控制器
 *
 * @author acooly
 * Date: 2018-06-06 10:40:46
 */
@Controller
@RequestMapping(value = "/manage/component/account/accountBill")
public class AccountBillManagerController extends AbstractJQueryEntityController<AccountBill, AccountBillService> {


    {
        allowMapping = "*";
    }

    @SuppressWarnings("unused")
    @Autowired
    private AccountBillService accountBillService;


    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allDirections", DirectionEnum.mapping());
        model.put("allStatuss", AbleStatus.mapping());
    }

}
