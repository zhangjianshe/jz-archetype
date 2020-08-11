package com.ziroom.jz.archetype.config.model;

import cn.mapway.document.annotation.ApiField;
import cn.mapway.document.annotation.Doc;

import java.io.Serializable;

@Doc("返回结果")
public class Result implements Serializable {
    @ApiField(
            value = "返回码",
            example = "200"
    )
    public int code;
    @ApiField(
            value = "消息",
            example = "成功"
    )
    public String message;


    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }


    public void setCode(final int code) {
        this.code = code;
    }

    public void setMessage(final String message) {
        this.message = message;
    }


    public Result() {
    }

    public Result(final int code, final String message) {
        this.code = code;
        this.message = message;

    }

    public String toString() {
        return "Result(code=" + this.getCode() + ", message=" + this.getMessage() + ")";
    }

    /**
     * 失败消息
     *
     * @param code
     * @param message
     * @return
     */
    public static Result fail(int code, String message) {
        return new Result(code, message);
    }
}