package com.acooly.module.account;

import com.acooly.module.account.service.tradecode.TradeCode;
import com.acooly.module.account.service.tradecode.TradeCodeLoader;
import com.acooly.core.common.boot.Apps;
import com.acooly.module.test.AppTestBase;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-07-03 21:05
 */
public class TradeCodeLoaderTest extends AppTestBase {

    public static final String PROFILE = "sdev";

    //设置环境
    static {
        Apps.setProfileIfNotExists(PROFILE);
    }


    @Resource(name = "tradeCodeLoader")
    TradeCodeLoader tradeCodeLoader;

    /**
     * 查询所有可用交易码
     *
     * 该接口主要用户全局使用交易编码的场景，比如：前端交易查询的翻译。
     *
     * 交易码的来源：
     * 1、组件内部的CommonTradeCodeEnum定义的公共基础交易嘛，比如：充值，提现等基础。
     * 2、组件内置的管理系统界面中数据库维护的交易编。
     * 3、集成项目中任何实现TradeCodeLoader接口的输出（spring容器内）
     *
     */
    @Test
    public void testQueryTradeCodes() {
        List<TradeCode> tradeCodes = tradeCodeLoader.loadTradeCodes();
        for (TradeCode tradeCode : tradeCodes) {
            System.out.println(tradeCode);
        }
    }


}
