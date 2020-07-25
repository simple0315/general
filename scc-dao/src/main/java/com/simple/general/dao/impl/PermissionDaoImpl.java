package com.simple.general.dao.impl;

import com.mongodb.client.MongoCollection;
import com.simple.general.dao.PermissionDao;
import com.simple.general.entity.Permission;
import com.simple.general.mongo.MongoConvertUtils;
import com.simple.general.mongo.MongoDBUtils;
import com.simple.general.utils.SystemConstantUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("permissionDao")
public class PermissionDaoImpl implements PermissionDao {
    MongoCollection<Permission> permissionCollection = MongoDBUtils.INSTANCE.getCollection(SystemConstantUtils.SCC_PERMISSION, Permission.class);

    @Override
    public List<Permission> findAll() {
        return MongoDBUtils.INSTANCE.findAll(permissionCollection, Permission.class);
    }

    @Override
    public void saveMany(List<Permission> permissionList) {
        MongoDBUtils.INSTANCE.saveMany(permissionCollection, permissionList);
    }

    @Override
    public List<Permission> findByCondition(Criteria condition) {
        return MongoDBUtils.INSTANCE.findByCondition(permissionCollection, MongoConvertUtils.criteriaConvertDocument(condition), Permission.class);
    }

    @Override
    public long getCountByCondition(Criteria condition) {
        return MongoDBUtils.INSTANCE.getCountByCondition(permissionCollection, MongoConvertUtils.criteriaConvertDocument(condition));
    }
}
