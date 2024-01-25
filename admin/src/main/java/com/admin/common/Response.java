package com.admin.common;

import lombok.Data;

@Data
public class Response<T> {

    private int code ;
    private String msg;
    private T data;

    public Response(int code, String message, T data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    public Response(int code, String message) {
        this.code = code;
        this.msg = message;
        this.data = null;
    }
}
