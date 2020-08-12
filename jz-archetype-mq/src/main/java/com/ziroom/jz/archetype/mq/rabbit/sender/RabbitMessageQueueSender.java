package com.ziroom.jz.archetype.mq.rabbit.sender;

import com.ziroom.jz.archetype.mq.rabbit.model.MessageHello;
import org.springframework.stereotype.Component;


/**
 * 向消息队列发送消息
 */
@Component
public class RabbitMessageQueueSender {


    /**
     * 发送hello到 rabbitMQ
     *
     * @param hello
     */
    public void send(MessageHello hello) {


    }
}
