package com.acooly.module.countnum;

import static com.acooly.module.countnum.CountNumProperties.PREFIX;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import com.acooly.core.common.dao.support.StandardDatabaseScriptIniter;
import com.acooly.core.utils.Strings;
import com.acooly.module.countnum.business.service.cache.listener.CountNumRedisListener;
import com.acooly.module.security.config.SecurityAutoConfig;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableConfigurationProperties({ CountNumProperties.class })
@ConditionalOnProperty(value = PREFIX + ".enable", matchIfMissing = true)
@ComponentScan(basePackages = "com.acooly.module.countnum")
@MapperScan(basePackages = "com.acooly.module.countnum.dao")
@AutoConfigureAfter(SecurityAutoConfig.class)
public class CountNumAutoConfig {

	/**
	 * 计数器监听器
	 */
	@Autowired
	private CountNumRedisListener countNumRedisListener;

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

	@Bean
	public RedisMessageListenerContainer countNumRedisMessageListenerContainer(RedisConnectionFactory connectionFactory,
			Environment environment) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		String redisDataBase = environment.getProperty("spring.redis.database");
		if (Strings.isBlank(redisDataBase)) {
			redisDataBase = "0";
		}
		log.info("[计数游戏组件]:初始化RedisMessageListenerContainer监听事件,spring.redis.database:{}", redisDataBase);
		String subscribeChannel = "__keyevent@" + redisDataBase + "__:expired";
		container.addMessageListener(countNumRedisListener, new PatternTopic(subscribeChannel));
		return container;
	}

}
