package com.fuhx.dubbo;

public interface ApiUserService {

    /**
     * 从用户账户中借出
     */
    void debit(String userId, int money);
}