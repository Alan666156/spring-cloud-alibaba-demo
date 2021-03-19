package com.fuhx.api;

import com.fuhx.util.Result;

public interface ApiUserService {

    /**
     * 从用户账户中借出
     */
    Result debit(String userId, int money);
}