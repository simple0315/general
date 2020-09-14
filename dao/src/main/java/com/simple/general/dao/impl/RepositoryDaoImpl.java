package com.simple.general.dao.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.simple.general.dao.RepositoryDao;
import com.simple.general.entity.Repository;
import com.simple.general.mongo.MongoConvertUtils;
import com.simple.general.mongo.MongoDBUtils;
import com.simple.general.utils.MongoConstUtils;
import com.simple.general.utils.SystemConstantUtils;
import org.bson.conversions.Bson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("repositoryDao")
public class RepositoryDaoImpl implements RepositoryDao {

    MongoCollection<Repository> repositoryCollection = MongoDBUtils.INSTANCE.getCollection(SystemConstantUtils.SCC_REPOSITORY, Repository.class);

    @Override
    public void save(Repository repository) {
        MongoDBUtils.INSTANCE.save(repositoryCollection, repository);
    }

    @Override
    public void update(Repository repository) {
        Integer id = repository.getId();
        Bson bson = Filters.eq("_id", id);
        repository.setId(null);
        MongoDBUtils.INSTANCE.updateOneByCondition(repositoryCollection, bson, MongoConvertUtils.objectConvertDocument(repository));
    }

    @Override
    public void deleteByCondition(Criteria condition) {
        MongoDBUtils.INSTANCE.deleteByCondition(repositoryCollection, MongoConvertUtils.criteriaConvertDocument(condition));
    }

    @Override
    public Repository findById(Integer id) {
        Bson bson = Filters.eq("_id", id);
        return MongoDBUtils.INSTANCE.findOneByCondition(repositoryCollection, bson, Repository.class);
    }

    @Override
    public Page<Repository> pageList(Criteria condition, int pageNo, int pageSize) {
        List<Repository> repositoryList = MongoDBUtils.INSTANCE.pageByCondition(repositoryCollection, MongoConvertUtils.criteriaConvertDocument(condition), Filters.eq(MongoConstUtils.CREATE_TIME,-1), pageNo, pageSize, Repository.class);
        long count = getCountByCondition(condition);
        return new PageImpl<>(repositoryList, new PageRequest(pageNo - 1, pageSize), count);
    }

    @Override
    public int getId() {
        return MongoDBUtils.INSTANCE.getSequence(MongoConstUtils.REPOSITORY_ID);
    }

    @Override
    public long getCountByCondition(Criteria condition) {
        return MongoDBUtils.INSTANCE.getCountByCondition(repositoryCollection, MongoConvertUtils.criteriaConvertDocument(condition));
    }

    @Override
    public Repository findByName(String name) {
        return MongoDBUtils.INSTANCE.findOneByCondition(repositoryCollection, Filters.eq(MongoConstUtils.NAME, name), Repository.class);
    }

    @Override
    public void incrementImageNumber(int repositoryId, int count) {
        Bson condition = Filters.eq("_id", repositoryId);
        MongoDBUtils.INSTANCE.increase(repositoryCollection, condition, MongoConstUtils.IMAGE_COUNT, count);
    }
}
