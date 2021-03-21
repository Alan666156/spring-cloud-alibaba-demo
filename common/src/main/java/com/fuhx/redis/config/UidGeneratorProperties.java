package com.fuhx.redis.config;


import lombok.Data;

/**
 * uid基础配置信息
 * https://github.com/baidu/uid-generator/blob/master/README.zh_cn.md
 * @author fuhx
 */
@Data
public class UidGeneratorProperties {

    private String workIdKey  = "redis:uid:workId";
    /**
     * 1、对于并发数要求不高、期望长期使用的应用, 可增加timeBits位数, 减少seqBits位数.
     * 例如节点采取用完即弃的WorkerIdAssigner策略, 重启频率为12次/天, 那么配置成{"workerBits":23,"timeBits":31,"seqBits":9}时, 可支持28个节点以整体并发量14400 UID/s的速度持续运行68年.
     *
     * 2、对于节点重启频率频繁、期望长期使用的应用, 可增加workerBits和timeBits位数, 减少seqBits位数.
     * 例如节点采取用完即弃的WorkerIdAssigner策略, 重启频率为24*12次/天, 那么配置成{"workerBits":27,"timeBits":30,"seqBits":6}时, 可支持37个节点以整体并发量2400 UID/s的速度持续运行34年.
     */
    private int    timeBits   = 28;
    /**工作机器id所占位数，最多可支持约420w次机器启动。内置实现为在启动时由数据库分配，默认分配策略为用后即弃，后续可提供复用策略。*/
    private int    workerBits = 22;
    /**序列号占用的位数，每秒下的并发序列，13 bits可支持每秒8192个并发*/
    private int    seqBits    = 13;
}
