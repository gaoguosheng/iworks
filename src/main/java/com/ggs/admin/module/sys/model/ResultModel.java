package com.ggs.admin.module.sys.model;

/**
 * Rest返回json模型
 * Created by gswon on 2017/8/24.
 */
public class ResultModel{
    private String errorcode="0";
    private String message="";
    private Object data;

    public ResultModel() {
    }

    public ResultModel(String errorcode, String message) {
        this.errorcode = errorcode;
        this.message = message;
    }


    public ResultModel(String errorcode) {
        this.errorcode = errorcode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultModel(String errorcode, String message, Object data) {
        this.errorcode = errorcode;
        this.message = message;
        this.data = data;
    }
}
