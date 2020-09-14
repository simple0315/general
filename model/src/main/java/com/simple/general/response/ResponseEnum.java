package com.simple.general.response;

/**
 * @author Mr.Wu
 * @date 2020/4/12 19:01
 */
public enum ResponseEnum {

    /**
     * 成功
     */
    OK(0, "OK"),

    /**
     *失败
     */
    FAILED(-1, "FAILED"),

    /**
     * 请求参数有误
     */
    BAD_REQUEST(400,"请求参数有误"),

    /**
     * 未登陆
     */
    NOT_LOGIN(401,"用户未登录或登陆过期"),

    /**
     * 未授权
     */
    UNAUTHORIZED(403,"当前用户无权限执行此操作"),

    /**
     * 用户名,密码有误
     */
    USER_AUTHENTICATION_FAILED(1001,"用户名或密码有误");

    private final Integer code;
    private final String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
