package com.fhx;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

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
        SpringApplication.run(OrderApplication.class, args);
        log.info("===============Stock START SUCCESS==============");
    }
}
