package com.ziroom.jz.archetype.client.model;

import cn.mapway.document.annotation.ApiField;
import cn.mapway.document.annotation.Doc;

import java.io.Serializable;

/**
 * Hello Req
 */
@Doc("Hello请求参数")
public class HelloReq implements Serializable {
    /**
     * 用户名称
     */
    @ApiField(value = "用户名", example = "张建设")
    public String userName;
}
