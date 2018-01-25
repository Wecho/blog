package com.wecho.core.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * http响应的包装类
 */
@Data
public class ResultBean<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int SUCCESS = 0;

    public static final int FAIL = 1;

    public static final int NO_PERMISSION = 2;

    private String msg = "success";

    private int code = SUCCESS;

    private T data;

    public ResultBean() {
    }

    public ResultBean(T data) {
        this("success",SUCCESS,data);
    }

    public ResultBean(String msg, int code, T data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

}