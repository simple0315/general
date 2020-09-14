package com.simple.general.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 读取配置文件工具类
 *
 * @author Mr.Wu
 * @date 2020/4/11 11:29 下午
 */
@Slf4j
public class SystemConfigUtils {

    private static final Map<String, String> CONFIGURES = new HashMap<>();

    static {
        loadProperties();
    }

    /**
     * 加载配置文件资源
     *
     * @author Mr.Wu
     * @date 2020/4/12 00:00
     */
    private static void loadProperties() {
        String configPath = getConfigFile("general.properties");
        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(configPath));
            Properties propertyFiles = new Properties();
            propertyFiles.load(inputStream);
            for (Object key : propertyFiles.keySet()) {
                CONFIGURES.put(key.toString(), propertyFiles.getProperty(key.toString()));
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

    /**
     * 读取文件路径 在项目文件的上一次conf文件夹里面
     *
     * @return java.lang.String
     * @author Mr.Wu
     * @date 2020/4/12 01:08
     */
    public static String getConfigFileDir() {
        return System.getProperty("user.dir") + File.separator + "config";
    }

    public static String getConfigFile(String fileNamePath) {
        String path = getConfigFileDir() + File.separator + fileNamePath;
        booleanFileExists(new File(path));
        return path;
    }

    /**
     * 判断文件是否存在
     *
     * @param file 配置文件
     * @author Mr.Wu
     * @date 2020/4/12 01:08
     */
    public static void booleanFileExists(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据配置文件的key，返回对应的配置值
     *
     * @param configKey 配置文件key
     * @return java.lang.String
     * @author Mr.Wu
     * @date 2020/4/12 00:01
     */
    public static String getConfig(String configKey) {
        return CONFIGURES.get(configKey);
    }

    /**
     * 根据配置文件的key，返回对应的配置值，如果configKey不存在,或者值为空的字符串，则返回默认值defaultValue
     *
     * @param configKey    配置文件key
     * @param defaultValue 默认值
     * @return java.lang.String
     * @author Mr.Wu
     * @date 2020/4/12 00:01
     */
    public static String getConfig(String configKey, String defaultValue) {
        String key = CONFIGURES.get(configKey);
        if (key == null || "".equals(key.trim())) {
            return defaultValue;
        }
        return key;
    }

    /**
     * 属性文件中是否包含键configKey，包含返回true
     *
     * @param configKey 配置文件key
     * @return boolean
     * @author Mr.Wu
     * @date 2020/4/12 00:03
     */
    public static boolean contentKey(String configKey) {
        return CONFIGURES.containsKey(configKey);
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir") + File.separator + "scc-config" + File.separator + "general.properties");
        System.out.println(getConfig("cloud.db.host"));
    }
}
