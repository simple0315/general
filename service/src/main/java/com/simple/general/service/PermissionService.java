package com.simple.general.service;

import com.simple.general.entity.Permission;

import java.util.List;

/**
 * 权限
 *
 * @author Mr.Wu
 * @date 2020/4/27 23:47
 */
public interface PermissionService {

    /**
     * 查询所有权限
     *
     * @return java.util.List<com.simple.general.entity.Permission>
     * @author Mr.Wu
     * @date 2020/4/27 23:48
     */
    List<Permission> findAll();

    /**
     * 查询权限
     *
     * @param ids 权限id列表
     * @return java.util.List<com.simple.general.entity.Permission>
     * @author Mr.Wu
     * @date 2020/4/28 23:52
     */
    List<Permission> findPermissionByIds(List<Integer> ids);

}
