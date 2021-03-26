//package com.fuhx.mq;
//
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.fuhx.constant.QueueConstant;
//import com.fuhx.entity.Order;
//import com.fuhx.service.OrderService;
//import com.rabbitmq.client.Channel;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.ExchangeTypes;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.amqp.rabbit.annotation.*;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.amqp.support.AmqpHeaders;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//
//@Slf4j
//@Component
//@RabbitListener(bindings = @QueueBinding(
//        exchange = @Exchange(value = QueueConstant.EXCHANGE_FUHX, type = ExchangeTypes.TOPIC),
//        value = @Queue(value = QueueConstant.QUEUE_FUHX_ORDER),
//        key = QueueConstant.QUEUE_FUHX_ORDER
//))
//public class OrderConsumer implements InitializingBean {
//
//    @Resource
//    private OrderService orderService;
//    @Resource
//    private RabbitAdmin           rabbitAdmin;
//
//    @RabbitHandler
//    public void process(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
//        log.info("CouponOrderConsumer message receive:{}", message);
//        JSONObject  msg   = JSON.parseObject(message);
//        Order order = msg.toJavaObject(Order.class);
//        if (!"0".equals(order.getStatus())) {
//            log.info("优惠券领取订单MQ队列,订单状态非领取中，订单号：{}，订单状态：{}", order.getOrderNo(), order.getStatus());
//            channel.basicAck(tag, true);
//            return;
//        }
//        //订单状态：领取中
//        try {
////            orderService.consumerCouponOrder(order);
//            channel.basicAck(tag, true);
//        } catch (Exception e) {
//            log.error("优惠券领取订单MQ队列 error，订单号：{}", order.getOrderNo(), e);
//            channel.basicNack(tag, false, true);
//        }
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        org.springframework.amqp.core.Queue queue         = new org.springframework.amqp.core.Queue(QueueConstant.QUEUE_FUHX_ORDER);
//        TopicExchange                       topicExchange = new TopicExchange(QueueConstant.EXCHANGE_FUHX);
//        rabbitAdmin.declareQueue(queue);
//        rabbitAdmin.declareExchange(topicExchange);
//        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(topicExchange).with(QueueConstant.QUEUE_FUHX_ORDER));
//    }
//}
