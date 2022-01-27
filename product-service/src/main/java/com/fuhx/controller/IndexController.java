package com.fuhx.controller;

import com.fuhx.util.Result;
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
@RequestMapping("/product")
public class IndexController {


    /**
     * 通过主键查询单条数据
     *
     * @return 单条数据
     */
    @GetMapping("/product")
    public Result product() {
        return Result.success();
    }

    /**
     * 查询数据
     *
     * @return 单条数据
     */
    @GetMapping("/productList")
    public Result productList() {
        return Result.success();
    }
}
