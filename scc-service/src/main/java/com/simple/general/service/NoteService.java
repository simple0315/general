package com.simple.general.service;

import com.simple.general.entity.Note;
import com.simple.general.query.BaseQuery;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 笔记模块
 *
 * @author Mr.Wu
 * @date 2020/5/17 18:49
 */
public interface NoteService {

    /**
     * 添加笔记
     *
     * @param note 添加内容
     * @author Mr.Wu
     * @date 2020/5/17 18:49
     */
    void saveNote(Note note);

    /**
     * 修改笔记
     *
     * @param note 修改内容
     * @author Mr.Wu
     * @date 2020/5/17 18:49
     */
    void updateNote(Note note);

    /**
     * 单个/批量删除笔记
     *
     * @param ids id数组
     * @author Mr.Wu
     * @date 2020/5/17 18:49
     */
    void deleteNote(List<Integer> ids);

    /**
     * 分页查询笔记
     *
     * @param noteQuery 查询条件
     * @return org.springframework.data.domain.Page<com.simple.general.entity.Note>
     * @author Mr.Wu
     * @date 2020/5/17 18:49
     */
    Page<Note> pageListNotes(BaseQuery noteQuery);

}
