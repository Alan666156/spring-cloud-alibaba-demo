package com.fuhx.controller;

import com.fuhx.dubbo.ApiUserService;
import com.fuhx.entity.Account;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (TAccount)表控制层
 *
 * @author fuhongxing
 * @since 2021-03-18 12:21:22
 */
@RestController
@RequestMapping("tAccount")
public class AccountController {
    /**
     * 服务对象
     */
    @DubboReference
    private ApiUserService apiAccountService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Account selectOne(Integer id) {
        return null;
    }

}
