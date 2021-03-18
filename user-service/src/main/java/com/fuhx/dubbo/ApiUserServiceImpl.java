package com.fuhx.dubbo;

import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author fuhongxing
 */
@DubboService(version = "1.0")
public class ApiUserServiceImpl implements ApiUserService{

    /**
     * 从用户账户中借出
     */
    @Override
    public void debit(String userId, int money){

    }
}