package com.ziroom.jz.archetype.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ziroom.jz.archetype.client.IHelloWorld;
import com.ziroom.jz.archetype.client.model.HelloReq;
import com.ziroom.jz.archetype.client.model.HelloResp;
import com.ziroom.jz.archetype.mq.rabbit.configure.RabbitServerLocal;
import com.ziroom.jz.archetype.mq.rabbit.model.MessageHello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * 此接口用于框架测试，基于此模板生成的应用，可以直接删除这个接口以及实现
 *
 * @author zhangjianshe
 */
@Component
@Service
public class IHelloWorldImpl implements IHelloWorld {

    @Autowired
    RabbitServerLocal rabbitLocal;

    @Override
    public HelloResp hello(HelloReq req) {
        HelloResp resp = new HelloResp();
        resp.data = "Hello " + req.userName;

        MessageHello hello = new MessageHello();
        hello.id= UUID.randomUUID().toString();
        hello.name=req.userName;
        hello.now=new Date();
        rabbitLocal.send(hello);

        return resp;
    }
}
