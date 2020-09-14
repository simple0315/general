package com.simple.general.service.impl;

import com.simple.general.dao.PermissionDao;
import com.simple.general.entity.Permission;
import com.simple.general.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

    private final PermissionDao permissionDao;

    @Autowired
    public PermissionServiceImpl(PermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }

    @Override
    public List<Permission> findAll() {
        return permissionDao.findAll();
    }

    @Override
    public List<Permission> findPermissionByIds(List<Integer> ids) {
        Criteria condition = Criteria.where("_id").in(ids);
        return permissionDao.findByCondition(condition);
    }

}
