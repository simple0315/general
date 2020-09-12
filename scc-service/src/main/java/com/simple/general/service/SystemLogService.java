package com.simple.general.service;

import com.simple.general.entity.OperateLog;
import com.simple.general.entity.UserLog;
import com.simple.general.query.BaseQuery;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 日志模块
 *
 * @author Mr.Wu
 * @date 2020/5/19 00:12
 */
public interface SystemLogService {

    /**
     * 添加用户登录日志
     *
     * @param request request
     * @param session session
     * @author Mr.Wu
     * @date 2020/5/19 00:15
     */
    void saveUserLog(HttpServletRequest request,HttpSession session);

    /**
     * 修改用户登录日志
     *
     * @param sessionId sessionId
     * @author Mr.Wu
     * @date 2020/5/19 00:15
     */
    void updateUserLog(String sessionId);

    /**
     * 分页查询用户登录日志
     *
     * @param userLogQuery 条件
     * @return org.springframework.data.domain.Page<com.simple.general.entity.UserLog>
     * @author Mr.Wu
     * @date 2020/5/19 00:25
     */
    Page<UserLog> pageListUserLogs(BaseQuery userLogQuery);

    /**
     * 分页查询用户操作日志
     *
     * @param operateLogQuery 条件
     * @return org.springframework.data.domain.Page<com.simple.general.entity.OperateLog>
     * @author Mr.Wu
     * @date 2020/5/19 00:25
     */
    Page<OperateLog> pageListOperateLogs(BaseQuery operateLogQuery);

}
