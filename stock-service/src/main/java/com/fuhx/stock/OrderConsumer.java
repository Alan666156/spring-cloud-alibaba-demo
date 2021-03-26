package com.fuhx.stock;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fuhx.constant.QueueConstant;
import com.fuhx.dto.ApplyOutOrderDTO;
import com.fuhx.entity.Order;
import com.fuhx.service.StorageService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 订单出库消费
 */
@Slf4j
@Component
@RabbitListener(bindings = @QueueBinding(
        exchange = @Exchange(value = QueueConstant.EXCHANGE_FUHX, type = ExchangeTypes.TOPIC),
        value = @Queue(value = QueueConstant.QUEUE_FUHX_ORDER),
        key = QueueConstant.QUEUE_FUHX_ORDER
))
public class OrderConsumer implements InitializingBean {

    @Resource
    private RabbitAdmin           rabbitAdmin;
    @Resource
    private StorageService storageService;
    @RabbitHandler
    public void process(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("CouponOrderConsumer message receive:{}", message);
        JSONObject  msg   = JSON.parseObject(message);
        Order order = msg.toJavaObject(Order.class);
        if (!"0".equals(order.getStatus())) {
            log.info("订单申请出库MQ队列,订单状态非领取中，订单号：{}，订单状态：{}", order.getOrderNo(), order.getStatus());
            //ack确认，mq消息中移除
            channel.basicAck(tag, true);
            return;
        }

        try {
            ApplyOutOrderDTO applyOutOrderDTO = new ApplyOutOrderDTO();
            BeanUtils.copyProperties(order, applyOutOrderDTO);
            storageService.applyOutbound(applyOutOrderDTO);
            channel.basicAck(tag, true);
        } catch (Exception e) {
            log.error("订单申请出库MQ队列 error，订单号：{}", order.getOrderNo(), e);
            channel.basicNack(tag, false, true);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        org.springframework.amqp.core.Queue queue         = new org.springframework.amqp.core.Queue(QueueConstant.QUEUE_FUHX_ORDER);
        TopicExchange                       topicExchange = new TopicExchange(QueueConstant.EXCHANGE_FUHX);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareExchange(topicExchange);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(topicExchange).with(QueueConstant.QUEUE_FUHX_ORDER));
    }
}
