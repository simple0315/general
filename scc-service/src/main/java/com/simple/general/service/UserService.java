package com.simple.general.service;

import com.simple.general.entity.User;
import com.simple.general.query.BaseQuery;
import com.simple.general.vo.UserVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 用户模块
 *
 * @author Mr.Wu
 * @date 2020/4/12 16:24
 */
public interface UserService {

    /**
     * 添加用户
     *
     * @param user 用户内容
     * @author Mr.Wu
     * @date 2020/4/12 16:27
     */
    void saveUser(User user);

    /**
     * 修改用户
     *
     * @param user 修改内容
     * @author Mr.Wu
     * @date 2020/4/23 00:05
     */
    void updateUser(User user);

    /**
     * 单个/批量删除用户
     *
     * @param ids id数组
     * @author Mr.Wu
     * @date 2020/4/23 00:07
     */
    void deleteUser(List<Integer> ids);

    /**
     * 分页查询用户
     *
     * @param userQuery 查询条件
     * @return org.springframework.data.domain.Page<com.simple.general.entity.User>
     * @author Mr.Wu
     * @date 2020/4/12 16:30
     */
    Page<User> pageListUsers(BaseQuery userQuery);

    /**
     * 登录用户
     *
     * @param user 用户信息
     * @author Mr.Wu
     * @date 2020/4/18 21:43
     */
    void login(User user);

    /**
     * 通过用户名查询用户
     *
     * @param name 用户名
     * @return com.simple.general.entity.User
     * @author Mr.Wu
     * @date 2020/4/23 23:36
     */
    UserVO getUserByName(String name);
}
