package com.fuhx.dubbo;

import com.fuhx.util.Result;

public interface ApiStorageService {

    /**
     * 扣除存储数量
     */
    Result deduct(String commodityCode, int count);
}