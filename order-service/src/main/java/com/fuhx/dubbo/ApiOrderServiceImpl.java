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
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.redisson.api.RLock;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.IntegerCodec;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author fuhx
 */
@Slf4j
@DubboService(version = "1.0")
public class ApiOrderServiceImpl implements ApiOrderService {

    @Resource
    private OrderDao orderDao;
    @DubboReference(version = "1.0", check =false)
    private ApiUserService userService;
    @DubboReference(version = "1.0", check =false)
    private ApiStorageService storageService;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private CachedUidGenerator cachedUidGenerator;
    @Resource
    private RabbitMqProducer rabbitMqProducer;
    /**
     * lua库存扣减
     */
    private String                       subStockScript       = "local c = redis.call('DECRBY', KEYS[1], ARGV[1]);" +
            "if(c < 0) then " +
            "redis.call('INCRBY', KEYS[1], ARGV[1]);" +
            "return -1;" +
            "else " +
            "return c;" +
            "end;";
    /**
     * 存放redis产品库存key
     */
    public static final String PRODUCT_STORE_REDIS_KEY = "productStore";

    /**
     * 创建订单
     * @param userId 用户
     * @param commodityCode 商品编码
     * @param orderCount 购买数量
     * @return
     */
    @GlobalTransactional
    @Override
    public Result<Order> create(String userId, String commodityCode, int orderCount) {
        RLock lock = redissonClient.getLock("user:" + redissonClient);
        try {
            if (!lock.tryLock(0, 10, TimeUnit.SECONDS)) {
                log.info("并发锁操作" + Thread.currentThread().getId());
                return Result.failure("请求过于频繁，请稍后再试");
            }
            //金额扣减
            int orderMoney = 100;
            //扣减库存，从redis中获取商品库存数据，进行库存预占扣减
            Long n = redissonClient.getScript(IntegerCodec.INSTANCE).eval(RScript.Mode.READ_WRITE, subStockScript, RScript.ReturnType.INTEGER,
                    Collections.singletonList(PRODUCT_STORE_REDIS_KEY + commodityCode), orderCount);
            if (n == -1) {
                log.info("{}请求下单, {}商品库存不足:{}", userId, commodityCode, n);
                //TODO 此处可用map集合标记库存是否足够
                return Result.failure("已售罄");
            }
//            storageService.deduct(commodityCode, orderCount);
            //用户扣款
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
            //mq通知库存系统
            rabbitMqProducer.sendOrder(order);
            return Result.success(order);
        } catch (Exception e) {
            log.error("订单创建异常:", e);
//            return Result.failure("订单创建失败");
        }finally {
            if(lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
        return Result.success();
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