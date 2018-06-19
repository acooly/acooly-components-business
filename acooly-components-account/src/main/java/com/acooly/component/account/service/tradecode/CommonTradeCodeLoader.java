package com.acooly.component.account.service.tradecode;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-06-12 15:27
 */
@Component
public class CommonTradeCodeLoader implements TradeCodeLoader {

    @Override
    public List<TradeCode> loadTradeCodes() {
        List commonTradeCodeEnums = CommonTradeCodeEnum.getAll();
        return commonTradeCodeEnums;
    }
}
