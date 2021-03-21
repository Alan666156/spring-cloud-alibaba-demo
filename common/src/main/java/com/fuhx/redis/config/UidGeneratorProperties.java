package com.fuhx.redis.config;


import lombok.Data;

/**
 * 算法
 * @author fuhx
 */
@Data
public class UidGeneratorProperties {

    private String workIdKey  = "redis:uid:workId";
    private int    timeBits   = 28;
    /**工作机器id所占位数*/
    private int    workerBits = 22;
    /**序列号占用的位数*/
    private int    seqBits    = 13;
}
