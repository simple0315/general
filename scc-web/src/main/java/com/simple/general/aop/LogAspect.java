package com.simple.general.aop;

import com.alibaba.fastjson.JSON;
import com.simple.general.annotation.OperationLogDetail;
import com.simple.general.dao.OperateLogDao;
import com.simple.general.dao.UserLogDao;
import com.simple.general.entity.OperateLog;
import com.simple.general.entity.User;
import com.simple.general.utils.DateUtils;
import com.simple.general.utils.HttpRequestUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * aop切面
 *
 * @author Mr.Wu
 * @date 2020/9/9 23:54
 */
@Aspect
@Component
public class LogAspect {

    final OperateLogDao operateLogDao;

    final UserLogDao userLogDao;

    public LogAspect(OperateLogDao operateLogDao, UserLogDao userLogDao) {
        this.operateLogDao = operateLogDao;
        this.userLogDao = userLogDao;
    }

    /**
     * 此处的切点是注解的方式，也可以用包名的方式达到相同的效果
     * '@Pointcut("execution(* com.simple.general.annotation.*.*(..))")'
     */
    @Pointcut("@annotation(com.simple.general.annotation.OperationLogDetail)")
    public void operationLog() {
    }

    /**
     * 环绕增强，相当于MethodInterceptor
     */
    @Around("operationLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object res;
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        assert requestAttributes != null;
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        User user = HttpRequestUtils.getUserFromSession(request.getSession());
        OperateLog operateLog = new OperateLog();
        operateLog.setId(userLogDao.getId());
        operateLog.setTimestamp(DateUtils.now());
        operateLog.setRemoteHost(HttpRequestUtils.getRemoteAddress(request));
        operateLog.setUsername(user.getUsername());
        OperationLogDetail annotation = signature.getMethod().getAnnotation(OperationLogDetail.class);
        if (annotation != null) {
            operateLog.setOperation(annotation.operation());
            operateLog.setDetail(annotation.detail());
        }
        // 这里保存日志
        System.out.println("记录日志:" + operateLog.toString());
        try {
            res = joinPoint.proceed();
            operateLog.setStatus("成功");
            operateLogDao.save(operateLog);
            return res;
        } catch (Exception e) {
            System.out.println("LogAspect 操作失败：" + e.getMessage());
            operateLog.setStatus("失败");
            operateLogDao.save(operateLog);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 对当前登录用户和占位符处理
     *
     * @param argNames   方法参数名称数组
     * @param args       方法参数数组
     * @param annotation 注解信息
     * @return 返回处理后的描述
     */
    private String getDetail(String[] argNames, Object[] args, OperationLogDetail annotation) {

        Map<Object, Object> map = new HashMap<>(4);
        for (int i = 0; i < argNames.length; i++) {
            map.put(argNames[i], args[i]);
        }

        String detail = annotation.detail();
        try {
            detail = "'" + "#{currentUserName}" + "'=》" + annotation.detail();
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                Object k = entry.getKey();
                Object v = entry.getValue();
                detail = detail.replace("{{" + k + "}}", JSON.toJSONString(v));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    @Before("operationLog()")
    public void doBeforeAdvice(JoinPoint joinPoint) {
        System.out.println("进入方法前执行.....");
    }

    /**
     * 处理完请求，返回内容
     *
     * @param ret ret
     */
    @AfterReturning(returning = "ret", pointcut = "operationLog()")
    public void doAfterReturning(Object ret) {
        System.out.println("方法的返回值 : " + ret);
    }

    /**
     * 后置异常通知
     */
    @AfterThrowing("operationLog()")
    public void throwss(JoinPoint jp) {
        System.out.println("方法异常时执行.....");
    }

    /**
     * 后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
     */
    @After("operationLog()")
    public void after(JoinPoint jp) {
        System.out.println("方法最后执行.....");
    }

}
