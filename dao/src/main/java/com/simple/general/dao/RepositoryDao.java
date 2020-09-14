package com.simple.general.dao;

import com.simple.general.entity.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Criteria;

/**
 * 人像库
 *
 * @author Mr.Wu
 * @date 2020/5/7 23:57
 */
public interface RepositoryDao {

    /**
     * 添加人像库
     *
     * @param repository 内容
     * @author Mr.Wu
     * @date 2020/5/7 23:58
     */
    void save(Repository repository);

    /**
     * 修改人像库
     *
     * @param repository 内容
     * @author Mr.Wu
     * @date 2020/5/7 23:59
     */
    void update(Repository repository);

    /**
     * 删除人像库
     *
     * @param condition 条件
     * @author Mr.Wu
     * @date 2020/5/8 00:00
     */
    void deleteByCondition(Criteria condition);

    /**
     * 查询单个人像库
     *
     * @param id 人像库id
     * @return com.simple.general.entity.Repository
     * @author Mr.Wu
     * @date 2020/5/8 00:01
     */
    Repository findById(Integer id);

    /**
     * 分页查询人像库
     *
     * @param condition 条件
     * @param pageNo    页码
     * @param pageSize  查询数量
     * @return org.springframework.data.domain.Page<com.simple.general.entity.Repository>
     * @author Mr.Wu
     * @date 2020/5/8 00:02
     */
    Page<Repository> pageList(Criteria condition, int pageNo, int pageSize);

    /**
     * 获取id
     *
     * @author Mr.Wu
     * @date 2020/5/8 00:02
     */
    int getId();

    /**
     * 查询数量
     *
     * @param condition 条件
     * @return long
     * @author Mr.Wu
     * @date 2020/5/8 00:03
     */
    long getCountByCondition(Criteria condition);

    /**
     * 查询单个人像库
     *
     * @param name 姓名
     * @return com.simple.general.entity.Repository
     * @author Mr.Wu
     * @date 2020/5/8 00:03
     */
    Repository findByName(String name);

    /**
     * 修改人像库图片数量
     *
     * @param repositoryId 人像库id
     * @param count            数量
     * @author Mr.Wu
     * @date 2020/5/8 00:15
     */
    void incrementImageNumber(int repositoryId, int count);

}
