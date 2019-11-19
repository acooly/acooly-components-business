package com.acooly.module.redpack;

import static com.acooly.module.redpack.RedPackProperties.PREFIX;

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
import com.acooly.module.redpack.business.service.cache.listener.RedPackRedisListener;
import com.acooly.module.security.config.SecurityAutoConfig;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableConfigurationProperties({ RedPackProperties.class })
@ConditionalOnProperty(value = PREFIX + ".enable", matchIfMissing = true)
@ComponentScan(basePackages = "com.acooly.module.redpack")
@MapperScan(basePackages = "com.acooly.module.redpack.dao")
@AutoConfigureAfter(SecurityAutoConfig.class)
public class RedPackAutoConfig {

	/**
	 * 红包组件器监听器
	 */
	@Autowired
	private RedPackRedisListener redPackRedisListener;

	@Bean
	public StandardDatabaseScriptIniter redPackScriptIniter() {
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

	@Bean
	public RedisMessageListenerContainer redPackRedisMessageListenerContainer(RedisConnectionFactory connectionFactory,
			Environment environment) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		String redisHost = environment.getProperty("spring.redis.host");
		String redisDataBase = environment.getProperty("spring.redis.database");
		if (Strings.isBlank(redisDataBase)) {
			redisDataBase = "0";
		}
		log.info("[红包组件]:初始化RedisMessageListenerContainer监听事件,redisHost:{},redisDatabase:{}", redisHost,
				redisDataBase);
		String subscribeChannel = "__keyevent@" + redisDataBase + "__:expired";
		container.addMessageListener(redPackRedisListener, new PatternTopic(subscribeChannel));
		return container;
	}

}
