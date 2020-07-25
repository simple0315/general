package com.simple.general.utils;


/**
 * 日期工具类
 *
 * @author Mr.Wu
 * @date 2020/4/12 16:53
 */
public class DateUtils {

    /**
     * 获取系统当前时间10位时间戳
     *
     * @return int
     * @author Mr.Wu
     * @date 2020/4/12 17:29
     */
    public static int now() {
        return (int) (System.currentTimeMillis() / 1000);
    }


}
