package com.ziroom.jz.archetype.client;

import com.ziroom.jz.archetype.client.model.HelloReq;
import com.ziroom.jz.archetype.client.model.HelloResp;

/**
 * 此接口用于框架测试，基于此模板生成的应用，可以直接删除这个接口以及实现
 *
 * @author zhangjianshe
 */
public interface IHelloWorld {

    /**
     * 测试接口，根据传入的用户名，返回问候语句
     *
     * @param req 用户名
     * @return 问候语
     */
    HelloResp hello(HelloReq req);
}
