package com.acooly.module.countnum;

import static com.acooly.module.countnum.CountNumProperties.PREFIX;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
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
@EnableConfigurationProperties({ CountNumProperties.class })
@ConditionalOnProperty(value = PREFIX + ".enable", matchIfMissing = true)
@ComponentScan(basePackages = "com.acooly.module.countnum")
@MapperScan(basePackages = "com.acooly.module.countnum.dao")
@AutoConfigureAfter(SecurityAutoConfig.class)
public class CountNumAutoConfig {
	@Bean
	public StandardDatabaseScriptIniter countnumScriptIniter() {
		return new StandardDatabaseScriptIniter() {
			@Override
			public String getEvaluateTable() {
				return "game_count_num";
			}

			@Override
			public String getComponentName() {
				return "countnum";
			}

			@Override
			public List<String> getInitSqlFile() {
				return Lists.newArrayList("game_count_num", "game_count_num_urls");
			}
		};
	}
}
