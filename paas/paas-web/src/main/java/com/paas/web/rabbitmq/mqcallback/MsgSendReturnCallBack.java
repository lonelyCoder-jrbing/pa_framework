package com.paas.web.rabbitmq.mqcallback;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * create by sumerian on 2020/5/26
 * <p>
 * desc:
 **/
public class MsgSendReturnCallBack implements RabbitTemplate.ReturnCallback {
    @Override
    public void returnedMessage(Message message, int replyCode, String desc, String exchangeName, String routeKey) {
        System.out.println("err code :" + replyCode);
        System.out.println("错误消息的描述 :" + desc);
        System.out.println("错误的交换机是 :" + exchangeName);
        System.out.println("错误的路右键是 :" + routeKey);

    }
}
