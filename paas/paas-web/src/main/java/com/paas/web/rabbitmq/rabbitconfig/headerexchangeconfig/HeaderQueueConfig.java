package com.paas.web.rabbitmq.rabbitconfig.headerexchangeconfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Hashtable;
import java.util.Map;

/**
 * create by sumerian on 2020/5/27
 * <p>
 * desc:
 **/
@Configuration
public class HeaderQueueConfig {
    /***
     * 声明队列
     * @return
     */
    @Bean(name = "header_queue")
    public Queue getQueue() {
        return new Queue("header_queue");
    }

    /***
     * 声明交换机
     * @return
     */
    @Bean("headerExchange")
    public HeadersExchange getExchange() {
        return new HeadersExchange("headerExchange");
    }

    /****
     * 将交换机和队列进行绑定
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding bindingExchangeTopicQueue(@Qualifier("header_queue") Queue queue, @Qualifier("headerExchange") HeadersExchange exchange) {
        Map<String, Object> table = new Hashtable<>();
        table.put("one", "A");
        table.put("two", "B");
        return BindingBuilder.bind(queue).to(exchange).whereAny(table).match();
//        return BindingBuilder.bind(queue).to(exchange).whereAny(table).match();
    }
}
