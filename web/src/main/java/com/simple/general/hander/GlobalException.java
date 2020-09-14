package com.simple.general.hander;

import com.simple.general.exception.ParameterException;
import com.simple.general.exception.UserLoginException;
import com.simple.general.response.ResponseEnum;
import com.simple.general.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 全局异常类
 *
 * @author Mr.Wu
 * @date 2020/4/12 19:22
 */
@ControllerAdvice
@Slf4j
public class GlobalException {

    /**
     * 通用异常
     *
     * @author Mr.Wu
     * @date 2020/4/12 19:28
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseResult handlerException(Exception e) {
        log.error("异常", e);
        if (e instanceof NullPointerException || e == null) {
            return ResponseResult.simpleFailed();
        }
        String message = e.getMessage();
        if (message == null) {
            return ResponseResult.simpleFailed();
        }
        int msgLength = message.length();
        int maxLength = 50;

        if (msgLength > maxLength) {
            message = message.substring(0, maxLength);
        }
        return ResponseResult.failed(ResponseEnum.FAILED.getCode(), message);
    }

    /**
     * 实体类参数值验证异常
     *
     * @param e 异常信息
     * @return com.simple.general.response.ResponseResult
     * @author Mr.Wu
     * @date 2020/4/13 23:43
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseResult methodNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<ObjectError> objectErrorList = e.getBindingResult().getAllErrors();
        List<String> errorMsg = getString(objectErrorList);
        return ResponseResult.failed(ResponseEnum.FAILED.getCode(), errorMsg.toString());
    }

    private List<String> getString(List<ObjectError> errorList) {
        List<String> stringList = new ArrayList<>();
        for (ObjectError objectError : errorList) {
            stringList.add(objectError.getDefaultMessage());
        }
        return stringList;
    }

    /**
     * 用户登录异常
     *
     * @param e 异常信息
     * @return com.simple.general.response.ResponseResult
     * @author Mr.Wu
     * @date 2020/4/18 22:20
     */
    @ExceptionHandler(UserLoginException.class)
    @ResponseBody
    public ResponseResult loginExceptionHandler(Exception e) {
        log.error("登录异常", e);
        return ResponseResult.failed(ResponseEnum.USER_AUTHENTICATION_FAILED);
    }


    /**
     * 请求参数异常
     *
     * @author Mr.Wu
     * @date 2020/4/25 23:19
     */
    @ExceptionHandler(ParameterException.class)
    @ResponseBody
    public ResponseResult parameterExceptionHandler(Exception e){
        log.error("请求参数异常", e);
        return ResponseResult.badRequest(e.getMessage());
    }
}
