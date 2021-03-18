package com.fhx.dubbo;

import com.fuhx.entity.Order;

public interface ApiOrderService {

    /**
     * 创建订单
     */
    Order create(String userId, String commodityCode, int orderCount);
}