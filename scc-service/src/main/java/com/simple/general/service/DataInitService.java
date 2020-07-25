package com.simple.general.service;

/**
 * 项目数据初始化
 *
 * @author Mr.Wu
 * @date 2020/4/28 23:41
 */
public interface DataInitService {

    /**
     * 初始化权限
     *
     * @author Mr.Wu
     * @date 2020/4/28 23:41
     */
    void initPermission();

    /**
     * 初始化角色
     *
     * @author Mr.Wu
     * @date 2020/5/5 19:42
     */
    void initRole();

    /**
     * 初始化用户
     *
     * @author Mr.Wu
     * @date 2020/5/5 19:51
     */
    void initUser();

}
