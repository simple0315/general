package com.simple.general.service;

import com.simple.general.entity.Repository;
import com.simple.general.query.BaseQuery;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 人像库
 *
 * @author Mr.Wu
 * @date 2020/5/8 00:20
 */
public interface RepositoryService {

    /**
     * 添加人像库
     *
     * @param repository 添加内容
     * @author Mr.Wu
     * @date 2020/5/8 00:21
     */
    void saveRepository(Repository repository);

    /**
     * 修改人像库
     *
     * @param repository 修改内容
     * @author Mr.Wu
     * @date 2020/5/8 00:22
     */
    void updateRepository(Repository repository);

    /**
     * 单个/批量删除人像库
     *
     * @param ids id数组
     * @author Mr.Wu
     * @date 2020/5/8 00:22
     */
    void deleteRepository(List<Integer> ids);

    /**
     * 分页查询人像库
     *
     * @param repositoryQuery 查询条件
     * @return org.springframework.data.domain.Page<com.simple.general.entity.Repository>
     * @author Mr.Wu
     * @date 2020/5/8 00:22
     */
    Page<Repository> pageListRepositories(BaseQuery repositoryQuery);

}
