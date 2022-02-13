package com.fuhx.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

/**
 *
 *
 */
@Slf4j
//@EnableDubbo(scanBasePackages = "com.fuhx.dubbo")
@EnableAsync
@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class GatewayApplication implements CommandLineRunner
{
    public static void main( String[] args )
    {
        SpringApplication.run(GatewayApplication.class, args);
        log.info("===============Gateway START SUCCESS==============");
    }
    /**
     * test
     */
    @GetMapping("/test")
    public String test() {
        log.info("test success");
        return "test success";
    }
    /**
     * 报错 'org.springframework.http.codec.ServerCodecConfigurer' that could not be found
     * 应该是版本webflux与web中版本有冲突，手动创建一个可解决，或者移除spring-boot-starter-web
     * @return
     */
    @Bean
    public ServerCodecConfigurer serverCodecConfigurer() {
        return ServerCodecConfigurer.create();
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
