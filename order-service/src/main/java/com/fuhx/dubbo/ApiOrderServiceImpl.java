package com.fuhx.dubbo;

import com.baidu.fsg.uid.impl.CachedUidGenerator;
import com.fuhx.api.ApiOrderService;
import com.fuhx.api.ApiStorageService;
import com.fuhx.api.ApiUserService;
import com.fuhx.dao.OrderDao;
import com.fuhx.entity.Order;
import com.fuhx.mq.RabbitMqProducer;
import com.fuhx.util.Result;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.redisson.api.RedissonClient;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * @author fuhx
 */
@Service(version = "1.0")
public class ApiOrderServiceImpl implements ApiOrderService {

    @Resource
    private OrderDao orderDao;
    @Reference(version = "1.0", check =false)
    private ApiUserService userService;
    @Reference(version = "1.0", check =false)
    private ApiStorageService storageService;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private CachedUidGenerator cachedUidGenerator;
    @Resource
    private RabbitMqProducer rabbitMqProducer;

    @GlobalTransactional
    @Override
    public Result<Order> create(String userId, String commodityCode, int orderCount) {
        //金额扣减
        int orderMoney = 100;
        //扣减库存
//        storageService.deduct(commodityCode, orderCount);
        userService.debit(userId, orderMoney);
//        int i = 1/0;
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderNo(String.valueOf(cachedUidGenerator.getUID()));
        order.setCommodityCode(commodityCode);
        order.setCount(orderCount);
        order.setAmount(orderMoney);
        order.setStatus("0");
        int count = orderDao.insert(order);
        //库存系统
        rabbitMqProducer.sendOrder(order);
        return Result.success(order);
    }

    @Override
    public Result<Order> getOrder(String orderNo) {
        Example example = new Example(Order.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderNo", orderNo);
        Order order = orderDao.selectOneByExample(example);
        if(order == null){
            return Result.failure("订单不存在");
        }
        return Result.success(order);
    }
}