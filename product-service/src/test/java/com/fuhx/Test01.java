package com.fuhx;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author fuhongxing
 * @date 2021/4/1 19:01
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ProductApplication.class)
public class Test01 {
    @Test
    public void contextLoads() {
        System.out.println(111);
    }
}
