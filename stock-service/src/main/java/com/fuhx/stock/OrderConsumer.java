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
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

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

    /**
     * deliveryTag（唯一标识 ID）：当一个消费者向 RabbitMQ 注册后，会建立起一个 Channel ，RabbitMQ 会用 basic.deliver 方法向消费者推送消息，这个方法携带了一个 delivery tag， 它代表了 RabbitMQ 向该 Channel 投递的这条消息的唯一标识 ID
     * @param message
     * @param channel
     * @param headers
     * @throws IOException
     */
    @RabbitHandler
    public void process(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag, @Headers Map<String, Object> headers) throws IOException {
        //生产者传递的唯一表示，可以根据此参数进行消息的幂等验证，将处理过的数据存入redis缓存
        String correlationId = null;
        if(headers.get("spring_returned_message_correlation") != null){
            correlationId = headers.get("spring_returned_message_correlation").toString();
        }
        log.info("stock Order Consumer message receive:{}, tag:{}, correlationId:{}", message, headers.get(AmqpHeaders.DELIVERY_TAG), correlationId);
        JSONObject  msg   = JSON.parseObject(message);
        Order order = msg.toJavaObject(Order.class);
        if (!"0".equals(order.getStatus())) {
            log.info("订单申请出库MQ队列,订单状态非成功，订单号：{}，订单状态：{}", order.getOrderNo(), order.getStatus());
            //ack确认，mq消息中移除
            channel.basicAck((Long) headers.get(AmqpHeaders.DELIVERY_TAG), true);
            return;
        }

        try {
            ApplyOutOrderDTO applyOutOrderDTO = new ApplyOutOrderDTO();
            BeanUtils.copyProperties(order, applyOutOrderDTO);
            storageService.applyOutbound(applyOutOrderDTO);
            log.info("订单号：{}处理成功.", order.getOrderNo());
            channel.basicAck((Long) headers.get(AmqpHeaders.DELIVERY_TAG), true);
        } catch (Exception e) {
            log.error("订单申请出库MQ队列 error，订单号：{}", order.getOrderNo(), e);
            channel.basicNack((Long) headers.get(AmqpHeaders.DELIVERY_TAG), false, true);
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
