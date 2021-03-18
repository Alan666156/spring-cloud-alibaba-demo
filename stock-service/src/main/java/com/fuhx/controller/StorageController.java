package com.fuhx.controller;

import com.fuhx.dubbo.ApiStorageService;
import com.fuhx.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (TStorage)表控制层
 *
 * @author fuhongxing
 * @since 2021-03-18 12:21:30
 */
@Slf4j
@RestController
@RequestMapping("storage")
public class StorageController {
    /**
     * 服务对象
     */
    @DubboReference(version = "1.0")
    private ApiStorageService apiStorageService;

    /**
     * 通过主键查询单条数据
     *
     * @return 单条数据
     */
    @GetMapping("/storageList")
    public Result storageList() {
        log.info("======库存查询=======");
        return Result.success();
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Result selectOne(Integer id) {
        log.info("库存查询");
        return Result.success();
    }
}
