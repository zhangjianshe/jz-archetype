package com.ziroom.jz.archetype.client.model;

import cn.mapway.document.annotation.ApiField;
import cn.mapway.document.annotation.Doc;

import java.io.Serializable;

/**
 * 接口返回结果
 */
@Doc("接口返回的基类数据")
public class BaseRep implements Serializable {
    /**
     * 返回
     */
    @ApiField(value = "返回码 0:成功 其他:失败", example = "0")
    public int code;
    @ApiField(value = "返回的消息", example = "操作成功")
    public String message;
}
