package com.simple.general.dao.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.simple.general.dao.NoteDao;
import com.simple.general.entity.Note;
import com.simple.general.mongo.MongoConvertUtils;
import com.simple.general.mongo.MongoDBUtils;
import com.simple.general.utils.MongoConstUtils;
import com.simple.general.utils.SystemConstantUtils;
import org.bson.conversions.Bson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("noteDao")
public class NoteDaoImpl implements NoteDao {

    private MongoCollection<Note> noteCollection = MongoDBUtils.INSTANCE.getCollection(SystemConstantUtils.SCC_NOTE,Note.class);

    @Override
    public void save(Note note) {
        MongoDBUtils.INSTANCE.save(noteCollection, note);
    }

    @Override
    public void update(Note note) {
        Integer id = note.getId();
        Bson bson = Filters.eq("_id", id);
        note.setId(null);
        MongoDBUtils.INSTANCE.updateOneByCondition(noteCollection, bson, MongoConvertUtils.objectConvertDocument(note));
    }

    @Override
    public void deleteByCondition(Criteria condition) {
        MongoDBUtils.INSTANCE.deleteByCondition(noteCollection,MongoConvertUtils.criteriaConvertDocument(condition));
    }

    @Override
    public Note findById(Integer id) {
        Bson bson = Filters.eq("_id", id);
        return MongoDBUtils.INSTANCE.findOneByCondition(noteCollection, bson, Note.class);
    }

    @Override
    public Page<Note> pageList(Criteria condition, int pageNo, int pageSize) {
        List<Note> noteList = MongoDBUtils.INSTANCE.pageByCondition(noteCollection, MongoConvertUtils.criteriaConvertDocument(condition), Filters.eq(MongoConstUtils.CREATE_TIME,-1), pageNo, pageSize, Note.class);
        long count = getCountByCondition(condition);
        return new PageImpl<>(noteList, new PageRequest(pageNo - 1, pageSize), count);
    }

    @Override
    public int getId() {
        return MongoDBUtils.INSTANCE.getSequence(MongoConstUtils.NOTE_ID);
    }

    @Override
    public long getCountByCondition(Criteria condition) {
        return MongoDBUtils.INSTANCE.getCountByCondition(noteCollection, MongoConvertUtils.criteriaConvertDocument(condition));
    }

    @Override
    public Note findByName(String name) {
        return MongoDBUtils.INSTANCE.findOneByCondition(noteCollection, Filters.eq(MongoConstUtils.NAME,name), Note.class);
    }
}
