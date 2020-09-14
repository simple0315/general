package com.simple.general.dao;

import com.simple.general.entity.UserLog;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Criteria;

/**
 * 用户登录日志
 *
 * @author Mr.Wu
 * @date 2020/5/18 23:42
 */
public interface UserLogDao {

    /**
     * 添加用户登录日志
     *
     * @param userLog 日志内容
     * @author Mr.Wu
     * @date 2020/5/18 23:38
     */
    void save(UserLog userLog);

    /**
     * 修改用户登录日志
     *
     * @param userLog 日志内容
     * @author Mr.Wu
     * @date 2020/5/18 23:38
     */
    void update(UserLog userLog);

    /**
     * 分页查询用户登录日志
     *
     * @param condition 条件
     * @param pageNo    页码
     * @param pageSize  数量
     * @return org.springframework.data.domain.Page<com.simple.general.entity.UserLog>
     * @author Mr.Wu
     * @date 2020/5/18 23:40
     */
    Page<UserLog> pageList(Criteria condition, int pageNo, int pageSize);

    /**
     * id
     *
     * @return int
     * @author Mr.Wu
     * @date 2020/5/18 23:40
     */
    int getId();

    /**
     * 查询数量
     *
     * @param condition 条件
     * @return long
     * @author Mr.Wu
     * @date 2020/5/18 23:41
     */
    long getCountByCondition(Criteria condition);

}
