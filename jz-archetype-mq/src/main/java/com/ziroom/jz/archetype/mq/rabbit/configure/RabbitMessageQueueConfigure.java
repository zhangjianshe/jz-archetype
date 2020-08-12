package com.ziroom.jz.archetype.mq.rabbit.configure;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Rabbit MQ 消息队列服务的配置信息
 * 初始化消息队列,本配置可以配置多个Rabbit Proxy 实例
 */
@Configuration
public class RabbitMessageQueueConfigure {

    public final static String QUEUE_NAME_HELLO = "hello-mq";


    /**
     * server1 配置信息
     *
     * @return
     */
    @Bean("rabbit.server1.information")
    @ConfigurationProperties(prefix = "rabbit1")
    public RabbitServerInformation rabbitServer1() {
        return new RabbitServerInformation();
    }

    /**
     * server2 配置信息
     *
     * @return
     */
    @Bean("rabbit.server2.information")
    @ConfigurationProperties(prefix = "rabbit2")
    public RabbitServerInformation rabbitServer2() {
        return new RabbitServerInformation();
    }

    @Bean(name = "rabbit.server2.connectionFactory")
    public CachingConnectionFactory server2ConnectionFactory(
            @Qualifier("rabbit.server2.information") RabbitServerInformation serverInformation) {
        return createRabbitConnectionFactory(serverInformation);
    }

    @Bean(name = "rabbit.server1.connectionFactory")
    public CachingConnectionFactory server1ConnectionFactory(
            @Qualifier("rabbit.server1.information") RabbitServerInformation serverInformation) {
            return createRabbitConnectionFactory(serverInformation);
    }


}
