package com.dwyinyuan.generate.model.dto;

import com.dwyinyuan.generate.constant.Sys_Constant;

import java.io.Serializable;


/**
 * @author 公众号:笛舞音缘
 * @version 1.0
 * @description 封装通用的返回对象
 * @date 2021/7/27
 */
public class ResponseResult implements Serializable {

    private String msg;

    private int code;

    private Object data;

    public ResponseResult() {
    }

    public ResponseResult(String msg, int code, Object data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    /**
     * 请求成功的返回结果
     */
    public static ResponseResult success() {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(Sys_Constant.SUCCESS_CODE);
        responseResult.setData(null);
        responseResult.setMsg(Sys_Constant.SUCCESS_MSG);
        return responseResult;
    }

    /**
     * 请求成功的返回结果
     *
     * @param data 数据
     */
    public static ResponseResult success(Object data) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(Sys_Constant.SUCCESS_CODE);
        responseResult.setData(data);
        responseResult.setMsg(Sys_Constant.SUCCESS_MSG);
        return responseResult;
    }

    /**
     * 请求成功的返回结果
     *
     * @param data 数据
     * @param msg  消息
     */
    public static ResponseResult success(Object data, String msg) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(Sys_Constant.SUCCESS_CODE);
        responseResult.setData(data);
        responseResult.setMsg(msg);
        return responseResult;
    }

    /**
     * 请求失败后的返回结果
     */
    public static ResponseResult error() {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(Sys_Constant.FAIL_CODE);
        responseResult.setData(null);
        responseResult.setMsg(Sys_Constant.FAIL_MSG);
        return responseResult;
    }

    /**
     * 请求失败后的返回结果
     *
     * @param msg
     */
    public static ResponseResult error(int code, String msg) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(code);
        responseResult.setData(null);
        responseResult.setMsg(msg);
        return responseResult;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public int getCode() {
        return code;
    }

    public ResponseResult setCode(int code) {
        this.code = code;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ResponseResult setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "LazyResult{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}
