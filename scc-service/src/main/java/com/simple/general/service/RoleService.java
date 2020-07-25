package com.simple.general.service;

import com.simple.general.entity.Role;
import com.simple.general.query.BaseQuery;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 角色
 *
 * @author Mr.Wu
 * @date 2020/4/26 23:58
 */
public interface RoleService {

    /**
     * 添加角色
     *
     * @param role 角色
     * @author Mr.Wu
     * @date 2020/4/26 23:44
     */
    void addRole(Role role);

    /**
     * 修改角色
     *
     * @param role 内容
     * @author Mr.Wu
     * @date 2020/4/26 23:43
     */
    void updateRole(Role role);

    /**
     * 删除角色
     *
     * @param ids 角色id数组
     * @author Mr.Wu
     * @date 2020/4/26 23:43
     */
    void deleteRole(List<Integer> ids);


    /**
     * 查询所有角色
     *
     * @return java.util.List<com.simple.general.entity.Role>
     * @author Mr.Wu
     * @date 2020/4/26 23:43
     */
    List<Role> findAll();

    /**
     * 分页查询角色
     *
     * @param roleQuery 条件
     * @return org.springframework.data.domain.Page<com.simple.general.entity.Role>
     * @author Mr.Wu
     * @date 2020/5/6 23:36
     */
    Page<Role> pageListRoles(BaseQuery roleQuery);

    /**
     * 查询角色
     *
     * @param roleId id
     * @return com.simple.general.entity.Role
     * @author Mr.Wu
     * @date 2020/5/26 00:52
     */
    Role findRoleById(Integer roleId);
}
