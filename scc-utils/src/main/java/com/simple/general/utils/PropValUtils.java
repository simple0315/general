package com.simple.general.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;

/**
 * 获取配置文件信息
 *
 * @author Mr.Wu
 * @date 2020/4/19 20:45
 */
public class PropValUtils {

    /**
     * 获取图片存储路径
     *
     * @return java.lang.String
     * @author Mr.Wu
     * @date 2020/4/19 20:49
     */
    public static String getImageUrl() {
        String prefix = SystemConfigUtils.getConfig("base.image.url");
        if (StringUtils.isBlank(prefix)) {
            String userDir = System.getProperty("user.dir");
            File file = new File(userDir);
            prefix = file.getParent() + "/picture";
        }
        return prefix;
    }

    /**
     * 获取mongo host
     *
     * @return java.lang.String
     * @author Mr.Wu
     * @date 2020/6/6 23:13
     */
    public static String getMongoHost() {
        return StringUtils.defaultString(SystemConfigUtils.getConfig("cloud.db.host"), "127.0.0.1");
    }

    /**
     * 获取mongo port
     *
     * @return java.lang.String
     * @author Mr.Wu
     * @date 2020/6/6 23:14
     */
    public static Integer getMongoPort() {
        return NumberUtils.toInt(SystemConfigUtils.getConfig("cloud.db.port"), 27017);
    }

    /**
     * 获取mongo dbName
     *
     * @return java.lang.String
     * @author Mr.Wu
     * @date 2020/6/6 23:15
     */
    public static String getMongoDbName() {
        return StringUtils.defaultString(SystemConfigUtils.getConfig("cloud.db.name"), "default");
    }

    /**
     * 获取redis host
     *
     * @return java.lang.String
     * @author Mr.Wu
     * @date 2020/6/6 23:16
     */
    public static String getRedisHost() {
        return StringUtils.defaultString(SystemConfigUtils.getConfig("redis.db.host"), "127.0.0.1");
    }

    /**
     * 获取redis port
     *
     * @return java.lang.String
     * @author Mr.Wu
     * @date 2020/6/6 23:17
     */
    public static Integer getRedisPort() {
        return NumberUtils.toInt(SystemConfigUtils.getConfig("redis.db.port"), 6379);
    }

    /**
     * 获取redis password
     *
     * @return java.lang.String
     * @author Mr.Wu
     * @date 2020/6/6 23:18
     */
    public static String getRedisAuth() {
        return StringUtils.defaultString(SystemConfigUtils.getConfig("redis.db.auth"), "");
    }

    public static void main(String[] args) {
        System.out.println(PropValUtils.getImageUrl());
    }
}
