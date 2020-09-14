package com.simple.general.dao;

import com.simple.general.entity.Permission;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

/**
 * 权限
 *
 * @author Mr.Wu
 * @date 2020/4/27 23:43
 */
public interface PermissionDao {

    /**
     * 查询所有权限
     *
     * @return java.util.List<com.simple.general.entity.Permission>
     * @author Mr.Wu
     * @date 2020/4/27 23:49
     */
    List<Permission> findAll();

    /**
     * 添加权限
     *
     * @param permissionList 权限列表
     * @author Mr.Wu
     * @date 2020/4/28 23:45
     */
    void saveMany(List<Permission> permissionList);

    /**
     * 查询权限
     *
     * @param condition 条件
     * @return java.util.List<com.simple.general.entity.Permission>
     * @author Mr.Wu
     * @date 2020/4/28 23:48
     */
    List<Permission> findByCondition(Criteria condition);

    /**
     * 查询权限数量
     *
     * @param condition 条件
     * @return long
     * @author Mr.Wu
     * @date 2020/4/29 00:44
     */
    long getCountByCondition(Criteria condition);
}
