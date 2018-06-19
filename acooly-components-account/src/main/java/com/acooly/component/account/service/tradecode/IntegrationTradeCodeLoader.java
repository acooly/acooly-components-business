package com.acooly.component.account.service.tradecode;

import com.acooly.component.account.enums.DirectionEnum;
import com.acooly.core.utils.Collections3;
import com.google.common.collect.Lists;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-06-18 02:22
 */
@Component("integrationTradeCodeLoader")
public class IntegrationTradeCodeLoader implements TradeCodeLoader, ApplicationContextAware, InitializingBean {

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
            if (tradeCodeLoader instanceof IntegrationTradeCodeLoader) {
                continue;
            }
            tradeCodeLoaders.add(tradeCodeLoader);
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    public static void main(String[] args) {
        Set<TradeCode> tradeCodes = new HashSet<TradeCode>();

        tradeCodes.add(CommonTradeCodeEnum.deposit);
        tradeCodes.add(new DefaultTradeCode("deposit", "充值", DirectionEnum.in));

        System.out.println(tradeCodes);


    }

}
