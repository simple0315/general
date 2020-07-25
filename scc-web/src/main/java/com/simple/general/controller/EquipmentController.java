package com.simple.general.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.simple.general.entity.Equipment;
import com.simple.general.exception.ParameterException;
import com.simple.general.group.SaveGroup;
import com.simple.general.group.UpdateGroup;
import com.simple.general.query.BaseQuery;
import com.simple.general.response.ResponseResult;
import com.simple.general.service.EquipmentService;
import com.simple.general.service.SystemLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 设备
 *
 * @author Mr.Wu
 * @date 2020/5/16 21:50
 */
@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;

    private final SystemLogService systemLogService;

    private static final String OPERATION = "设备模块";

    @Autowired
    public EquipmentController(EquipmentService equipmentService, SystemLogService systemLogService) {
        this.equipmentService = equipmentService;
        this.systemLogService = systemLogService;
    }

    /**
     * 添加设备
     *
     * @param equipment 用户
     * @return ResponseResult
     * @author Mr.Wu
     * @date 2020/5/16 21:50
     */
    @RequiresPermissions("equipment:save")
    @PostMapping("/manage")
    public ResponseResult saveEquipment(@Validated(SaveGroup.class) @RequestBody Equipment equipment, HttpServletRequest request, HttpSession session) {
        equipmentService.saveEquipment(equipment);
        systemLogService.saveOperateLog(request, session, OPERATION, "添加设备");

        return ResponseResult.simpleOk();
    }

    /**
     * 修改设备
     *
     * @param equipment 内容
     * @return com.simple.general.response.ResponseResult
     * @author Mr.Wu
     * @date 2020/5/16 21:50
     */
    @RequiresPermissions("equipment:update")
    @PutMapping("/manage")
    public ResponseResult updateEquipment(@Validated(UpdateGroup.class) @RequestBody Equipment equipment, HttpServletRequest request, HttpSession session) {
        equipmentService.updateEquipment(equipment);
        systemLogService.saveOperateLog(request, session, OPERATION, "修改设备");

        return ResponseResult.simpleOk();
    }

    /**
     * 删除设备
     *
     * @param equipmentObject 用户id数组
     * @return com.simple.general.response.ResponseResult
     * @author Mr.Wu
     * @date 2020/5/16 21:50
     */
    @RequiresPermissions("equipment:delete")
    @PutMapping("/manage/delete")
    public ResponseResult deleteEquipment(@RequestBody JSONObject equipmentObject, HttpServletRequest request, HttpSession session) {
        JSONArray ids = equipmentObject.getJSONArray("id");
        if (ids == null || ids.isEmpty()) {
            throw new ParameterException("id不能为空");
        }
        equipmentService.deleteEquipment(JSONArray.parseArray(ids.toJSONString(), Integer.class));
        systemLogService.saveOperateLog(request, session, OPERATION, "删除设备");

        return ResponseResult.simpleOk();
    }

    /**
     * 分页查询设备
     *
     * @param equipmentQuery 查询条件
     * @return com.simple.general.response.ResponseResult
     * @author Mr.Wu
     * @date 2020/5/16 21:50
     */
    @RequiresPermissions("equipment:query")
    @PostMapping("/manage/page")
    public ResponseResult pageListEquipments(@Validated @RequestBody BaseQuery equipmentQuery) {
        Page<Equipment> equipmentPage = equipmentService.pageListEquipments(equipmentQuery);
        return ResponseResult.ok(equipmentPage.getContent(), equipmentPage.getTotalElements());
    }

}
