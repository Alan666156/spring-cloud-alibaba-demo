package com.fuhx.controller;

import com.fuhx.dubbo.ApiOrderService;
import com.fuhx.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
    @DubboReference(version = "1.0")
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

}
