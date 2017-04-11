package com.qbian.rabbitmq;

import com.qbian.Application;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by qbian on 17/4/11.
 */
@Service
public class Sender {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendToExchange_a(String content) {
        String message = content + " --:-- " + new Date();
        System.out.println("Sender " + Application.EXCHANGE_NAME + " exchange : " + message);
        // 发送消息到 exchange_a 交换机
        this.rabbitTemplate.convertAndSend(Application.EXCHANGE_NAME, Application.ROUTING_KEY, message);
    }

}
