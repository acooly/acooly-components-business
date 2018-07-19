package com.acooly.module.account.service.tradecode;

import com.acooly.core.utils.Collections3;
import com.google.common.collect.Lists;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局交易码加载器
 * <p>
 * 交易码的来源：
 * 1、组件内部的CommonTradeCodeEnum定义的公共基础交易嘛，比如：充值，提现等基础。
 * 2、组件内置的管理系统界面中数据库维护的交易编。
 * 3、集成项目中任何实现TradeCodeLoader接口的输出（spring容器内）
 *
 * @author zhangpu@acooly.cn
 * @date 2018-06-18 02:22
 */
@Component("tradeCodeLoader")
public class TradeCodeLoaderImpl implements TradeCodeLoader, ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    private List<TradeCodeLoader> tradeCodeLoaders = new ArrayList<>();

    @Override
    public List<TradeCode> loadTradeCodes() {
        return mergeTradeCodes();
    }

    private List<TradeCode> mergeTradeCodes() {
        Map<String, TradeCode> tradeCodeMaps = new HashMap<>();
        if (Collections3.isNotEmpty(tradeCodeLoaders)) {
            List<TradeCode> oneLoaderCodes = null;
            for (TradeCodeLoader tradeCodeLoader : tradeCodeLoaders) {
                oneLoaderCodes = tradeCodeLoader.loadTradeCodes();
                for (TradeCode tradeCode : oneLoaderCodes) {
                    tradeCodeMaps.put(tradeCode.code(), tradeCode);
                }
            }
        }
        return Lists.newArrayList(tradeCodeMaps.values());
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, TradeCodeLoader> tradeCodeLoaderMap = applicationContext.getBeansOfType(TradeCodeLoader.class);
        for (TradeCodeLoader tradeCodeLoader : tradeCodeLoaderMap.values()) {
            if (tradeCodeLoader instanceof TradeCodeLoaderImpl) {
                continue;
            }
            tradeCodeLoaders.add(tradeCodeLoader);
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


}
