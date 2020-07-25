package com.simple.general.dao.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.simple.general.dao.UserDao;
import com.simple.general.entity.User;
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

@Repository("userDao")
public class UserDaoImpl implements UserDao {

    MongoCollection<User> userCollection = MongoDBUtils.INSTANCE.getCollection(SystemConstantUtils.SCC_USER, User.class);

    @Override
    public void save(User user) {
        MongoDBUtils.INSTANCE.save(userCollection, user);
    }

    @Override
    public void update(User user) {
        Integer id = user.getId();
        Bson bson = Filters.eq("_id", id);
        user.setId(null);
        MongoDBUtils.INSTANCE.updateOneByCondition(userCollection, bson, MongoConvertUtils.objectConvertDocument(user));
    }

    @Override
    public void deleteByCondition(Criteria condition) {
        MongoDBUtils.INSTANCE.deleteByCondition(userCollection,MongoConvertUtils.criteriaConvertDocument(condition));
    }

    @Override
    public User findById(Integer id) {
        Bson bson = Filters.eq("_id", id);
        return MongoDBUtils.INSTANCE.findOneByCondition(userCollection, bson, User.class);
    }

    @Override
    public Page<User> pageList(Criteria condition, int pageNo, int pageSize) {
        List<User> userList = MongoDBUtils.INSTANCE.pageByCondition(userCollection, MongoConvertUtils.criteriaConvertDocument(condition), Filters.eq(MongoConstUtils.CREATE_TIME,-1), pageNo, pageSize, User.class);
        long count = getCountByCondition(condition);
        return new PageImpl<>(userList, new PageRequest(pageNo - 1, pageSize), count);
    }

    @Override
    public int getId() {
        return MongoDBUtils.INSTANCE.getSequence(MongoConstUtils.USER_ID);
    }

    @Override
    public long getCountByCondition(Criteria condition) {
        return MongoDBUtils.INSTANCE.getCountByCondition(userCollection, MongoConvertUtils.criteriaConvertDocument(condition));
    }

    @Override
    public User findByName(String name) {
        return MongoDBUtils.INSTANCE.findOneByCondition(userCollection, Filters.eq(MongoConstUtils.USERNAME,name), User.class);
    }
}
