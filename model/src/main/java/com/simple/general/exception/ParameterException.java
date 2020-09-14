package com.simple.general.exception;

/**
 * 请求参数异常
 *
 * @author Mr.Wu
 * @date 2020/4/25 23:09
 */
public class ParameterException extends RuntimeException {

    private static final long serialVersionUID = 1836568649158699344L;

    public ParameterException() {
    }

    public ParameterException(String message) {
        super(message);
    }

    public ParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterException(Throwable cause) {
        super(cause);
    }
}
