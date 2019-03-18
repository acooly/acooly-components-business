package com.acooly.module.redpack;

import static com.acooly.module.redpack.RedPackProperties.PREFIX;

import java.util.List;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.acooly.core.common.dao.support.StandardDatabaseScriptIniter;
import com.acooly.module.security.config.SecurityAutoConfig;
import com.google.common.collect.Lists;

@Configuration
@EnableConfigurationProperties({RedPackProperties.class})
@ConditionalOnProperty(value = PREFIX + ".enable", matchIfMissing = true)
@ComponentScan(basePackages = "com.acooly.module.redpack")
@AutoConfigureAfter(SecurityAutoConfig.class)
public class RedPackAutoConfig {
    @Bean
    public StandardDatabaseScriptIniter pointScriptIniter() {
        return new StandardDatabaseScriptIniter() {
            @Override
            public String getEvaluateTable() {
                return "red_red_pack";
            }

            @Override
            public String getComponentName() {
                return "redpack";
            }

            @Override
            public List<String> getInitSqlFile() {
                return Lists.newArrayList("redpack", "redpack_urls");
            }
        };
    }
}
