package com.qbian.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by qbian on 17/4/11.
 */
@RestController
public class RabbitMQController {

    @Autowired
    Sender sender;

    @GetMapping("/rabbitmq/{content}")
    String receiveAndSenderMsg(@PathVariable(value = "content") String content) {
        sender.sendToExchange_a(content);

        return "succeed";
    }

}
