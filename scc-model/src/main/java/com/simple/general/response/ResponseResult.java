package com.simple.general.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"code", "message", "total", "data"})
public class ResponseResult implements Serializable {

    private static final long serialVersionUID = -2173834343174795301L;

    private Integer code;

    private String message;

    private Long total;

    private Object data;

    public static ResponseResult simpleOk() {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ResponseEnum.OK.getCode());
        responseResult.setMessage(ResponseEnum.OK.getMessage());
        return responseResult;
    }

    public static ResponseResult ok(List<?> data,Long total){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ResponseEnum.OK.getCode());
        responseResult.setMessage(ResponseEnum.OK.getMessage());
        responseResult.setData(data);
        responseResult.setTotal(total);
        return responseResult;
    }

    public static ResponseResult ok(List<?> data){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ResponseEnum.OK.getCode());
        responseResult.setMessage(ResponseEnum.OK.getMessage());
        responseResult.setData(data);
        return responseResult;
    }

    public static ResponseResult ok(Object data){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ResponseEnum.OK.getCode());
        responseResult.setMessage(ResponseEnum.OK.getMessage());
        responseResult.setData(data);
        return responseResult;
    }

    public static ResponseResult simpleFailed() {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ResponseEnum.FAILED.getCode());
        responseResult.setMessage(ResponseEnum.FAILED.getMessage());
        return responseResult;
    }

    public static ResponseResult failed(int code,String message){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(code);
        responseResult.setMessage(message);
        return responseResult;
    }

    public static ResponseResult failed(ResponseEnum responseEnum){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(responseEnum.getCode());
        responseResult.setMessage(responseEnum.getMessage());
        return responseResult;
    }

    public static ResponseResult badRequest(String msg){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ResponseEnum.BAD_REQUEST.getCode());
        responseResult.setMessage(msg);
        return responseResult;
    }

}
