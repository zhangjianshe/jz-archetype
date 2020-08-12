package com.ziroom.jz.archetype.mq.rabbit.configure;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * Rabbit MQ Proxy
 * 负责处理Rabbit MQ的服务器建立和各种对象的管理
 * 子类具体处理某一个Rabbit MQ的逻辑代码
 * 在 <code>OnInitialize()</code> 方法中创建交换机和队列以及绑定关系
 */
public abstract class RabbitProxy {

    public RabbitAdmin getAdmin() {
        return admin;
    }

    private final RabbitAdmin admin;

    public RabbitServerInformation getServerInformation() {
        return serverInformation;
    }

    public CachingConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public RabbitTemplate getRabbitTemplate() {
        return rabbitTemplate;
    }

    private final RabbitServerInformation serverInformation;
    private final CachingConnectionFactory connectionFactory;
    private final RabbitTemplate rabbitTemplate;

    public RabbitProxy(RabbitServerInformation serverInformation) {
        this.serverInformation = serverInformation;
        this.connectionFactory = createRabbitConnectionFactory(serverInformation);
        this.rabbitTemplate = createTemplate(connectionFactory);
        this.admin = createAdmin(connectionFactory);
        this.onInitialize();
    }


    /**
     * 根据服务器信息 创建连接工厂
     *
     * @param serverInformation
     * @return
     */
    private CachingConnectionFactory createRabbitConnectionFactory(RabbitServerInformation serverInformation) {
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
     *
     * @param connectionFactory
     * @return
     */
    private RabbitTemplate createTemplate(CachingConnectionFactory connectionFactory) {
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

    /**
     * 创建消息队列管理对象
     *
     * @param connectionFactory
     * @return
     */
    private RabbitAdmin createAdmin(CachingConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    /**
     * 初始化,子类在此方法中创建交换机和队列以及绑定关系
     */
    protected abstract void onInitialize();
}
