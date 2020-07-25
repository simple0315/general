package com.simple.general.controller;

import com.simple.general.entity.ImageInfo;
import com.simple.general.group.SaveGroup;
import com.simple.general.group.UpdateGroup;
import com.simple.general.query.ImageQuery;
import com.simple.general.response.ResponseResult;
import com.simple.general.service.ImageInfoService;
import com.simple.general.service.SystemLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 人像
 *
 * @author Mr.Wu
 * @date 2020/5/9 01:01
 */
@RestController
@RequestMapping("/image")
public class ImageInfoController {

    private final ImageInfoService imageInfoService;

    private final SystemLogService systemLogService;

    private static final String OPERATION = "人像模块";

    @Autowired
    public ImageInfoController(ImageInfoService imageInfoService, SystemLogService systemLogService) {
        this.imageInfoService = imageInfoService;
        this.systemLogService = systemLogService;
    }

    /**
     * 添加人像库
     *
     * @param imageInfo 用户
     * @return ResponseResult
     * @author Mr.Wu
     * @date 2020/5/9 01:05
     */
    @RequiresPermissions("image:save")
    @PostMapping("/manage")
    public ResponseResult saveImageInfo(@Validated(SaveGroup.class) @RequestBody ImageInfo imageInfo, HttpServletRequest request, HttpSession session) {
        imageInfoService.saveImageInfo(imageInfo);
        systemLogService.saveOperateLog(request, session, OPERATION, "上传人像");
        return ResponseResult.simpleOk();
    }

    /**
     * 修改人像
     *
     * @param imageInfo 内容
     * @return com.simple.general.response.ResponseResult
     * @author Mr.Wu
     * @date 2020/5/9 01:05
     */
    @RequiresPermissions("image:update")
    @PutMapping("/manage")
    public ResponseResult updateImageInfo(@Validated(UpdateGroup.class) @RequestBody ImageInfo imageInfo, HttpServletRequest request, HttpSession session) {
        imageInfoService.updateImageInfo(imageInfo);
        systemLogService.saveOperateLog(request, session, OPERATION, "修改人像");
        return ResponseResult.simpleOk();
    }

    /**
     * 删除人像
     *
     * @param imageQuery 条件
     * @return com.simple.general.response.ResponseResult
     * @author Mr.Wu
     * @date 2020/5/9 01:05
     */
    @RequiresPermissions("image:delete")
    @PutMapping("/manage/delete")
    public ResponseResult deleteImageInfo(@RequestBody ImageQuery imageQuery, HttpServletRequest request, HttpSession session) {
        imageInfoService.deleteImageInfo(imageQuery);
        systemLogService.saveOperateLog(request, session, OPERATION, "删除人像");
        return ResponseResult.simpleOk();
    }

    /**
     * 分页查询人像
     *
     * @param imageQuery 查询条件
     * @return com.simple.general.response.ResponseResult
     * @author Mr.Wu
     * @date 2020/5/9 01:05
     */
    @RequiresPermissions("image:query")
    @PostMapping("/manage/page")
    public ResponseResult pageListRepositories(@Validated(SaveGroup.class) @RequestBody ImageQuery imageQuery) {
        Page<ImageInfo> imageInfoPage = imageInfoService.pageListImageInfos(imageQuery);
        return ResponseResult.ok(imageInfoPage.getContent(), imageInfoPage.getTotalElements());
    }

}
