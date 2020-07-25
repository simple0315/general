package com.simple.general.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.simple.general.entity.Repository;
import com.simple.general.exception.ParameterException;
import com.simple.general.group.SaveGroup;
import com.simple.general.group.UpdateGroup;
import com.simple.general.query.BaseQuery;
import com.simple.general.response.ResponseResult;
import com.simple.general.service.RepositoryService;
import com.simple.general.service.SystemLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 人像库
 *
 * @author Mr.Wu
 * @date 2020/5/8 00:35
 */
@RestController
@RequestMapping("/repository")
public class RepositoryController {

    private final RepositoryService repositoryService;

    private final SystemLogService systemLogService;

    private static final String OPERATION = "人像库模块";

    @Autowired
    public RepositoryController(RepositoryService repositoryService, SystemLogService systemLogService) {
        this.repositoryService = repositoryService;
        this.systemLogService = systemLogService;
    }

    /**
     * 添加人像库
     *
     * @param repository 添加内容
     * @return ResponseResult
     * @author Mr.Wu
     * @date 2020/5/8 00:36
     */
    @RequiresPermissions("repository:save")
    @PostMapping("/manage")
    public ResponseResult saveRepository(@Validated(SaveGroup.class) @RequestBody Repository repository, HttpServletRequest request, HttpSession session) {
        repositoryService.saveRepository(repository);
        systemLogService.saveOperateLog(request, session, OPERATION, "添加人像库");
        return ResponseResult.simpleOk();
    }

    /**
     * 修改人像库
     *
     * @param repository 内容
     * @return com.simple.general.response.ResponseResult
     * @author Mr.Wu
     * @date 2020/5/8 00:36
     */
    @RequiresPermissions("repository:update")
    @PutMapping("/manage")
    public ResponseResult updateRepository(@Validated(UpdateGroup.class) @RequestBody Repository repository, HttpServletRequest request, HttpSession session) {
        repositoryService.updateRepository(repository);
        systemLogService.saveOperateLog(request, session, OPERATION, "修改人像库");
        return ResponseResult.simpleOk();
    }

    /**
     * 删除人像库
     *
     * @param repositoryObject 人像库id数组
     * @return com.simple.general.response.ResponseResult
     * @author Mr.Wu
     * @date 2020/5/8 00:36
     */
    @RequiresPermissions("repository:delete")
    @PutMapping("/manage/delete")
    public ResponseResult deleteRepository(@RequestBody JSONObject repositoryObject, HttpServletRequest request, HttpSession session) {
        JSONArray ids = repositoryObject.getJSONArray("id");
        if (ids == null || ids.isEmpty()) {
            throw new ParameterException("id不能为空");
        }
        repositoryService.deleteRepository(JSONArray.parseArray(ids.toJSONString(), Integer.class));
        systemLogService.saveOperateLog(request, session, OPERATION, "修改人像库");
        return ResponseResult.simpleOk();
    }

    /**
     * 分页查询人像库
     *
     * @param repositoryQuery 查询条件
     * @return com.simple.general.response.ResponseResult
     * @author Mr.Wu
     * @date 2020/5/8 00:37
     */
    @RequiresPermissions("repository:query")
    @PostMapping("/manage/page")
    public ResponseResult pageListRepositories(@Validated @RequestBody BaseQuery repositoryQuery) {
        Page<Repository> repositoryPage = repositoryService.pageListRepositories(repositoryQuery);
        return ResponseResult.ok(repositoryPage.getContent(), repositoryPage.getTotalElements());
    }

}
