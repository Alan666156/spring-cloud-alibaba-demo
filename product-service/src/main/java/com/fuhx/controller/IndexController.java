package com.fuhx.controller;

import com.fuhx.entity.Order;
import com.fuhx.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (TOrder)表控制层
 *
 * @author fuhongxing
 * @since 2021-03-18 12:21:29
 */
@RestController
@RequestMapping("/product")
public class IndexController {


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
