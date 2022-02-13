package com.fuhx.controller;

import com.fuhx.elk.CommodityDTO;
import com.fuhx.elk.CommodityService;
import com.fuhx.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@RequestMapping("/product")
public class IndexController {

    @Autowired
    private CommodityService commodityService;
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
        Page<CommodityDTO> page = commodityService.pageQuery(0, 10, "切片");
        log.info("result:{}", page.getContent());
        log.info("count result:{}", commodityService.count());
        return Result.success();
    }
}
