package com.ziroom.jz.archetype.client.model;

import cn.mapway.document.annotation.ApiField;
import cn.mapway.document.annotation.Doc;

@Doc("Hello 接口返回的数据")
public class HelloResp extends BaseRep{
    @ApiField(value = "问候语",example = "Hello,zhangjianshe")
    public String data;
}
