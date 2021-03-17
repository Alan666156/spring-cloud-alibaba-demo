package com.fuhx.service;

import lombok.extern.slf4j.Slf4j;
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
 *
 *
 */
@Slf4j
//@EnableDubbo(scanBasePackages = "com.fuhx.order")
@EnableAsync
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication implements CommandLineRunner
{
    public static void main( String[] args )
    {
        SpringApplication.run(GatewayApplication.class, args);
        log.info("===============Gateway START SUCCESS==============");
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
