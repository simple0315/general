package com.simple.general.service;

import com.simple.general.entity.Camera;
import com.simple.general.query.BaseQuery;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 摄像头模块
 *
 * @author Mr.Wu
 * @date 2020/5/16 20:59
 */
public interface CameraService {

    /**
     * 添加摄像头
     *
     * @param camera 添加内容
     * @author Mr.Wu
     * @date 2020/5/16 20:59
     */
    void saveCamera(Camera camera);

    /**
     * 修改摄像头
     *
     * @param camera 修改内容
     * @author Mr.Wu
     * @date 2020/5/16 20:59
     */
    void updateCamera(Camera camera);

    /**
     * 单个/批量删除摄像头
     *
     * @param ids id数组
     * @author Mr.Wu
     * @date 2020/5/16 20:59
     */
    void deleteCamera(List<Integer> ids);

    /**
     * 分页查询摄像头
     *
     * @param cameraQuery 查询条件
     * @return org.springframework.data.domain.Page<com.simple.general.entity.Camera>
     * @author Mr.Wu
     * @date 2020/5/16 20:59
     */
    Page<Camera> pageListCameras(BaseQuery cameraQuery);

}
