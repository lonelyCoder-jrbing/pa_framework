package com.paas.web.rabbitmq.rabbitconfig;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * create by sumerian on 2020/5/23
 * <p>
 * desc: 队列配置，可以配置多个队列
 **/
@Configuration
public class QueueConfig {

    @Bean
    public Queue firstQueue() {
    return  new Queue("first-queue",true,false,false);
    }
    @Bean
    public Queue secondQueue() {
        return  new Queue("second-queue",true,false,false);
    }




}
