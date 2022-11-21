/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-14 17:04 创建
 */
package cn.acooly.component.assetmgmt;

import com.acooly.core.common.dao.support.StandardDatabaseScriptIniter;
import com.google.common.collect.Lists;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;


/**
 * 自动装配配置
 *
 * @author zhangpu@acooly.cn
 * @date 2022-11-19
 * todo：资产身份和凭证信息加密存储(数据库加密 or Vault)
 * todo: 未左侧分类树增加刷新按钮
 */
@Configuration
@ComponentScan
@MapperScan(basePackages = "cn.acooly.component.assetmgmt.dao")
public class AssetmgmtAutoConfig {

    @Bean
    public StandardDatabaseScriptIniter assetmgmtDbScriptIniter() {
        return new StandardDatabaseScriptIniter() {
            @Override
            public String getEvaluateTable() {
                return "ac_secretbox";
            }

            @Override
            public String getComponentName() {
                return "assetmgmt";
            }

            @Override
            public List<String> getInitSqlFile() {
                return Lists.newArrayList("ddl", "init");
            }
        };
    }


}
