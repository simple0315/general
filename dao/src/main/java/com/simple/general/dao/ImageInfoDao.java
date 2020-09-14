package com.simple.general.dao;

import com.simple.general.entity.ImageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Criteria;

/**
 * 人像
 *
 * @author Mr.Wu
 * @date 2020/5/8 23:21
 */
public interface ImageInfoDao {

    /**
     * 添加人像
     *
     * @param imageInfo 内容
     * @author Mr.Wu
     * @date 2020/5/8 23:34
     */
    void save(ImageInfo imageInfo);

    /**
     * 修改人像
     *
     * @param imageInfo 内容
     * @author Mr.Wu
     * @date 2020/5/7 23:35
     */
    void update(ImageInfo imageInfo);

    /**
     * 删除人像
     *
     * @param condition 条件
     * @author Mr.Wu
     * @date 2020/5/8 23:36
     */
    long deleteByCondition(Criteria condition);

    /**
     * 分页查询人像
     *
     * @param condition 条件
     * @param pageNo    页码
     * @param pageSize  查询数量
     * @return org.springframework.data.domain.Page<com.simple.general.entity.ImageInfo>
     * @author Mr.Wu
     * @date 2020/5/8 23:36
     */
    Page<ImageInfo> pageList(Criteria condition, int pageNo, int pageSize);

    /**
     * 获取id
     *
     * @author Mr.Wu
     * @date 2020/5/8 23:37
     */
    int getId();

    /**
     * 查询数量
     *
     * @param condition 条件
     * @return long
     * @author Mr.Wu
     * @date 2020/5/8 23:37
     */
    long getCountByCondition(Criteria condition);

}
