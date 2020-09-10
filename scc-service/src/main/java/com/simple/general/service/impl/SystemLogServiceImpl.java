package com.simple.general.service.impl;

import com.simple.general.dao.OperateLogDao;
import com.simple.general.dao.UserLogDao;
import com.simple.general.entity.OperateLog;
import com.simple.general.entity.UserLog;
import com.simple.general.query.BaseQuery;
import com.simple.general.service.SystemLogService;
import com.simple.general.utils.DateUtils;
import com.simple.general.utils.HttpRequestUtils;
import com.simple.general.utils.MongoConstUtils;
import com.simple.general.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 系统日志
 *
 * @author Mr.Wu
 * @date 2020/5/28 23:51
 */
@Service("systemLogService")
public class SystemLogServiceImpl implements SystemLogService {

    private final UserLogDao userLogDao;

    private final OperateLogDao operateLogDao;

    @Autowired
    public SystemLogServiceImpl(UserLogDao userLogDao, OperateLogDao operateLogDao) {
        this.userLogDao = userLogDao;
        this.operateLogDao = operateLogDao;
    }

    @Override
    public void saveUserLog(HttpServletRequest request, HttpSession session) {
        UserLog userLog = new UserLog();
        UserVO userVO = (UserVO) session.getAttribute("user");
        if (userVO != null) {
            userLog.setUserId(userVO.getId());
            userLog.setUsername(userVO.getUsername());
        }
        userLog.setId(userLogDao.getId());
        userLog.setLoginTime(DateUtils.now());
        userLog.setLogoutTime(0);
        userLog.setSessionId(session.getId());
        userLog.setRemoteHost(HttpRequestUtils.getRemoteAddress(request));
        userLogDao.save(userLog);
    }

    @Override
    public void updateUserLog(String sessionId) {
        UserLog userLog = new UserLog();
        userLog.setSessionId(sessionId);
        userLog.setLogoutTime(DateUtils.now());
        userLogDao.update(userLog);
    }

    @Override
    public Page<UserLog> pageListUserLogs(BaseQuery userLogQuery) {
        String name = userLogQuery.getName();
        Criteria condition = Criteria.where("logout_time").ne(0);
        if (StringUtils.isNotBlank(name)) {
            condition.and(MongoConstUtils.USERNAME).regex(name);
        }
        return userLogDao.pageList(condition, userLogQuery.getPageNo(), userLogQuery.getPageSize());
    }

    @Override
    public void saveOperateLog(HttpServletRequest request, HttpSession session, String operation, String detail) {
        UserVO user = (UserVO) session.getAttribute("user");
        OperateLog operateLog = new OperateLog();
        operateLog.setId(userLogDao.getId());
        if(user!=null){
            operateLog.setUsername(user.getUsername());
        }
        operateLog.setRemoteHost(HttpRequestUtils.getRemoteAddress(request));
        operateLog.setDetail(detail);
        operateLog.setOperation(operation);
        operateLog.setTimestamp(DateUtils.now());
        operateLogDao.save(operateLog);
    }

    @Override
    public Page<OperateLog> pageListOperateLogs(BaseQuery operateLogQuery) {
        String name = operateLogQuery.getName();
        Criteria condition = new Criteria();
        if (StringUtils.isNotBlank(name)) {
            condition.and(MongoConstUtils.USERNAME).regex(name);
        }
        return operateLogDao.pageList(condition, operateLogQuery.getPageNo(), operateLogQuery.getPageSize());
    }

}
