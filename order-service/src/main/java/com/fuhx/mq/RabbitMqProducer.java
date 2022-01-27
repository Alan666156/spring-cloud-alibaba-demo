package com.fuhx.mq;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.fuhx.constant.QueueConstant;
import com.fuhx.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * mq生产者
 */
@Component
@Slf4j
public class RabbitMqProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String routingKey, String msgId, Object message) {
        send(QueueConstant.EXCHANGE_FUHX, routingKey, msgId, message);
    }

    public void send(String exchange, String routingKey, String msgId, Object message) {
        if (!(message instanceof String)) {
            message = JSONObject.toJSONString(message);
        }
        // 定义消息ID唯一确定消息
        log.info("Send MQ Message:{}, {}, {}, {}", exchange, routingKey, msgId, message);
        rabbitTemplate.convertAndSend(exchange, routingKey, message, new CorrelationData(msgId));
    }

    /**
     * 调用库存系统下订单
     */
    public void sendOrder(Order order) {
        send(QueueConstant.QUEUE_FUHX_ORDER, IdUtil.fastUUID(), order);
    }

}
