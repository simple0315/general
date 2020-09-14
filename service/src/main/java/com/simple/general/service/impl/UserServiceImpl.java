package com.simple.general.service.impl;

import com.simple.general.dao.PermissionDao;
import com.simple.general.dao.RoleDao;
import com.simple.general.dao.UserDao;
import com.simple.general.entity.Permission;
import com.simple.general.entity.Role;
import com.simple.general.entity.User;
import com.simple.general.exception.ParameterException;
import com.simple.general.exception.UserLoginException;
import com.simple.general.query.BaseQuery;
import com.simple.general.service.UserService;
import com.simple.general.utils.DateUtils;
import com.simple.general.utils.MD5Utils;
import com.simple.general.utils.MongoConstUtils;
import com.simple.general.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户
 *
 * @author Mr.Wu
 * @date 2020/5/28 23:34
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final RoleDao roleDao;

    private final PermissionDao permissionDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, PermissionDao permissionDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.permissionDao = permissionDao;
    }


    @Override
    public void saveUser(User user) {
        checkName(user);
        int id = userDao.getId();
        user.setId(id);
        user.setPassword(MD5Utils.MD5(user.getPassword()));
        user.setCreateTime(DateUtils.now());
        user.setUpdateTime(DateUtils.now());
        userDao.save(user);
    }

    @Override
    public void updateUser(User user) {
        User checkUser = userDao.findById(user.getId());
        if (!user.getUsername().equals(checkUser.getUsername())) {
            checkName(user);
        }
        String password = user.getPassword();
        if(password!=null){
            user.setPassword(MD5Utils.MD5(password));
        }
        user.setUpdateTime(DateUtils.now());
        userDao.update(user);
    }

    @Override
    public void deleteUser(List<Integer> ids) {
        userDao.deleteByCondition(Criteria.where("_id").in(ids));
    }

    @Override
    public Page<User> pageListUsers(BaseQuery userQuery) {
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(userQuery.getName())) {
            criteria.and(MongoConstUtils.USERNAME).regex(userQuery.getName());
        }
        Page<User> userPage = userDao.pageList(criteria, userQuery.getPageNo(), userQuery.getPageSize());
        Map<Integer, String> roleMap = new HashMap<>(2);
        List<Role> roleList = roleDao.findAll();
        for (Role role : roleList) {
            roleMap.put(role.getId(), role.getName());
        }
        List<User> userList = userPage.getContent();
        for (User user : userList) {
            user.setRoleName(roleMap.get(user.getRoleId()));
            user.setPassword(null);
        }
        return userPage;
    }

    @Override
    public void login(User user) {
        String username = user.getUsername().trim();
        String password = MD5Utils.MD5(user.getPassword().trim());
        User checkUser = userDao.findByName(username);
        if (checkUser == null) {
            throw new UserLoginException();
        }
        if (password == null || !password.equals(checkUser.getPassword())) {
            throw new UserLoginException();
        }
    }

    @Override
    public UserVO getUserByName(String name) {
        User user = userDao.findByName(name);
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setUsername(user.getUsername());
        userVO.setPassword(user.getPassword() );
        Role role = roleDao.findById(user.getRoleId());
        if (role == null) {
            userVO.setRoleId(0);
            userVO.setRoleName("---");
            userVO.setMenuList(new ArrayList<>());
            userVO.setPermissionList(new ArrayList<>());
        } else {
            userVO.setRoleId(role.getId());
            userVO.setRoleName(role.getName());
            List<Permission> permissionList = permissionDao.findByCondition(Criteria.where("_id").in(role.getPermissionList()));
            List<String> menuList = new ArrayList<>();
            List<String> permissionStrList = new ArrayList<>();
            for (Permission permission : permissionList) {
                Integer pId = permission.getPId();
                String permissionId = permission.getPermissionId();
                if (pId == 0) {
                    menuList.add(permissionId);
                } else {
                    permissionStrList.add(permissionId);
                }
            }
            userVO.setMenuList(menuList);
            userVO.setPermissionList(permissionStrList);
        }
        return userVO;
    }

    private void checkName(User user) {
        Criteria condition = Criteria.where(MongoConstUtils.USERNAME).is(user.getUsername());
        long count = userDao.getCountByCondition(condition);
        if (count > 0) {
            throw new ParameterException("用户名已存在");
        }
    }
}
