package com.fuhx.dubbo;

import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class ApiUserServiceImpl implements ApiUserService{

    /**
     * 从用户账户中借出
     */
    @Override
    public void debit(String userId, int money){

    }
}