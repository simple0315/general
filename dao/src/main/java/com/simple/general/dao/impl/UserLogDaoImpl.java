package com.simple.general.dao.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.simple.general.dao.UserLogDao;
import com.simple.general.entity.UserLog;
import com.simple.general.mongo.MongoConvertUtils;
import com.simple.general.mongo.MongoDBUtils;
import com.simple.general.utils.MongoConstUtils;
import com.simple.general.utils.SystemConstantUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userLogDao")
public class UserLogDaoImpl implements UserLogDao {

    private MongoCollection<UserLog> userLogCollection = MongoDBUtils.INSTANCE.getCollection(SystemConstantUtils.SCC_USER_LOG, UserLog.class);

    @Override
    public void save(UserLog userLog) {
        MongoDBUtils.INSTANCE.save(userLogCollection, userLog);
    }

    @Override
    public void update(UserLog userLog) {
        String sessionId = userLog.getSessionId();
        Bson bson = Filters.eq(MongoConstUtils.SESSION_ID, sessionId);
        userLog.setSessionId(null);
        MongoDBUtils.INSTANCE.updateOneByCondition(userLogCollection, bson, MongoConvertUtils.objectConvertDocument(userLog));
    }

    @Override
    public Page<UserLog> pageList(Criteria condition, int pageNo, int pageSize) {
        Document document = MongoConvertUtils.criteriaConvertDocument(condition);
        List<UserLog> userLogList = MongoDBUtils.INSTANCE.pageByCondition(userLogCollection, document, Filters.eq("login_time",-1), pageNo, pageSize, UserLog.class);
        long count = getCountByCondition(condition);
        return new PageImpl<>(userLogList, new PageRequest(pageNo - 1, pageSize), count);
    }

    @Override
    public int getId() {
        return MongoDBUtils.INSTANCE.getSequence(MongoConstUtils.USER_LOG_ID);
    }

    @Override
    public long getCountByCondition(Criteria condition) {
        return MongoDBUtils.INSTANCE.getCountByCondition(userLogCollection, MongoConvertUtils.criteriaConvertDocument(condition));
    }
}
