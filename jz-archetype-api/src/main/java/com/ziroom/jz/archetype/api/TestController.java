package com.ziroom.jz.archetype.api;

import cn.mapway.document.annotation.DevelopmentState;
import cn.mapway.document.annotation.Doc;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ziroom.jz.archetype.client.IHelloWorld;
import com.ziroom.jz.archetype.client.model.HelloReq;
import com.ziroom.jz.archetype.client.model.HelloResp;
import com.ziroom.jz.archetype.service.IHelloWorldImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 测试控制器接口
 */
@RestController
@RequestMapping("")
@Doc("测试控制器接口")
public class TestController {

    /**
     * 引用本地的Service實現
     */
    @Autowired
    IHelloWorldImpl helloWorld;

    @Doc(value = "hello world", group = "项目测试", author = "zhang jianshe", state = DevelopmentState.FINISH)
    @RequestMapping(value = "api/v2/hello", method = RequestMethod.POST)
    public HelloResp hello(@RequestBody HelloReq req) {
        return helloWorld.hello(req);
    }

    @RequestMapping("/")
    public void home(HttpRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect("/doc/index");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 應用DUBBO服務
     */
    @Reference(lazy = true, check = false)
    IHelloWorld helloDubbo;

    @Doc(value = "hello world from dubbo", group = "项目测试", author = "zhang jianshe", state = DevelopmentState.FINISH)
    @RequestMapping(value = "api/v2/hello/dubbo", method = RequestMethod.POST)
    public HelloResp helloDubbo(@RequestBody HelloReq req) {
        return helloDubbo.hello(req);
    }


}
