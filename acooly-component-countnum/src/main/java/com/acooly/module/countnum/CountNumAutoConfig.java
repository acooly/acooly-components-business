package com.acooly.module.countnum;

import static com.acooly.module.countnum.CountNumProperties.PREFIX;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.acooly.core.common.dao.support.StandardDatabaseScriptIniter;
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

//	@Bean
//    public RedisMessageListenerContainer configRedisMessageListenerContainer(RedisConnectionFactory connectionFactory,
//                                                                             @Qualifier("configlistenerAdapter") MessageListenerAdapter listenerAdapter, ThreadPoolTaskExecutor commonTaskExecutor) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setTaskExecutor(commonTaskExecutor);
//        container.addMessageListener(listenerAdapter, new PatternTopic("game_count_num_urls"));
//        return container;
//    }
//
//    @Bean
//    public MessageListenerAdapter configlistenerAdapter(RedisTemplate redisTemplate) {
//        return new MessageListenerAdapter((MessageListener) (message, pattern) -> {
//            String key = (String) redisTemplate.getValueSerializer().deserialize(message.getBody());
//            log.info("配置管理[key={}]更新", key.replace("sgame_count_num_urls", ""));
//        });
//    }

}
