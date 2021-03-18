package com.fuhx.controller;

import com.fuhx.dubbo.ApiStorageService;
import com.fuhx.entity.Storage;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (TStorage)表控制层
 *
 * @author fuhongxing
 * @since 2021-03-18 12:21:30
 */
@RestController
@RequestMapping("storage")
public class StorageController {
    /**
     * 服务对象
     */
    @DubboReference
    private ApiStorageService apiStorageService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Storage selectOne(Integer id) {
        return null;
    }

}
