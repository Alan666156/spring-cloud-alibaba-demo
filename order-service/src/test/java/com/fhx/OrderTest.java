package com.fhx;

import com.baidu.fsg.uid.impl.CachedUidGenerator;
import com.fuhx.OrderApplication;
import com.fuhx.api.ApiOrderService;
import com.fuhx.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author fuhongxing
 * @date 2021/3/24 17:09
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = OrderApplication.class)
public class OrderTest {
    /**
     * 服务对象
     */
    @Resource
    private ApiOrderService apiOrderService;
    @Resource
    private CachedUidGenerator cachedUidGenerator;

    @Test
    void testOrderSubmit(){
        log.info("====下单测试====");
        Order order = new Order();
        order.setUserId("49999");
        order.setCommodityCode("C201901140001");
        order.setAmount(1);
        order.setOrderNo(String.valueOf(cachedUidGenerator.getUID()));
        apiOrderService.create(order.getUserId(), order.getCommodityCode(), order.getCount());

    }
}
