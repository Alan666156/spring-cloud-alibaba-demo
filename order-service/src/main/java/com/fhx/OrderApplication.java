package com.fhx;

import cn.hutool.json.JSONString;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Hello world!
 *
 */
@Slf4j
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
