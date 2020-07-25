package com.simple.general.service;

import com.simple.general.entity.ImageInfo;
import com.simple.general.query.ImageQuery;
import org.springframework.data.domain.Page;

/**
 * 人像
 *
 * @author Mr.Wu
 * @date 2020/5/9 00:01
 */
public interface ImageInfoService {

    /**
     * 添加人像
     *
     * @param imageInfo 添加内容
     * @author Mr.Wu
     * @date 2020/5/9 00:10
     */
    void saveImageInfo(ImageInfo imageInfo);

    /**
     * 修改人像
     *
     * @param imageInfo 修改内容
     * @author Mr.Wu
     * @date 2020/5/9 00:10
     */
    void updateImageInfo(ImageInfo imageInfo);

    /**
     * 单个/批量删除人像库
     *
     * @param imageQuery 条件
     * @author Mr.Wu
     * @date 2020/5/9 00:10
     */
    void deleteImageInfo(ImageQuery imageQuery);

    /**
     * 分页查询人像
     *
     * @param imageQuery 查询条件
     * @return org.springframework.data.domain.Page<com.simple.general.entity.ImageInfo>
     * @author Mr.Wu
     * @date 2020/5/9 00:10
     */
    Page<ImageInfo> pageListImageInfos(ImageQuery imageQuery);

}
