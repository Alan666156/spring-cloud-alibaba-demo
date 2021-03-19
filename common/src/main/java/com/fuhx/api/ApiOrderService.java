package com.fuhx.api;

import com.fuhx.entity.Order;
import com.fuhx.util.Result;

public interface ApiOrderService {

    /**
     * 创建订单
     */
    Result<Order> create(String userId, String commodityCode, int orderCount);
}