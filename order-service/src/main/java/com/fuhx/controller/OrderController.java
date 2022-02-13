package com.fuhx.controller;

import com.fuhx.api.ApiOrderService;
import com.fuhx.entity.Order;
import com.fuhx.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * (TOrder)表控制层
 *
 * @author fuhongxing
 * @since 2021-03-18 12:21:29
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    /**
     * 服务对象
     */
    @Resource
    private ApiOrderService apiOrderService;

    /**
     * 订单列表查询
     *
     * @return 单条数据
     */
    @GetMapping("/orderList")
    public Result orderList() {
        log.info("订单列表查询");
        return Result.success();
    }

    /**
     * 下单
     *
     * @return 单条数据
     */
    @PostMapping("/submit")
    public Result submitOrder(@RequestBody Order order) {
        log.info("下单请求:{}", order);
        return apiOrderService.create("49999", order.getCommodityCode(), order.getCount());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param orderNo 订单号
     * @return 单条数据
     */
    @GetMapping("/myOrder/{orderNo}")
    public Result order(@PathVariable(name = "orderNo") String orderNo) {
        log.info("订单查询");
        return apiOrderService.getOrder(orderNo);
    }

}
