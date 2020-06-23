package com.paas.web.rabbitmq.rabbitconfig.deadletterqueueconfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * create by sumerian on 2020/5/26
 * <p>
 * desc:
 **/
@Configuration
public class DeadQueueConfig {
    /***
     * 死信队列交换机标识符
     */
    public static final String DEAD_LETTER_QUEUE_KEY = "x-dead-letter-exchange";

    /**
     * 死信队列交换机绑定键标识符
     */
    private static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

    /***
     * 私信队列跟交换机类型没有关系，不一定为directExchange，不影响该类型的交换器特性
     */

    @Bean("deadLetterExchange")
    public DirectExchange deadletterExchange() {
        return new DirectExchange("DL_EXCHANGE", true, false);
    }

    /**
     * 声明一个私信队列，x-dead-letter-exchange对应死信交换器，x-dead-letter-routing-key对应私信队列
     */
    @Bean("deadLetterQueue")
    public Queue deadLetterQueue() {
        Map<String, Object> args = new HashMap<>(2);
        args.put(DEAD_LETTER_QUEUE_KEY, "DL_EXCHANGE");
        args.put(DEAD_LETTER_ROUTING_KEY, "KEY_R");
        return QueueBuilder.durable("DL_QUEUE").withArguments(args).build();
    }

    /***
     * 定义死信队列转发队列
     */
    @Bean
    public Queue redirectQueue() {
        return QueueBuilder.durable("REDIRECT_QUEUE").build();
    }

    /***
     * 死信队列通过DL_KEY 绑定键绑定到死信队列上
     */
    @Bean
    public Binding deadletterBinding() {
        return new Binding("DL_QUEUE", Binding.DestinationType.QUEUE, "DL_EXCHANGE", "DL_KEY", null);
    }


    /**
     * 死信路由通过 KEY_R 绑定键绑定到死信队列上.
     *
     * @return the binding
     */
    @Bean
    public Binding redirectBinding() {
        return new Binding("REDIRECT_QUEUE", Binding.DestinationType.QUEUE, "DL_EXCHANGE", "KEY_R", null);
    }


}
