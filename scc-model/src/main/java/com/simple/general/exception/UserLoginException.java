package com.simple.general.exception;

/**
 * 登录异常类
 *      
 * @author Mr.Wu
 * @date 2020/4/18 22:00
 */
public class UserLoginException extends RuntimeException {

    private static final long serialVersionUID = -135876550155855838L;

    public UserLoginException() {
    }

    public UserLoginException(String message) {
        super(message);
    }

    public UserLoginException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public UserLoginException(Throwable throwable) {
        super(throwable);
    }

}
