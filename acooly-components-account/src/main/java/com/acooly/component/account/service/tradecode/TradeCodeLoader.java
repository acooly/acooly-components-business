package com.acooly.component.account.service.tradecode;

import java.util.List;

/**
 * 交易码集成服务
 *
 * @author zhangpu
 * @date 2018-06-06
 */
public interface TradeCodeLoader {


    /**
     * 加载交易码
     *
     * @return
     */
    List<TradeCode> loadTradeCodes();


}
