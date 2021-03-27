package com.fuhx.controller;

import com.fuhx.api.ApiStorageService;
import com.fuhx.entity.Storage;
import com.fuhx.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.IntegerCodec;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * (TStorage)表控制层
 *
 * @author fuhongxing
 * @since 2021-03-18 12:21:30
 */
@Slf4j
@RestController
@RequestMapping("storage")
public class StorageController {
    /**
     * 服务对象
     */
    @Resource
    private ApiStorageService apiStorageService;
    @Resource
    private RedissonClient redissonClient;
    private DefaultRedisScript<Long> defaultRedisScript;
    /**
     * 初始化lua库存扣减脚本
     */
    @PostConstruct
    public void init(){
        defaultRedisScript = new DefaultRedisScript<>();
        //设置lua脚本返回值类型
        defaultRedisScript.setResultType(Long.class);
        //加载lua脚本
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("rateLimit.lua")));
    }
    /**
     * 通过主键查询单条数据
     *
     * @return 单条数据
     */
    @GetMapping("/storageList")
    public Result storageList() {
        log.info("======库存查询=======");
        return Result.success();
    }

    /**
     * 库存扣减测试
     *
     * @return 单条数据
     */
    @GetMapping("/subStock")
    public Result subStock() {
        log.info("======库存扣减=======");
        RLock lock = redissonClient.getLock("user:" + Thread.currentThread().getId());
        try {
            if (!lock.tryLock(0, 30, TimeUnit.SECONDS)) {
                log.info("并发锁操作" + Thread.currentThread().getId());
                return Result.failure("请求过于频繁，请稍后再试");
            }
            String code = "C201901140001";
            Result<Storage> result = apiStorageService.findByCommodityCode(code);
            //限流的key
            String limitKey ="limitKey";
            //限制次数
            long limit = 100;
            //限制时间
            long expire = 120;
            //执行lua
            Long res = redissonClient.getScript(IntegerCodec.INSTANCE).eval(RScript.Mode.READ_WRITE, defaultRedisScript.getScriptAsString(), RScript.ReturnType.INTEGER, Arrays.asList(limitKey), limit, expire);
            if(res == 0){
                log.warn("库存不足，请稍后再试");
                return Result.failure("请求过于频繁，请稍后再试");
            }
            if(result.successful()){
                apiStorageService.update(result.getData().setCount(Math.toIntExact(res)));
            }
        } catch (Exception e) {
            log.error("库存扣减异常:", e);
        }finally {
            if(lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
        return Result.success();
    }


}
