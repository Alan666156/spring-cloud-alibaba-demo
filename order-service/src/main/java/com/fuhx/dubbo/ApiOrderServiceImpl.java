package com.fuhx.dubbo;

import com.fuhx.dao.OrderDao;
import com.fuhx.entity.Order;
import com.fuhx.util.Result;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService(version = "1.0")
public class ApiOrderServiceImpl implements ApiOrderService {

    @Resource
    private OrderDao orderDao;
    @DubboReference(version = "1.0")
    private ApiUserService userService;
    @DubboReference(version = "1.0")
    private ApiStorageService storageService;

//    @GlobalTransactional
    @Override
    public Result<Order> create(String userId, String commodityCode, int orderCount) {
        //金额扣减
        int orderMoney = 100;
        //扣减库存
        storageService.deduct(commodityCode, orderCount);
        userService.debit(userId, orderMoney);

        Order order = new Order();
        order.setUserId(userId);
        order.setCommodityCode(commodityCode);
        order.setCount(orderCount);
        order.setAmount(orderMoney);
        int count = orderDao.insert(order);
        return Result.success(order);
    }
}