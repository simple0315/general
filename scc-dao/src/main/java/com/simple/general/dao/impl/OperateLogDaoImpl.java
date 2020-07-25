package com.simple.general.dao.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.simple.general.dao.OperateLogDao;
import com.simple.general.entity.OperateLog;
import com.simple.general.mongo.MongoConvertUtils;
import com.simple.general.mongo.MongoDBUtils;
import com.simple.general.utils.MongoConstUtils;
import com.simple.general.utils.SystemConstantUtils;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("operateLogDao")
public class OperateLogDaoImpl implements OperateLogDao {

    private MongoCollection<OperateLog> operateLogCollection = MongoDBUtils.INSTANCE.getCollection(SystemConstantUtils.SCC_OPERATE_LOG,OperateLog.class);

    @Override
    public void save(OperateLog operateLog) {
        MongoDBUtils.INSTANCE.save(operateLogCollection,operateLog);
    }

    @Override
    public Page<OperateLog> pageList(Criteria condition, int pageNo, int pageSize) {
        Document document = MongoConvertUtils.criteriaConvertDocument(condition);
        List<OperateLog> operateLogList = MongoDBUtils.INSTANCE.pageByCondition(operateLogCollection, document, Filters.eq("timestamp", -1), pageNo, pageSize, OperateLog.class);
        long count = getCountByCondition(condition);
        return new PageImpl<>(operateLogList,new PageRequest(pageNo-1,pageSize),count);
    }

    @Override
    public int getId() {
        return MongoDBUtils.INSTANCE.getSequence(MongoConstUtils.OPERATE_LOG_ID);
    }

    @Override
    public long getCountByCondition(Criteria condition) {
        return MongoDBUtils.INSTANCE.getCountByCondition(operateLogCollection,MongoConvertUtils.criteriaConvertDocument(condition));
    }
}
