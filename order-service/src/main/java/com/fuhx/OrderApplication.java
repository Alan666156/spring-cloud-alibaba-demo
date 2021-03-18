package com.fuhx;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Hello world!
 *
 */
@Slf4j
@EnableDubbo(scanBasePackages = "com.fuhx.dubbo")
@EnableDiscoveryClient
@SpringBootApplication
public class OrderApplication
{
    public static void main( String[] args )
    {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(OrderApplication.class, args);
        log.info("===============Order START SUCCESS==============");
    }
}
