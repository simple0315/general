package com.simple.general.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.simple.general.dao.PermissionDao;
import com.simple.general.dao.RoleDao;
import com.simple.general.dao.UserDao;
import com.simple.general.entity.Permission;
import com.simple.general.entity.Role;
import com.simple.general.entity.User;
import com.simple.general.service.DataInitService;
import com.simple.general.utils.DateUtils;
import com.simple.general.utils.MD5Utils;
import com.simple.general.utils.MongoConstUtils;
import lombok.Cleanup;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service("dataInitService")
@Log4j
public class DataInitServiceImpl implements DataInitService {

    private final PermissionDao permissionDao;

    private final RoleDao roleDao;

    private final UserDao userDao;

    @Autowired
    public DataInitServiceImpl(PermissionDao permissionDao, RoleDao roleDao, UserDao userDao) {
        this.permissionDao = permissionDao;
        this.roleDao = roleDao;
        this.userDao = userDao;
    }

    @Override
    public void initPermission() {

        long count = permissionDao.getCountByCondition(new Criteria());
        if (count > 0) {
            return;
        }

        try {
            ClassPathResource classPathResource = new ClassPathResource("static/permission.json");
            @Cleanup InputStreamReader inputStreamReader = new InputStreamReader(classPathResource.getInputStream(), StandardCharsets.UTF_8);
            @Cleanup BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder permissionInfo = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                permissionInfo.append(line);
            }
            String permissionStr = permissionInfo.toString().replace("\r\n", "").replaceAll(" +", "");
            JSONArray permissionArray = JSONArray.parseArray(permissionStr);
            List<Permission> permissionList = new ArrayList<>();
            for (Object object : permissionArray) {
                JSONObject permissionObject = JSONObject.parseObject(object.toString());
                Permission permission = new Permission();
                permission.setId(permissionObject.getInteger(MongoConstUtils.ID));
                permission.setPId(permissionObject.getInteger(MongoConstUtils.PID));
                permission.setUri(permissionObject.getString(MongoConstUtils.URI));
                permission.setMethod(permissionObject.getString(MongoConstUtils.METHOD));
                permission.setPermissionId(permissionObject.getString(MongoConstUtils.PERMISSION_ID));
                permission.setDescription(permissionObject.getString(MongoConstUtils.DESCRIPTION));
                permissionList.add(permission);
            }
            if (permissionList.size() > 0) {
                permissionList.sort(Comparator.comparingInt(Permission::getPId));
                permissionDao.saveMany(permissionList);
                log.info("初始化权限列表");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void initRole() {
        long count = roleDao.getCountByCondition(new Criteria());
        if (count > 0) {
            return;
        }
        try {
            Role role = new Role();
            role.setId(1);
            role.setName("超级管理员");
            List<Integer> permissionIds = new ArrayList<>();
            List<Permission> permissionList = permissionDao.findAll();
            permissionList.forEach(permission -> permissionIds.add(permission.getId()));
            role.setPermissionList(permissionIds);
            int now = DateUtils.now();
            role.setCreateTime(now);
            role.setUpdateTime(now);
            roleDao.save(role);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("初始化角色");

    }

    @Override
    public void initUser() {
        long count = userDao.getCountByCondition(new Criteria());
        if (count > 0) {
            return;
        }
        try {
            User user = new User();
            user.setId(1);
            user.setUsername("admin");
            user.setPassword(MD5Utils.MD5("admin"));
            user.setRoleId(1);
            int now = DateUtils.now();
            user.setCreateTime(now);
            user.setUpdateTime(now);
            userDao.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("初始化用户");
    }
}
