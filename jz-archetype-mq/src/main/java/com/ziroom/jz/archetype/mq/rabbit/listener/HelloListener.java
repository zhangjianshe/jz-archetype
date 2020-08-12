package com.ziroom.jz.archetype.mq.rabbit.listener;

import com.ziroom.jz.archetype.mq.rabbit.configure.RabbitMessageQueueConfigure;
import com.ziroom.jz.archetype.mq.rabbit.model.MessageHello;
import org.nutz.json.Json;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * MQ消息监听器
 */
@Component
public class HelloListener {

    @RabbitListener(queues = {RabbitMessageQueueConfigure.QUEUE_NAME_HELLO})
    @RabbitHandler
    public void process(MessageHello hello) {
        System.out.println("Receiver  : " + Json.toJson(hello));
    }

}
