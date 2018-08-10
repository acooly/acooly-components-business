package com.acooly.module.account.service.tradecode;

import com.acooly.module.account.TradeCode;
import com.acooly.module.account.entity.AccountTradeCode;
import com.acooly.module.account.manage.AccountTradeCodeService;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-06-12 15:27
 */
@Component
public class ConfigurableTradeCodeLoader implements TradeCodeLoader {


    @Autowired
    private AccountTradeCodeService accountTradeCodeService;

    @Override
    public List<TradeCode> loadTradeCodes() {
        List<AccountTradeCode> accountTradeCodes = accountTradeCodeService.getAll();
        return Lists.transform(accountTradeCodes, new Function<AccountTradeCode, TradeCode>() {
            @Nullable
            @Override
            public TradeCode apply(@Nullable AccountTradeCode input) {
                return new DefaultTradeCode(input.getTradeCode(), input.getTradeName(), input.getDirection());
            }
        });
    }


}
