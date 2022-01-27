package com.fuhx.constant;

/**
 * @author fuhongxing
 * @date 2021/3/25 19:30
 */

public class QueueConstant {
    /**
     * 交换机
     */
    public static final String EXCHANGE_FUHX = "exchange_fuhx";
    /**
     * 订单
     */
    public static final String QUEUE_FUHX_ORDER = "queue_fuhx_order";

    /**
     * 主要测试一个死信队列，功能主要实现延时消费，原理是先把消息发到正常队列，
     * 正常队列有超时时间，当达到时间后自动发到死信队列，然后由消费者去消费死信队列里的消息.
     */
    public static final String EXCHANGE_DLX = "exchange_dlx";
    public static final String QUEUE_DLX = "queue_dlx";
}
