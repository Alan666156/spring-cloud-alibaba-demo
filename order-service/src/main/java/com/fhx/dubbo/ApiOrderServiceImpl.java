package com.fhx.dubbo;

import com.fuhx.dao.OrderDao;
import com.fuhx.entity.Order;
import com.fuhx.service.AccountService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(version = "1.0")
public class ApiOrderServiceImpl implements ApiOrderService {

    private OrderDao orderDao;

    private AccountService accountService;

    @Override
    public Order create(String userId, String commodityCode, int orderCount) {

//        int orderMoney = calculate(commodityCode, orderCount);
//
//        accountService.debit(userId, orderMoney);
//
//        Order order = new Order();
//        order.userId = userId;
//        order.commodityCode = commodityCode;
//        order.count = orderCount;
//        order.money = orderMoney;
//
//        // INSERT INTO orders ...
//        return orderDao.insert(order);
        return null;
    }
}