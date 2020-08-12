package com.ziroom.jz.archetype.mq.rabbit.configure;

import com.rabbitmq.client.Channel;
import com.ziroom.jz.archetype.mq.rabbit.model.MessageHello;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 连接本地的Rabbit MQ
 */
@Component
public class RabbitServerLocal extends RabbitProxy {

    public final static String EXCHANGE_TEST = "exchange.topic.example.new";
    public final static String QUEUE_TEST = "queue.example.topic.new";
    public final static String ROUTE_TEST = "routing.key.example.new";

    public RabbitServerLocal(@Qualifier("rabbit.server1.information")
                                     RabbitServerInformation serverInformation) {
        super(serverInformation);
    }

    @Override
    protected void onInitialize(RabbitAdmin admin, RabbitTemplate template, SimpleMessageListenerContainer listenerContainer) {
        // getAdmin().declareExchange(new TopicExchange(EXCHANGE_TEST, true, false));
        // getAdmin().declareQueue(new Queue(QUEUE_TEST, true));
        Binding binding = BindingBuilder
                .bind(new Queue(QUEUE_TEST, true))        //直接创建队列
                .to(new TopicExchange(EXCHANGE_TEST, true, false))    //直接创建交换机 建立关联关系
                .with(ROUTE_TEST);//指定路由Key
        admin.declareBinding(binding);

        listenerContainer.addQueueNames(QUEUE_TEST);
    }

    /**
     * 发送消息
     *
     * @param exchange
     * @param key
     * @param hello
     */

    public void send(String exchange, String key, MessageHello hello) {
        getRabbitTemplate().convertAndSend(exchange, key, hello);
    }

    public void send(MessageHello hello) {
        send(EXCHANGE_TEST, ROUTE_TEST, hello);
    }

    @Override
    protected void handleMessage(Message message, Channel channel) {
        String data = new String(message.getBody());
        System.out.println(data);
    }
}
