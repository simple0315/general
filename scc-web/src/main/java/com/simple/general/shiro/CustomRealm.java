package com.simple.general.shiro;

import com.simple.general.entity.Permission;
import com.simple.general.entity.Role;
import com.simple.general.service.PermissionService;
import com.simple.general.service.RoleService;
import com.simple.general.service.UserService;
import com.simple.general.vo.UserVO;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * shiro认证授权
 *
 * @author Mr.Wu
 * @date 2020/5/26 00:53
 */
@Component
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 用户授权
     *
     * @param principalCollection 用户信息
     * @return org.apache.shiro.authz.AuthorizationInfo
     * @author wcy
     * @date 2020/3/25 12:44 上午
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名
        UserVO userWeb = (UserVO) principalCollection.getPrimaryPrincipal();
        String name = userWeb.getUsername();
        //根据用户名查询用户
        UserVO userVO = userService.getUserByName(name);
        if (userVO != null) {
            //添加角色和权限
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            Integer roleId = userVO.getRoleId();
            Role role = roleService.findRoleById(roleId);
            if (role == null) {
                return null;
            }
            //添加角色
//        simpleAuthorizationInfo.addRole(role.getName());
            //添加权限
            List<Permission> permissionList = permissionService.findPermissionByIds(role.getPermissionList());
            List<String> permissionIds = new ArrayList<>();
            permissionList.forEach(permission -> permissionIds.add(permission.getPermissionId()));
            simpleAuthorizationInfo.addStringPermissions(permissionIds);
            return simpleAuthorizationInfo;
        }
        return null;
    }

    /**
     * 认证用户
     *
     * @param authenticationToken 用户特征
     * @return org.apache.shiro.authc.AuthenticationInfo
     * @author wcy
     * @date 2020/3/25 12:44 上午
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //认证
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        String name = authenticationToken.getPrincipal().toString();
        UserVO userVO = userService.getUserByName(name);
        if (userVO == null) {
            return null;
        } else {
            return new SimpleAuthenticationInfo(userVO, userVO.getPassword(), getName());
        }
    }
}
