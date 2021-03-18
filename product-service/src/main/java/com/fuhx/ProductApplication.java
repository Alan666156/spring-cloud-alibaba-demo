package com.fuhx;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Hello world!
 *
 */
@Slf4j
@EnableAsync
@MapperScan("com.fuhx.dao")
@SpringBootApplication
@EnableDiscoveryClient
public class ProductApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(ProductApplication.class, args);
        log.info("===============Product START SUCCESS==============");
    }
}
