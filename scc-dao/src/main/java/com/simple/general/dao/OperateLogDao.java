package com.simple.general.dao;

import com.simple.general.entity.OperateLog;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Criteria;

/**
 * 用户操作日志
 *
 * @author Mr.Wu
 * @date 2020/5/18 23:56
 */
public interface OperateLogDao {

    /**
     * 添加用户登录日志
     *
     * @param operateLog 日志内容
     * @author Mr.Wu
     * @date 2020/5/18 23:56
     */
    void save(OperateLog operateLog);

    /**
     * 分页查询用户登录日志
     *
     * @param condition 条件
     * @param pageNo    页码
     * @param pageSize  数量
     * @return org.springframework.data.domain.Page<com.simple.general.entity.OperateLog>
     * @author Mr.Wu
     * @date 2020/5/18 23:56
     */
    Page<OperateLog> pageList(Criteria condition, int pageNo, int pageSize);

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
     * @date 2020/5/18 23:56
     */
    long getCountByCondition(Criteria condition);

}
