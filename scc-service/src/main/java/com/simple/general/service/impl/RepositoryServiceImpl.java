package com.simple.general.service.impl;

import com.simple.general.dao.ImageInfoDao;
import com.simple.general.dao.RepositoryDao;
import com.simple.general.entity.Repository;
import com.simple.general.exception.ParameterException;
import com.simple.general.query.BaseQuery;
import com.simple.general.service.RepositoryService;
import com.simple.general.utils.DateUtils;
import com.simple.general.utils.MongoConstUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("repositoryService")
public class RepositoryServiceImpl implements RepositoryService {

    private final RepositoryDao repositoryDao;

    private final ImageInfoDao imageInfoDao;

    @Autowired
    public RepositoryServiceImpl(RepositoryDao repositoryDao, ImageInfoDao imageInfoDao) {
        this.repositoryDao = repositoryDao;
        this.imageInfoDao = imageInfoDao;
    }

    @Override
    public void saveRepository(Repository repository) {
        checkName(repository);
        repository.setId(repositoryDao.getId());
        repository.setImageCount(0);
        int now = DateUtils.now();
        repository.setCreateTime(now);
        repository.setUpdateTime(now);
        repositoryDao.save(repository);
    }

    @Override
    public void updateRepository(Repository repository) {
        Repository checkRepository = repositoryDao.findById(repository.getId());
        if (!repository.getName().equals(checkRepository.getName())) {
            checkName(repository);
        }
        repository.setUpdateTime(DateUtils.now());
        repositoryDao.update(repository);
    }

    @Override
    public void deleteRepository(List<Integer> ids) {
        repositoryDao.deleteByCondition(Criteria.where("_id").in(ids));
        imageInfoDao.deleteByCondition(Criteria.where(MongoConstUtils.REPOSITORY_ID).in(ids));
    }

    @Override
    public Page<Repository> pageListRepositories(BaseQuery repositoryQuery) {
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(repositoryQuery.getName())) {
            criteria.and(MongoConstUtils.NAME).regex(repositoryQuery.getName());
        }
        return repositoryDao.pageList(criteria, repositoryQuery.getPageNo(), repositoryQuery.getPageSize());
    }

    private void checkName(Repository repository) {
        Criteria condition = Criteria.where(MongoConstUtils.NAME).is(repository.getName());
        long count = repositoryDao.getCountByCondition(condition);
        if (count > 0) {
            throw new ParameterException("人像库名称已存在");
        }
    }
}
