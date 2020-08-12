package com.ziroom.jz.archetype.mq.rabbit.configure;

/**
 * Rabbit 消息中间件 的配置信息,用于从配置文件中接受配置参数
 */
public class RabbitServerInformation {
    public String host;
    public int port;
    public String password;
    public String userName;
    public String virtualHost;
    public boolean publisherConfirms;
    public boolean publisherReturns;
    public boolean mandatory;
}
