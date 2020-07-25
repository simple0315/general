package com.simple.general.controller;

import com.simple.general.entity.OperateLog;
import com.simple.general.entity.UserLog;
import com.simple.general.query.BaseQuery;
import com.simple.general.response.ResponseResult;
import com.simple.general.service.SystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system")
public class SystemLogController {

    private final SystemLogService systemLogService;

    @Autowired
    public SystemLogController(SystemLogService systemLogService) {
        this.systemLogService = systemLogService;
    }

    /**
     * 查询用户登录日志
     *
     * @param baseQuery 条件
     * @return com.simple.general.response.ResponseResult
     * @author Mr.Li
     * @date 2020/5/20 00:31
     */
    @PostMapping("/user/log")
    public ResponseResult pageUserLogList(@RequestBody BaseQuery baseQuery) {
        Page<UserLog> userLogPage = systemLogService.pageListUserLogs(baseQuery);
        return ResponseResult.ok(userLogPage.getContent(), userLogPage.getTotalElements());
    }

    /**
     * 查询用户操作日志
     *
     * @param baseQuery 条件
     * @return com.simple.general.response.ResponseResult
     * @author Mr.Li
     * @date 2020/5/20 00:31
     */
    @PostMapping("/operate/log")
    public ResponseResult pageOperateLogList(@RequestBody BaseQuery baseQuery) {
        Page<OperateLog> operateLogPage = systemLogService.pageListOperateLogs(baseQuery);
        return ResponseResult.ok(operateLogPage.getContent(), operateLogPage.getTotalElements());
    }


}
