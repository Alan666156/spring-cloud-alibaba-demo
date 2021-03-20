package com.fuhx.controller;

import com.fuhx.api.ApiUserService;
import com.fuhx.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (TAccount)表控制层
 *
 * @author fuhongxing
 * @since 2021-03-18 12:21:22
 */
@Slf4j
@RestController
@RequestMapping("user")
public class AccountController {
    /**
     * 服务对象
     */
    @Resource
    private ApiUserService apiAccountService;

    /**
     *
     */
    @GetMapping("/userList")
    public Result userList() {
        log.info("用户查询");
        apiAccountService.getUser("1");
        return Result.success();
    }

}
