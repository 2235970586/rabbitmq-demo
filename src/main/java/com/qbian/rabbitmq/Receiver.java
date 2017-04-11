package com.qbian.rabbitmq;

import org.springframework.stereotype.Service;

/**
 * Created by qbian on 17/4/11.
 */
@Service
public class Receiver {

    /**
     * 监听 {Application.QUEUE_NAME} 队列内的消息
     * @param message 接收到的消息内容
     */
    void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
    }

}
