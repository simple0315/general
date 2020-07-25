package com.simple.general.service.impl;

import com.simple.general.dao.RoleDao;
import com.simple.general.entity.Role;
import com.simple.general.exception.ParameterException;
import com.simple.general.query.BaseQuery;
import com.simple.general.service.RoleService;
import com.simple.general.utils.DateUtils;
import com.simple.general.utils.MongoConstUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public void addRole(Role role) {
        checkName(role);
        role.setId(roleDao.getId());
        role.setCreateTime(DateUtils.now());
        role.setUpdateTime(DateUtils.now());
        roleDao.save(role);
    }

    @Override
    public void updateRole(Role role) {
        String name = role.getName();
        Role checkRole = roleDao.findByName(name);
        if (!name.equals(checkRole.getName())) {
            checkName(role);
        }
        role.setUpdateTime(DateUtils.now());
        roleDao.update(role);
    }

    @Override
    public void deleteRole(List<Integer> ids) {
        roleDao.delete(Criteria.where("_id").in(ids));
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Page<Role> pageListRoles(BaseQuery roleQuery) {
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(roleQuery.getName())) {
            criteria.and(MongoConstUtils.NAME).regex(roleQuery.getName());
        }
        return roleDao.pageList(criteria, roleQuery.getPageNo(), roleQuery.getPageSize());
    }

    @Override
    public Role findRoleById(Integer roleId) {
        return roleDao.findById(roleId);
    }

    private void checkName(Role role) {
        Criteria condition = Criteria.where(MongoConstUtils.NAME).is(role.getName());
        long count = roleDao.getCountByCondition(condition);
        if (count > 0) {
            throw new ParameterException("角色名已存在");
        }
    }
}
