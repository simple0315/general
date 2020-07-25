package com.simple.general.dao;

import com.simple.general.entity.Camera;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Criteria;

/**
 * 摄像头
 *
 * @author Mr.Wu
 * @date 2020/5/16 20:44
 */
public interface CameraDao {
    /**
     * 添加摄像头
     *
     * @param camera 内容
     * @author Mr.Wu
     * @date 2020/5/16 20:44
     */
    void save(Camera camera);

    /**
     * 修改摄像头
     *
     * @param camera 信息
     * @author Mr.Wu
     * @date 2020/5/16 20:44
     */
    void update(Camera camera);

    /**
     * 删除摄像头
     *
     * @param condition 条件
     * @author Mr.Wu
     * @date 2020/5/16 20:44
     */
    void deleteByCondition(Criteria condition);

    /**
     * 查询单个摄像头
     *
     * @param id 用户id
     * @return com.simple.general.entity.Camera
     * @author Mr.Wu
     * @date 2020/5/16 20:44
     */
    Camera findById(Integer id);

    /**
     * 分页查询摄像头
     *
     * @param condition 条件
     * @param pageNo    页码
     * @param pageSize  查询数量
     * @return org.springframework.data.domain.Page<com.simple.general.entity.Camera>
     * @author Mr.Wu
     * @date 2020/5/16 20:44
     */
    Page<Camera> pageList(Criteria condition, int pageNo, int pageSize);

    /**
     * 获取id
     *
     * @author Mr.Wu
     * @date 2020/5/16 20:44
     */
    int getId();

    /**
     * 查询数量
     *
     * @param condition 条件
     * @return long
     * @author Mr.Wu
     * @date 2020/5/16 20:44
     */
    long getCountByCondition(Criteria condition);

    /**
     * 查询单个摄像头
     *
     * @param name 姓名
     * @return com.simple.general.entity.Camera
     * @author Mr.Wu
     * @date 2020/5/16 20:44
     */
    Camera findByName(String name);
}
