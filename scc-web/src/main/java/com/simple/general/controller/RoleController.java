package com.simple.general.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.simple.general.entity.Role;
import com.simple.general.exception.ParameterException;
import com.simple.general.group.SaveGroup;
import com.simple.general.group.UpdateGroup;
import com.simple.general.query.BaseQuery;
import com.simple.general.response.ResponseResult;
import com.simple.general.service.RoleService;
import com.simple.general.service.SystemLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 角色
 *
 * @author Mr.Wu
 * @date 2020/4/26 23:58
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    private final SystemLogService systemLogService;

    private static final String OPERATION = "角色模块";

    @Autowired
    public RoleController(RoleService roleService, SystemLogService systemLogService) {
        this.roleService = roleService;
        this.systemLogService = systemLogService;
    }

    /**
     * 添加角色
     *
     * @param role 角色
     * @return com.simple.general.response.ResponseResult
     * @author Mr.Wu
     * @date 2020/4/27 00:00
     */
    @RequiresPermissions("role:save")
    @PostMapping("/manage")
    public ResponseResult addRole(@Validated(SaveGroup.class) @RequestBody Role role, HttpServletRequest request, HttpSession session) {
        roleService.addRole(role);
        systemLogService.saveOperateLog(request, session, OPERATION, "添加角色");
        return ResponseResult.simpleOk();
    }

    /**
     * 修改角色
     *
     * @param role 角色
     * @return com.simple.general.response.ResponseResult
     * @author Mr.Wu
     * @date 2020/4/27 00:00
     */
    @RequiresPermissions("role:update")
    @PutMapping("/manage")
    public ResponseResult updateRole(@Validated(UpdateGroup.class) @RequestBody Role role, HttpServletRequest request, HttpSession session) {
        roleService.updateRole(role);
        systemLogService.saveOperateLog(request, session, OPERATION, "修改角色");
        return ResponseResult.simpleOk();
    }

    /**
     * 删除角色
     *
     * @param roleObject 角色id数组
     * @return com.simple.general.response.ResponseResult
     * @author Mr.Wu
     * @date 2020/4/27 00:00
     */
    @RequiresPermissions("role:delete")
    @PutMapping("/manage/delete")
    public ResponseResult deleteRole(@Validated @RequestBody JSONObject roleObject, HttpServletRequest request, HttpSession session) {
        JSONArray roleIds = roleObject.getJSONArray("id");
        if (roleIds == null || roleIds.isEmpty()) {
            throw new ParameterException("id不能为空");
        }
        roleService.deleteRole(JSONArray.parseArray(roleIds.toJSONString(), Integer.class));
        systemLogService.saveOperateLog(request, session, OPERATION, "删除角色");
        return ResponseResult.simpleOk();
    }

    /**
     * 查询所有角色
     *
     * @return com.simple.general.response.ResponseResult
     * @author Mr.Wu
     * @date 2020/4/27 00:00
     */
    @RequiresPermissions("role:query")
    @GetMapping("/manage")
    public ResponseResult findAllRole() {
        List<Role> roleList = roleService.findAll();
        return ResponseResult.ok(roleList);
    }

    /**
     * 分页查询角色
     *
     * @param roleQuery 条件
     * @return com.simple.general.response.ResponseResult
     * @author Mr.Wu
     * @date 2020/5/6 23:32
     */
    @RequiresPermissions("role:query")
    @PostMapping("/manage/page")
    public ResponseResult pageListUsers(@Validated @RequestBody BaseQuery roleQuery) {
        Page<Role> rolePage = roleService.pageListRoles(roleQuery);
        return ResponseResult.ok(rolePage.getContent(), rolePage.getTotalElements());
    }

}
