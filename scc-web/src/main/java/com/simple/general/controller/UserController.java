package com.simple.general.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.simple.general.annotation.OperationLogDetail;
import com.simple.general.entity.User;
import com.simple.general.exception.ParameterException;
import com.simple.general.group.QueryGroup;
import com.simple.general.group.SaveGroup;
import com.simple.general.group.UpdateGroup;
import com.simple.general.query.BaseQuery;
import com.simple.general.response.ResponseResult;
import com.simple.general.service.SystemLogService;
import com.simple.general.service.UserService;
import com.simple.general.utils.MD5Utils;
import com.simple.general.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


/**
 * 用户模块
 *
 * @author Mr.Wu
 * @date 2020/4/12 18:34
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final SystemLogService systemLogService;

    private static final String OPERATION = "用户模块";

    @Autowired
    public UserController(UserService userService, SystemLogService systemLogService) {
        this.userService = userService;
        this.systemLogService = systemLogService;
    }

    /**
     * 添加用户
     *
     * @param user 用户
     * @return ResponseResult
     * @author Mr.Wu
     * @date 2020/4/12 18:42
     */
    @OperationLogDetail(operation = OPERATION,detail = "添加用户")
    @RequiresPermissions("user:save")
    @PostMapping("/manage")
    public ResponseResult saveUser(@Validated(SaveGroup.class) @RequestBody User user) {
        userService.saveUser(user);
        return ResponseResult.simpleOk();
    }

    /**
     * 修改用户
     *
     * @param user 内容
     * @return com.simple.general.response.ResponseResult
     * @author Mr.Wu
     * @date 2020/4/25 22:44
     */
    @OperationLogDetail(operation = OPERATION,detail = "修改用户")
    @RequiresPermissions("user:update")
    @PutMapping("/manage")
    public ResponseResult updateUser(@Validated(UpdateGroup.class) @RequestBody User user) {
        userService.updateUser(user);
        return ResponseResult.simpleOk();
    }

    /**
     * 删除用户
     *
     * @param userObject 用户id数组
     * @return com.simple.general.response.ResponseResult
     * @author Mr.Wu
     * @date 2020/4/25 22:54
     */
    @OperationLogDetail(operation = OPERATION,detail = "删除用户")
    @RequiresPermissions("user:delete")
    @PutMapping("/manage/delete")
    public ResponseResult deleteUser(@RequestBody JSONObject userObject) {
        JSONArray userIds = userObject.getJSONArray("id");
        if (userIds == null || userIds.isEmpty()) {
            throw new ParameterException("id不能为空");
        }
        userService.deleteUser(JSONArray.parseArray(userIds.toJSONString(), Integer.class));
        return ResponseResult.simpleOk();
    }

    /**
     * 分页查询用户
     *
     * @param userQuery 查询条件
     * @return com.simple.general.response.ResponseResult
     * @author Mr.Wu
     * @date 2020/4/12 18:59
     */
    @RequiresPermissions("user:query")
    @PostMapping("/manage/page")
    public ResponseResult pageListUsers(@Validated @RequestBody BaseQuery userQuery) {
        Page<User> userPage = userService.pageListUsers(userQuery);
        return ResponseResult.ok(userPage.getContent(), userPage.getTotalElements());
    }

    /**
     * 用户登录
     *
     * @param user 用户名，密码
     * @return com.simple.general.response.ResponseResult
     * @author Mr.Wu
     * @date 2020/4/16 00:54
     */
    @PostMapping("/manage/login")
    public ResponseResult login(@Validated(QueryGroup.class) @RequestBody User user, HttpServletRequest request, HttpSession session) {
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), MD5Utils.MD5(user.getPassword()));
        token.setRememberMe(true);
        currentUser.login(token);
        UserVO userVO = (UserVO) currentUser.getPrincipal();
        if (userVO == null) {
            throw new AuthenticationException();
        }
        Map<String,Object> responseMap = new HashMap<>(2);
        String sessionId = currentUser.getSession().getId().toString();
        responseMap.put("sessionId",sessionId);
        responseMap.put("user",userVO);
        session.setAttribute("user", userVO);
        session.setMaxInactiveInterval(60 * 60);
        systemLogService.saveUserLog(request, session);
        return ResponseResult.ok(responseMap);
    }

    /**
     * 用户退出
     *
     * @param session 清空用户登录信息
     * @return org.springframework.http.ResponseEntity
     * @author wcy
     * @date 2020/1/13 15:01
     */
    @GetMapping("/logout")
    public ResponseResult loginOut(HttpSession session, HttpServletRequest request) {
        String sessionId = session.getId();
        if (StringUtils.isNotBlank(sessionId)) {
            systemLogService.updateUserLog(sessionId);
        }
        request.getSession().invalidate();
        systemLogService.updateUserLog(sessionId);
        return ResponseResult.simpleOk();
    }

}
