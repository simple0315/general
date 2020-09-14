package com.simple.general.service.impl;

import com.simple.general.dao.NoteDao;
import com.simple.general.entity.Note;
import com.simple.general.exception.ParameterException;
import com.simple.general.query.BaseQuery;
import com.simple.general.service.NoteService;
import com.simple.general.utils.DateUtils;
import com.simple.general.utils.MongoConstUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("noteService")
public class NoteServiceImpl implements NoteService {

    private final NoteDao noteDao;

    @Autowired
    public NoteServiceImpl(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    public void saveNote(Note note) {
        checkName(note);
        int id = noteDao.getId();
        note.setId(id);
        note.setCreateTime(DateUtils.now());
        note.setUpdateTime(DateUtils.now());
        noteDao.save(note);
    }

    @Override
    public void updateNote(Note note) {
        Note checkNote = noteDao.findById(note.getId());
        if (!note.getName().equals(checkNote.getName())) {
            checkName(note);
        }
        note.setUpdateTime(DateUtils.now());
        noteDao.update(note);
    }

    @Override
    public void deleteNote(List<Integer> ids) {
        noteDao.deleteByCondition(Criteria.where("_id").in(ids));
    }

    @Override
    public Page<Note> pageListNotes(BaseQuery noteQuery) {
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(noteQuery.getName())) {
            criteria.and(MongoConstUtils.NAME).regex(noteQuery.getName());
        }
        return noteDao.pageList(criteria, noteQuery.getPageNo(), noteQuery.getPageSize());
    }

    private void checkName(Note note) {
        Criteria condition = Criteria.where(MongoConstUtils.NAME).is(note.getName());
        long count = noteDao.getCountByCondition(condition);
        if (count > 0) {
            throw new ParameterException("笔记名称已存在");
        }
    }
}
