package com.ziroom.jz.archetype.mq.rabbit.configure;

import org.slf4j.Logger;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * Rabbit MQ Proxy
 */
public class RabbitProxy {

    private RabbitServerInformation serverInformation;
    private CachingConnectionFactory connectionFactory;
    private RabbitTemplate rabbitTemplate;

    public RabbitProxy(RabbitServerInformation serverInformation) {
        this.serverInformation = serverInformation;
        this.connectionFactory =createRabbitConnectionFactory(serverInformation);
        this.rabbitTemplate=createTemplate(connectionFactory);
    }

    /**
     * 根据服务器信息 创建连接工厂
     * @param serverInformation
     * @return
     */
    private CachingConnectionFactory createRabbitConnectionFactory(RabbitServerInformation serverInformation){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(serverInformation.host);
        connectionFactory.setPort(serverInformation.port);
        connectionFactory.setUsername(serverInformation.userName);
        connectionFactory.setPassword(serverInformation.password);
        connectionFactory.setVirtualHost(serverInformation.virtualHost);
        connectionFactory.setPublisherConfirms(serverInformation.publisherConfirms);
        connectionFactory.setPublisherReturns(serverInformation.publisherReturns);
        return connectionFactory;
    }

    /**
     * 创建发送对象
     * @param connectionFactory
     * @return
     */
    private RabbitTemplate createTemplate(CachingConnectionFactory connectionFactory)
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(serverInformation.mandatory);
        rabbitTemplate.setConfirmCallback((correlationData, ack, s) -> {
            if (!ack) {
            } else {
            }
        });
        rabbitTemplate.setReturnCallback((message, code, s, exchange, routingKey) -> {
        });
        return rabbitTemplate;
    }
}
