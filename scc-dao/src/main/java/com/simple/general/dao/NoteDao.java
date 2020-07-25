package com.simple.general.dao;

import com.simple.general.entity.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Criteria;

/**
 * 笔记
 *
 * @author Mr.Wu
 * @date 2020/5/17 18:39
 */
public interface NoteDao {
    /**
     * 添加笔记
     *
     * @param note 内容
     * @author Mr.Wu
     * @date 2020/5/17 18:39
     */
    void save(Note note);

    /**
     * 修改笔记
     *
     * @param note 信息
     * @author Mr.Wu
     * @date 2020/5/17 18:39
     */
    void update(Note note);

    /**
     * 删除笔记
     *
     * @param condition 条件
     * @author Mr.Wu
     * @date 2020/5/17 18:39
     */
    void deleteByCondition(Criteria condition);

    /**
     * 查询单个笔记
     *
     * @param id 用户id
     * @return com.simple.general.entity.Note
     * @author Mr.Wu
     * @date 2020/5/17 18:39
     */
    Note findById(Integer id);

    /**
     * 分页查询笔记
     *
     * @param condition 条件
     * @param pageNo    页码
     * @param pageSize  查询数量
     * @return org.springframework.data.domain.Page<com.simple.general.entity.Note>
     * @author Mr.Wu
     * @date 2020/5/17 18:39
     */
    Page<Note> pageList(Criteria condition, int pageNo, int pageSize);

    /**
     * 获取id
     *
     * @author Mr.Wu
     * @date 2020/5/17 18:39
     */
    int getId();

    /**
     * 查询数量
     *
     * @param condition 条件
     * @return long
     * @author Mr.Wu
     * @date 2020/5/17 18:39
     */
    long getCountByCondition(Criteria condition);

    /**
     * 查询单个笔记
     *
     * @param name 姓名
     * @return com.simple.general.entity.Note
     * @author Mr.Wu
     * @date 2020/5/17 18:39
     */
    Note findByName(String name);
}
