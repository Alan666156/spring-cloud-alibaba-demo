package com.fuhx;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Hello world!
 *
 */
@Slf4j
@EnableAsync
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
