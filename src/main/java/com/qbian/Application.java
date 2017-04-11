package com.qbian;

import com.qbian.rabbitmq.Receiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by qbian on 17/4/11.
 */
@SpringBootApplication
public class Application {

    final static String BINDING_KEY             = "binding_key";    // binding key
    public final static String ROUTING_KEY      = "binding_key";    // routing key

    public final static String EXCHANGE_NAME    = "exchange_a";     // 交换机名
    final static String QUEUE_NAME              = "queue_a";        // 队列名
    final static String HOST                    = "127.0.0.1";      // rabbit mq 服务器 host
    final static String USERNAME                = "root";           // rabbit mq 服务器登录 用户名
    final static String PASSWORD                = "root";           // rabbit mq 服务器登录 密码
    final static int PORT                       = 5672;             // rabbit mq 服务器 端口号

    @Bean
    TopicExchange exchange() {

        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Queue queue() {

        return new Queue(QUEUE_NAME);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {

        return BindingBuilder.bind(queue).to(exchange).with(BINDING_KEY);
    }

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(HOST);
        connectionFactory.setPort(PORT);
        connectionFactory.setUsername(USERNAME);
        connectionFactory.setPassword(PASSWORD);
        // 设置虚拟主机
        connectionFactory.setVirtualHost("/");
        // 设置消息回调
        connectionFactory.setPublisherConfirms(true);

        return connectionFactory;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {

        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(new String[]{ QUEUE_NAME });
        container.setMessageListener(listenerAdapter);

        return container;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
