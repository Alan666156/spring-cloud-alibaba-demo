package com.fuhx;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.Serializable;

/**
 * Hello world!
 *
 */
@Slf4j
@EnableDubbo(scanBasePackages = "com.fuhx")
//@EnableRabbit
@EnableAsync
@SpringBootApplication
@EnableDiscoveryClient
public class UserApplication implements CommandLineRunner
{
    public static void main( String[] args )
    {
        SpringApplication.run(UserApplication.class, args);
        log.info("===============User START SUCCESS==============");
    }

    @Bean
    public RedisTemplate<Serializable, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Serializable, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 设置 value 的转化格式和 key 的转化格式
        redisTemplate.setValueSerializer(new GenericToStringSerializer(Object.class));
        redisTemplate.setHashValueSerializer(new GenericToStringSerializer(Object.class));
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    /**
     * 应用启动完成后，加载初始化内容
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {

    }

}
