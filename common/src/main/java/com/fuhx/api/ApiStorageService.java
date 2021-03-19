package com.fuhx.api;

import com.fuhx.entity.Storage;
import com.fuhx.util.Result;

public interface ApiStorageService {

    /**
     * 扣除存储数量
     */
    Result deduct(String commodityCode, int count);

    int update(Storage storage);

    Result<Storage> findByCommodityCode(String commodityCode);
}