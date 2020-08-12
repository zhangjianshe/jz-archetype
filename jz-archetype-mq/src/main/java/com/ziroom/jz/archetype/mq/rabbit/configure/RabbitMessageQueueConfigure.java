package com.ziroom.jz.archetype.mq.rabbit.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Rabbit MQ 消息队列服务的配置信息
 * 初始化消息队列,本配置可以配置多个Rabbit Proxy 实例
 */
@Configuration
public class RabbitMessageQueueConfigure {


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


}
