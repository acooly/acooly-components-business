/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-06-06
 *
 */
package com.acooly.component.account.manage;

import com.acooly.component.account.entity.AccountTradeCode;
import com.acooly.core.common.service.EntityService;

/**
 * 交易编码定义 Service接口
 * <p>
 * Date: 2018-06-06 10:40:46
 *
 * @author acooly
 */
public interface AccountTradeCodeService extends EntityService<AccountTradeCode> {

    /**
     * 根据交易编码获取配置的交易码全信息
     *
     * @param tradeCode
     * @return
     */
    AccountTradeCode getTradeCode(String tradeCode);


}
