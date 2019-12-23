/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-06-06
 */
package com.acooly.module.account.web;

import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.core.utils.Dates;
import com.acooly.core.utils.Money;
import com.acooly.core.utils.enums.AbleStatus;
import com.acooly.module.account.entity.AccountBill;
import com.acooly.module.account.enums.DirectionEnum;
import com.acooly.module.account.manage.AccountBillService;
import com.acooly.module.account.TradeCode;
import com.acooly.module.account.service.tradecode.TradeCodeLoader;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 账户进出账 管理控制器
 *
 * @author acooly
 * Date: 2018-06-06 10:40:46
 */
@Controller
@RequestMapping(value = "/manage/component/account/accountBill")
public class AccountBillManagerController extends AbstractJsonEntityController<AccountBill, AccountBillService> {

    /**
     * 后台管理功能，暂不考虑并发加载问题。
     */
    private Map<String, String> tradeCodeMapping = null;

    {
        allowMapping = "*";
    }

    @Autowired
    private AccountBillService accountBillService;

    @Resource(name = "tradeCodeLoader")
    private TradeCodeLoader tradeCodeLoader;


    @Override
    protected List<String> getExportTitles() {
        return Lists.newArrayList("ID", "账户ID", "账号", "用户ID", "用户编码", "用户名",
                "交易金额", "交易后余额", "资金流向", "交易码", "交易名称", "交易时间", "备注");
    }

    @Override
    protected List<String> doExportEntity(AccountBill entity) {
        List<String> row = Lists.newArrayList();
        row.add(String.valueOf(entity.getId()));
        row.add(String.valueOf(entity.getAccountId()));
        row.add(entity.getAccountNo());
        row.add(String.valueOf(entity.getUserId()));
        row.add(entity.getUserNo());
        row.add(entity.getUsername());
        row.add(Money.cent(entity.getAmount()).toString());
        row.add(Money.cent(entity.getBalancePost()).toString());
        row.add(entity.getDirection().message());
        row.add(entity.getTradeCode());
        row.add(tradeCodeMapping.get(entity.getTradeCode()));
        row.add(Dates.format(entity.getCreateTime(), Dates.CHINESE_DATETIME_FORMAT_LINE));
        row.add(entity.getComments());
        return row;
    }


    @Override
    protected String getExportFileName(HttpServletRequest request) {
        return super.getExportFileName(request);
    }

    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allDirections", DirectionEnum.mapping());
        model.put("allStatuss", AbleStatus.mapping());
        if (tradeCodeMapping == null) {
            List<TradeCode> tradeCodes = tradeCodeLoader.loadTradeCodes();
            tradeCodeMapping = Maps.newHashMap();
            for (TradeCode tradeCode : tradeCodes) {
                tradeCodeMapping.put(tradeCode.code(), tradeCode.message());
            }
        }
        model.put("allTradeCodes", tradeCodeMapping);
    }

}
