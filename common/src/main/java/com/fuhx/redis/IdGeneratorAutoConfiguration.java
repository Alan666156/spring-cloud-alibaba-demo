package com.fuhx.redis;

import com.baidu.fsg.uid.impl.CachedUidGenerator;
import com.baidu.fsg.uid.impl.DefaultUidGenerator;
import com.fuhx.redis.config.UidGeneratorProperties;
import com.fuhx.redis.sequence.SequenceGenerator;
import com.fuhx.redis.sequence.TimebaseSequenceGenerator;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 初始化配置
 * @author fuhx
 */
@Slf4j
@AutoConfigureAfter(RedissonAutoConfiguration.class)
@Configuration
public class IdGeneratorAutoConfiguration {
    @Bean
    @ConfigurationProperties("util.redis.uid")
    @ConditionalOnMissingBean(UidGeneratorProperties.class)
    public UidGeneratorProperties uidGeneratorProperties() {
        return new UidGeneratorProperties();
    }

    @Bean
    public CachedUidGenerator cachedUidGenerator(RedissonClient redissonClient, UidGeneratorProperties properties) {
        CachedUidGenerator cachedUidGenerator = new CachedUidGenerator(() -> redissonClient.getAtomicLong(properties.getWorkIdKey()).incrementAndGet());
        BeanUtils.copyProperties(cachedUidGenerator, properties);
        return cachedUidGenerator;
    }

    @Bean
    public DefaultUidGenerator defaultUidGenerator(RedissonClient redissonClient, UidGeneratorProperties properties) {
        return new DefaultUidGenerator(() -> redissonClient.getAtomicLong(properties.getWorkIdKey()).incrementAndGet());
    }

    @Bean
    public SequenceGenerator sequenceGenerator() {
        return new SequenceGenerator();
    }

    @Bean
    public TimebaseSequenceGenerator timebaseSequenceGenerator() {
        return new TimebaseSequenceGenerator();
    }

}
