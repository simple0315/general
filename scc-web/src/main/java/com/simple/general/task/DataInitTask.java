package com.simple.general.task;

import com.simple.general.service.DataInitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 项目数据初始化
 *
 * @author Mr.Wu
 * @date 2020/4/28 23:36
 */
@Component
@Order(1)
@Slf4j
public class DataInitTask implements ApplicationRunner {

    private DataInitService dataInitService;

    @Autowired
    public DataInitTask(DataInitService dataInitService) {
        this.dataInitService = dataInitService;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) {
        // 初始化权限
        dataInitService.initPermission();
        // 初始化角色
        dataInitService.initRole();
        //初始化用户
        dataInitService.initUser();
    }
}
