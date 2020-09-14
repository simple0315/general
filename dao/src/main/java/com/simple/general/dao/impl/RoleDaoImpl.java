package com.simple.general.dao.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.simple.general.dao.RoleDao;
import com.simple.general.entity.Role;
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

@Repository("roleDao")
public class RoleDaoImpl implements RoleDao {

    MongoCollection<Role> roleCollection = MongoDBUtils.INSTANCE.getCollection(SystemConstantUtils.SCC_ROLE, Role.class);

    @Override
    public void save(Role role) {
        MongoDBUtils.INSTANCE.save(roleCollection, role);
    }

    @Override
    public void update(Role role) {
        Bson idCondition = Filters.eq("_id", role.getId());
        role.setId(null);
        MongoDBUtils.INSTANCE.updateManyByCondition(roleCollection, idCondition, MongoConvertUtils.objectConvertDocument(role));
    }

    @Override
    public void delete(Criteria condition) {
        MongoDBUtils.INSTANCE.deleteByCondition(roleCollection, MongoConvertUtils.criteriaConvertDocument(condition));
    }

    @Override
    public Role findById(Integer id) {
        return MongoDBUtils.INSTANCE.findOneByCondition(roleCollection, Filters.eq("_id", id), Role.class);
    }

    @Override
    public Role findByName(String name) {
        return MongoDBUtils.INSTANCE.findOneByCondition(roleCollection, Filters.eq(MongoConstUtils.NAME, name), Role.class);
    }

    @Override
    public List<Role> findAll() {
        return MongoDBUtils.INSTANCE.findAll(roleCollection, Role.class);
    }

    @Override
    public int getId() {
        return MongoDBUtils.INSTANCE.getSequence(MongoConstUtils.ROLE_ID);
    }

    @Override
    public long getCountByCondition(Criteria condition) {
        return MongoDBUtils.INSTANCE.getCountByCondition(roleCollection, MongoConvertUtils.criteriaConvertDocument(condition));
    }

    @Override
    public Page<Role> pageList(Criteria condition, Integer pageNo, Integer pageSize) {
        List<Role> roleList = MongoDBUtils.INSTANCE.pageByCondition(roleCollection, MongoConvertUtils.criteriaConvertDocument(condition), Filters.eq(MongoConstUtils.CREATE_TIME,-1), pageNo, pageSize, Role.class);
        long count = getCountByCondition(condition);
        return new PageImpl<>(roleList, new PageRequest(pageNo - 1, pageSize), count);
    }
}
