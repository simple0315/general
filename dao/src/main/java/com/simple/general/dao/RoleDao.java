package com.simple.general.dao;

import com.simple.general.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

/**
 * 角色
 *
 * @author Mr.Wu
 * @date 2020/4/25 23:41
 */
public interface RoleDao {

    /**
     * 添加角色
     *
     * @param role 内容
     * @author Mr.Wu
     * @date 2020/4/25 23:45
     */
    void save(Role role);

    /**
     * 修改角色
     *
     * @param role 内容
     * @author Mr.Wu
     * @date 2020/4/25 23:46
     */
    void update(Role role);

    /**
     * 删除角色
     *
     * @param condition 条件
     * @author Mr.Wu
     * @date 2020/4/25 23:46
     */
    void delete(Criteria condition);

    /**
     * 查询单个角色
     *
     * @param id 角色id
     * @return com.simple.general.entity.Role
     * @author Mr.Wu
     * @date 2020/4/26 00:18
     */
    Role findById(Integer id);

    /**
     * 查询单个角色
     *
     * @param name 角色名称
     * @return com.simple.general.entity.Role
     * @author Mr.Wu
     * @date 2020/4/26 00:18
     */
    Role findByName(String name);

    /**
     * 查询所有角色
     *
     * @return java.util.List<com.simple.general.entity.Role>
     * @author Mr.Wu
     * @date 2020/4/25 23:44
     */
    List<Role> findAll();

    /**
     * id
     *
     * @return int id
     * @author Mr.Wu
     * @date 2020/4/25 23:47
     */
    int getId();

    /**
     * 查询数量
     *
     * @param condition 条件
     * @return long
     * @author Mr.Wu
     * @date 2020/4/25 23:54
     */
    long getCountByCondition(Criteria condition);

    /**
     * 分页查询角色
     *
     * @param condition 条件
     * @param pageNo    页码
     * @param pageSize  数量
     * @return org.springframework.data.domain.Page<com.simple.general.entity.Role>
     * @author Mr.Wu
     * @date 2020/5/6 23:40
     */
    Page<Role> pageList(Criteria condition, Integer pageNo, Integer pageSize);
}
