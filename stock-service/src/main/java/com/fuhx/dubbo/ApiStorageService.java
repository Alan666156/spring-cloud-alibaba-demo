package com.fuhx.dubbo;

public interface ApiStorageService {

    /**
     * 扣除存储数量
     */
    void deduct(String commodityCode, int count);
}