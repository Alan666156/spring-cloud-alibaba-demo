package com.fuhx.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import com.fuhx.constant.QueueConstant;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class RabbitMqConfig {

    /**
     * 创建普通交换机.
     * 如果exchange和queue两者之间有一个持久化，一个非持久化,则不允许建立绑定。
     * 注意：一旦创建了队列和交换机，就不能修改其标志了。例如创建了一个non-durable的队列，然后想把它改变成durable的，唯一的办法就是删除这个队列然后重新创建
     */
    @Bean
    public TopicExchange topicExchange() {
        return ExchangeBuilder.topicExchange(QueueConstant.EXCHANGE_FUHX).durable(true).build();
    }

    /**
     * 通过延时队列处理，进入死信队列
     * @return
     */
    @Bean
    public Queue orderQueue() {
        Map<String, Object> params = new HashMap<>();
        // x-dead-letter-exchange 声明了队列里的死信转发到的DLX名称，
        params.put("x-dead-letter-exchange", "immediate");
        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key
        params.put("x-dead-letter-routing-key", QueueConstant.QUEUE_DLX);
        // 设置队列中消息的过期时间，单位 毫秒
        params.put("x-message-ttl", 5 * 1000);
        return new Queue(QueueConstant.QUEUE_FUHX_ORDER, true, false, false, params);
    }

    @Bean
    public Binding bindingOrderQueue() {
        return BindingBuilder.bind(orderQueue())
                .to(topicExchange())
                .with(QueueConstant.QUEUE_FUHX_ORDER);
    }

    /**
     * 创建死信交换机.
     */
    @Bean
    public TopicExchange dlxExchange() {
        //durable持久化
        return ExchangeBuilder.topicExchange(QueueConstant.EXCHANGE_DLX).durable(true).build();
    }

    /**
     * 声明死信队列
     * @return Queue
     */
    @Bean
    public Queue dlxQueue() {
        return new Queue(QueueConstant.QUEUE_DLX);
    }

    /**
     * 绑定死信队列到死信交换机
     * @return Binding
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(dlxQueue())
                .to(dlxExchange())
                .with(QueueConstant.QUEUE_DLX);
    }

    @Bean
    @Primary
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        //失败处理
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            // 消息未从exchange->queue 被退回
            String correlationId = message.getMessageProperties().getCorrelationId();
            log.info("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}",
                    correlationId, replyCode, replyText, exchange, routingKey);
        });
        //消息发送到 Broker 后触发回调，确认消息是否到达 Broker 服务器
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            // 消息是否发送到exchange
            String id = "";
            if (correlationData != null) {
                id = correlationData.getId();
            }
            if (ack) {
                log.info("消息发送到exchange成功 消息ID:{}", id);
            } else {
                log.info("消息发送到exchange失败 ID:{},原因: {}", id, cause);
            }
        });
        return rabbitTemplate;
    }

}
