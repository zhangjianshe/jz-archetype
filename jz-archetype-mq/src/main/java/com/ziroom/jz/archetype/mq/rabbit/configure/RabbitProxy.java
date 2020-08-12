package com.ziroom.jz.archetype.mq.rabbit.configure;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.ConsumerTagStrategy;

import java.util.UUID;

/**
 * Rabbit MQ Proxy
 * 负责处理Rabbit MQ的服务器建立和各种对象的管理
 * 子类具体处理某一个Rabbit MQ的逻辑代码
 * 在 <code>OnInitialize()</code> 方法中创建交换机和队列以及绑定关系
 */
public abstract class RabbitProxy implements ChannelAwareMessageListener {

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
    private final SimpleMessageListenerContainer listenerContainer;


    public RabbitProxy(RabbitServerInformation serverInformation) {
        this.serverInformation = serverInformation;
        this.connectionFactory = createRabbitConnectionFactory(serverInformation);
        this.rabbitTemplate = createTemplate(connectionFactory);
        this.admin = createAdmin(connectionFactory);
        this.listenerContainer = createListener(connectionFactory);
        this.listenerContainer.setMessageListener(this);
        this.onInitialize(admin, rabbitTemplate, listenerContainer);
    }

    private SimpleMessageListenerContainer createListener(CachingConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConcurrentConsumers(1);    //当前的消费者数量
        container.setMaxConcurrentConsumers(5); //  最大的消费者数量
        container.setDefaultRequeueRejected(false); //是否重回队列
        container.setAcknowledgeMode(AcknowledgeMode.AUTO); //签收模式
        container.setExposeListenerChannel(true);
        container.setConsumerTagStrategy(new ConsumerTagStrategy() {    //消费端的标签策略
            @Override
            public String createConsumerTag(String queue) {
                return queue + "_" + UUID.randomUUID().toString();
            }
        });

        return container;
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
     *
     * @param admin
     * @param template
     * @param listenerContainer
     */
    protected abstract void onInitialize(RabbitAdmin admin, RabbitTemplate template, SimpleMessageListenerContainer listenerContainer);

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        handleMessage(message, channel);
    }

    protected abstract void handleMessage(Message message, Channel channel);
}
