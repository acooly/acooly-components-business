/*
 * www.acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2017-02-14 17:04 创建
 */
package com.acooly.component.account;

import com.acooly.core.common.dao.support.StandardDatabaseScriptIniter;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-07-10
 */
@Configuration
@EnableConfigurationProperties({AccountProperties.class})
@ConditionalOnProperty(value = AccountProperties.PREFIX + ".enable", matchIfMissing = true)
@ComponentScan(basePackages = "com.acooly.component.account")
public class AccountAutoConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private AccountProperties accountProperties;

    /**
     * 数据库初始化
     *
     * @return
     */
    @Bean
    public StandardDatabaseScriptIniter PortletScriptIniter() {

        return new StandardDatabaseScriptIniter() {
            @Override
            public String getEvaluateTable() {
                return "ac_account";
            }

            @Override
            public String getComponentName() {
                return "account";
            }

            @Override
            public List<String> getInitSqlFile() {
                return Lists.newArrayList("ac_account", "ac_account_bill");
            }
        };
    }


}
