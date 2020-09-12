package com.simple.general.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.simple.general.annotation.OperationLogDetail;
import com.simple.general.entity.Note;
import com.simple.general.exception.ParameterException;
import com.simple.general.group.SaveGroup;
import com.simple.general.group.UpdateGroup;
import com.simple.general.query.BaseQuery;
import com.simple.general.response.ResponseResult;
import com.simple.general.service.NoteService;
import com.simple.general.service.SystemLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 笔记
 *
 * @author Mr.Wu
 * @date 2020/5/17 19:12
 */
@RestController
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;


    private static final String OPERATION = "笔记模块";

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * 添加笔记
     *
     * @param note 用户
     * @return ResponseResult
     * @author Mr.Wu
     * @date 2020/5/17 19:12
     */
    @OperationLogDetail(operation = OPERATION, detail = "添加笔记")
    @RequiresPermissions("note:save")
    @PostMapping("/manage")
    public ResponseResult saveNote(@Validated(SaveGroup.class) @RequestBody Note note) {
        noteService.saveNote(note);
        return ResponseResult.simpleOk();
    }

    /**
     * 修改笔记
     *
     * @param note 内容
     * @return com.simple.general.response.ResponseResult
     * @author Mr.Wu
     * @date 2020/5/17 19:12
     */
    @OperationLogDetail(operation = OPERATION, detail = "修改笔记")
    @RequiresPermissions("note:update")
    @PutMapping("/manage")
    public ResponseResult updateNote(@Validated(UpdateGroup.class) @RequestBody Note note) {
        noteService.updateNote(note);
        return ResponseResult.simpleOk();
    }

    /**
     * 删除笔记
     *
     * @param noteObject 用户id数组
     * @return com.simple.general.response.ResponseResult
     * @author Mr.Wu
     * @date 2020/5/17 19:12
     */
    @OperationLogDetail(operation = OPERATION, detail = "删除笔记")
    @RequiresPermissions("note:delete")
    @PutMapping("/manage/delete")
    public ResponseResult deleteNote(@RequestBody JSONObject noteObject) {
        JSONArray ids = noteObject.getJSONArray("id");
        if (ids == null || ids.isEmpty()) {
            throw new ParameterException("id不能为空");
        }
        noteService.deleteNote(JSONArray.parseArray(ids.toJSONString(), Integer.class));
        return ResponseResult.simpleOk();
    }

    /**
     * 分页查询笔记
     *
     * @param noteQuery 查询条件
     * @return com.simple.general.response.ResponseResult
     * @author Mr.Wu
     * @date 2020/5/17 19:12
     */
    @RequiresPermissions("note:query")
    @PostMapping("/manage/page")
    public ResponseResult pageListNotes(@Validated @RequestBody BaseQuery noteQuery) {
        Page<Note> notePage = noteService.pageListNotes(noteQuery);
        return ResponseResult.ok(notePage.getContent(), notePage.getTotalElements());
    }

}
