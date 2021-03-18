package com.fuhx.controller;

import com.fuhx.dubbo.ApiUserService;
import com.fuhx.entity.Account;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
@RequestMapping("account")
public class AccountController {
    /**
     * 服务对象
     */
    @DubboReference
    private ApiUserService apiAccountService;

    /**
     *
     */
    @GetMapping("/userList")
    public Account userList() {
        log.info("");
        return null;
    }

}
