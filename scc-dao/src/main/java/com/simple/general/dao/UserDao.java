package com.simple.general.dao;

import com.simple.general.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Criteria;

/**
 * 用户
 *
 * @author Mr.Wu
 * @date 2020/4/12 15:33
 */
public interface UserDao {
    /**
     * 添加用户
     *
     * @param user 内容
     * @author Mr.Wu
     * @date 2020/4/12 15:36
     */
    void save(User user);

    /**
     * 修改用户
     *
     * @param user 信息
     * @author Mr.Wu
     * @date 2020/4/22 23:31
     */
    void update(User user);

    /**
     * 删除用户
     *
     * @param condition 条件
     * @author Mr.Wu
     * @date 2020/4/22 23:31
     */
    void deleteByCondition(Criteria condition);

    /**
     * 查询单个用户
     *
     * @param id 用户id
     * @return com.simple.general.entity.User
     * @author Mr.Wu
     * @date 2020/4/12 17:45
     */
    User findById(Integer id);

    /**
     * 分页查询用户
     *
     * @param condition 条件
     * @param pageNo    页码
     * @param pageSize  查询数量
     * @return org.springframework.data.domain.Page<com.simple.general.entity.User>
     * @author Mr.Wu
     * @date 2020/4/12 15:38
     */
    Page<User> pageList(Criteria condition, int pageNo, int pageSize);

    /**
     * 获取id
     *
     * @author Mr.Wu
     * @date 2020/4/12 16:44
     */
    int getId();

    /**
     * 查询数量
     *
     * @param condition 条件
     * @return long
     * @author Mr.Wu
     * @date 2020/4/12 17:42
     */
    long getCountByCondition(Criteria condition);

    /**
     * 查询单个用户
     *
     * @param name 姓名
     * @return com.simple.general.entity.User
     * @author Mr.Wu
     * @date 2020/4/18 21:46
     */
    User findByName(String name);
}
