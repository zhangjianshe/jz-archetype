package com.ziroom.jz.archetype.mq.rabbit.model;

import java.io.Serializable;
import java.util.Date;

/**
 * MQ消息体
 */
public class MessageHello implements Serializable {
    public String name;
    public Date now;
    public String id;
}
