package com.simple.general.controller;

import com.simple.general.response.ResponseResult;
import com.simple.general.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限
 *
 * @author Mr.Wu
 * @date 2020/4/27 23:50
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    /**
     * 查询所有权限
     *
     * @return com.simple.general.response.ResponseResult
     * @author Mr.Wu
     * @date 2020/4/27 23:52
     */
    @GetMapping("/manage")
    public ResponseResult findAll() {
        return ResponseResult.ok(permissionService.findAll());
    }
}
