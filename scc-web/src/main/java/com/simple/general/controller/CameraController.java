package com.simple.general.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.simple.general.entity.Camera;
import com.simple.general.exception.ParameterException;
import com.simple.general.group.SaveGroup;
import com.simple.general.group.UpdateGroup;
import com.simple.general.query.BaseQuery;
import com.simple.general.response.ResponseResult;
import com.simple.general.service.CameraService;
import com.simple.general.service.SystemLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 摄像头
 *
 * @author Mr.Wu
 * @date 2020/5/16 21:10
 */
@RestController
@RequestMapping("/camera")
public class CameraController {

    private final CameraService cameraService;

    private final SystemLogService systemLogService;

    private static final String OPERATION = "摄像头模块";

    @Autowired
    public CameraController(CameraService cameraService, SystemLogService systemLogService) {
        this.cameraService = cameraService;
        this.systemLogService = systemLogService;
    }

    /**
     * 添加摄像头
     *
     * @param camera 用户
     * @return ResponseResult
     * @author Mr.Li
     * @date 2020/5/16 21:10
     */
    @RequiresPermissions("camera:save")
    @PostMapping("/manage")
    public ResponseResult saveCamera(@Validated(SaveGroup.class) @RequestBody Camera camera, HttpServletRequest request, HttpSession session) {
        cameraService.saveCamera(camera);
        systemLogService.saveOperateLog(request, session, OPERATION, "添加摄像头");

        return ResponseResult.simpleOk();
    }

    /**
     * 修改摄像头
     *
     * @param camera 内容
     * @return com.simple.general.response.ResponseResult
     * @author Mr.Li
     * @date 2020/5/16 21:10
     */
    @RequiresPermissions("camera:update")
    @PutMapping("/manage")
    public ResponseResult updateCamera(@Validated(UpdateGroup.class) @RequestBody Camera camera, HttpServletRequest request, HttpSession session) {
        cameraService.updateCamera(camera);
        systemLogService.saveOperateLog(request, session, OPERATION, "修改摄像头");

        return ResponseResult.simpleOk();
    }

    /**
     * 删除摄像头
     *
     * @param cameraObject 用户id数组
     * @return com.simple.general.response.ResponseResult
     * @author Mr.Wu
     * @date 2020/5/16 21:10
     */
    @RequiresPermissions("camera:delete")
    @PutMapping("/manage/delete")
    public ResponseResult deleteCamera(@RequestBody JSONObject cameraObject, HttpServletRequest request, HttpSession session) {
        JSONArray ids = cameraObject.getJSONArray("id");
        if (ids == null || ids.isEmpty()) {
            throw new ParameterException("id不能为空");
        }
        cameraService.deleteCamera(JSONArray.parseArray(ids.toJSONString(), Integer.class));
        systemLogService.saveOperateLog(request, session, OPERATION, "删除摄像头");

        return ResponseResult.simpleOk();
    }

    /**
     * 分页查询摄像头
     *
     * @param cameraQuery 查询条件
     * @return com.simple.general.response.ResponseResult
     * @author Mr.Wu
     * @date 2020/5/16 21:10
     */
    @RequiresPermissions("camera:query")
    @PostMapping("/manage/page")
    public ResponseResult pageListCameras(@Validated @RequestBody BaseQuery cameraQuery) {
        Page<Camera> cameraPage = cameraService.pageListCameras(cameraQuery);
        return ResponseResult.ok(cameraPage.getContent(), cameraPage.getTotalElements());
    }

}
