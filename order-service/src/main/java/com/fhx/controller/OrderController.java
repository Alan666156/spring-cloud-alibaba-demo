package com.fhx.controller;

import com.fhx.dubbo.ApiOrderService;
import com.fuhx.entity.Order;
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
@RestController
@RequestMapping("/order")
public class OrderController {
    /**
     * 服务对象
     */
    @DubboReference
    private ApiOrderService apiOrderService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Order selectOne(Integer id) {
        return null;
    }

}
