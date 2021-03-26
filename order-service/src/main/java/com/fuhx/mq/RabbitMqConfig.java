package com.fuhx.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Slf4j
@Configuration
public class RabbitMqConfig {

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    /**
     * 主要测试一个死信队列，功能主要实现延时消费，原理是先把消息发到正常队列，
     * 正常队列有超时时间，当达到时间后自动发到死信队列，然后由消费者去消费死信队列里的消息.
     */
    public static final String LIND_EXCHANGE = "lind.exchange";
    public static final String LIND_DL_EXCHANGE = "lind.dl.exchange";
    public static final String LIND_QUEUE = "lind.queue";
    public static final String LIND_DEAD_QUEUE = "lind.queue.dead";
    public static final String LIND_FANOUT_EXCHANGE = "lindFanoutExchange";

    /**
     * 创建普通交换机.
     */
    @Bean
    public TopicExchange lindExchange() {
        return ExchangeBuilder.topicExchange(LIND_EXCHANGE).durable(true).build();
    }

    /**
     * 创建死信交换机.
     */
    @Bean
    public TopicExchange lindExchangeDl() {
        return ExchangeBuilder.topicExchange(LIND_DL_EXCHANGE).durable(true).build();
    }

    @Bean
    @Primary
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            // 消息未从exchange->queue 被退回
            String correlationId = message.getMessageProperties().getCorrelationId();
            log.info("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}",
                    correlationId, replyCode, replyText, exchange, routingKey);
        });
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            // 消息是否发送到exchange
            String id = "";
            if (correlationData != null) {
                id = correlationData.getId();
            }
            if (ack) {
                log.info("消息发送到exchange成功 ID:{}", id);
            } else {
                log.info("消息发送到exchange失败 ID:{},原因: {}", id, cause);
            }
        });
        return rabbitTemplate;
    }

}
