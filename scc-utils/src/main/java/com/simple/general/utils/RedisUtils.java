package com.simple.general.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.*;

/**
 * redis工具类
 *
 * @author Mr.Wu
 * @date 2020/6/1 00:47
 */
@Slf4j
public class RedisUtils {

    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static final int MAX_WAIT = 15 * 1000;
    //超时时间
    private static final int TIMEOUT = 10 * 1000;

    private static final int MAX_TOTAL = 1000;
    private static final int MAX_IDLE = 100;
    private static final int MIN_IDLE = 50;

    private static JedisPool jedisPool = null;

    private static void initialPool() {
        //Redis服务器IP
        String HOST = PropValUtils.getRedisHost();
        //Redis的端口号
        int PORT = PropValUtils.getRedisPort();
        //访问密码
        String AUTH = PropValUtils.getRedisAuth();

        try {
            JedisPoolConfig config = new JedisPoolConfig();
            //最大连接数，如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            config.setMaxTotal(MAX_TOTAL);
            //最大空闲数，控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
            config.setMaxIdle(MAX_IDLE);
            //最小空闲数
            config.setMinIdle(MIN_IDLE);
            //是否在从池中取出连接前进行检验，如果检验失败，则从池中去除连接并尝试取出另一个
            config.setTestOnBorrow(true);
            //在return给pool时，是否提前进行validate操作
            config.setTestOnReturn(true);
            //在空闲时检查有效性，默认false
            config.setTestWhileIdle(true);
            //表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；
            //这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
            config.setMinEvictableIdleTimeMillis(30000);
            //表示idle object evitor两次扫描之间要sleep的毫秒数
            config.setTimeBetweenEvictionRunsMillis(60000);
            //表示idle object evitor每次扫描的最多的对象数
            config.setNumTestsPerEvictionRun(1000);
            //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
            config.setMaxWaitMillis(MAX_WAIT);

            if (StringUtils.isNotBlank(AUTH)) {
                jedisPool = new JedisPool(config, HOST, PORT, TIMEOUT, AUTH);
            } else {
                jedisPool = new JedisPool(config, HOST, PORT, TIMEOUT);
            }
        } catch (Exception e) {
            if (jedisPool != null) {
                jedisPool.close();
            }
            log.error("初始化Redis连接池失败", e);
        }
    }

    // 初始化Redis连接池
    static {
        initialPool();
    }

    /**
     * 在多线程环境同步初始化
     *
     * @author Mr.Wu
     * @date 2020/6/6 22:59
     */
    private static synchronized void poolInit() {
        if (jedisPool == null) {
            initialPool();
        }
    }

    /**
     * 同步获取Jedis实例
     *
     * @return redis.clients.jedis.Jedis
     * @author Mr.Wu
     * @date 2020/6/6 22:59
     */
    public static Jedis getJedis() {
        if (jedisPool == null) {
            poolInit();
        }
        Jedis jedis = null;
        try {
            if (jedisPool != null) {
                jedis = jedisPool.getResource();
            }
        } catch (Exception e) {
            log.error("同步获取Jedis实例失败" + e.getMessage(), e);
        }
        return jedis;
    }

    /**
     * 释放jedis资源
     *
     * @param jedis jedis
     * @author Mr.Wu
     * @date 2020/6/6 22:58
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null && jedisPool != null) {
            jedisPool.close();
        }
    }

    /**
     * 设置值
     *
     * @param key   key
     * @param value value
     * @return java.lang.String
     * @author Mr.Wu
     * @date 2020/6/6 22:52
     */
    public static String set(String key, String value) {
        Jedis jedis = getJedis();
        if (jedis == null) {
            return null;
        }
        String result = null;
        try {
            result = jedis.set(key, value);
        } catch (Exception e) {
            log.error("设置值失败：" + e.getMessage(), e);
            returnResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 设置值
     *
     * @param key    key
     * @param value  value
     * @param expire 过期时间，单位：秒
     * @return java.lang.String
     * @author Mr.Wu
     * @date 2020/6/6 22:51
     */
    public static String set(String key, String value, int expire) {
        Jedis jedis = getJedis();
        if (jedis == null) {
            return null;
        }
        String result = null;
        try {
            result = jedis.set(key, value);
            jedis.expire(key, expire);
        } catch (Exception e) {
            log.error("设置值失败：" + e.getMessage(), e);
            returnResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取值
     *
     * @param key key
     * @return java.lang.String
     * @author Mr.Wu
     * @date 2020/6/6 22:51
     */
    public static String get(String key) {
        Jedis jedis = getJedis();
        if (jedis == null) {
            return null;
        }
        String result = null;
        try {
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("获取值失败：" + e.getMessage(), e);
            returnResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 判断key是否存在
     *
     * @param key key
     * @return boolean
     * @author Mr.Wu
     * @date 2020/6/6 22:50
     */
    public static boolean exists(String key) {
        Jedis jedis = getJedis();
        if (jedis == null) {
            log.warn("Jedis实例获取为空");
            return false;
        }
        boolean result = false;
        try {
            result = jedis.exists(key);
        } catch (Exception e) {
            log.error(String.format("判断key=%s是否存在失败：" + e.getMessage(), key), e);
            returnResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 删除key
     *
     * @param keys keys
     * @return long
     * @author Mr.Wu
     * @date 2020/6/6 22:50
     */
    public static long del(String... keys) {
        Jedis jedis = getJedis();
        if (jedis == null) {
            return 0;
        }
        long result = 0;
        try {
            result = jedis.del(keys);
        } catch (Exception e) {
            log.error(String.format("删除key=%s失败：" + e.getMessage(), (Object[]) keys), e);
            returnResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * set if not exists，若key已存在，则setnx不做任何操作
     *
     * @param key   key
     * @param value key已存在，1：key赋值成功
     * @return long
     * @author Mr.Wu
     * @date 2020/6/6 22:49
     */
    public static long setnx(String key, String value) {
        long result = 0;
        Jedis jedis = getJedis();
        if (jedis == null) {
            return result;
        }
        try {
            result = jedis.setnx(key, value);
        } catch (Exception e) {
            log.error("设置值失败：" + e.getMessage(), e);
            returnResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * set if not exists，若key已存在，则setnx不做任何操作
     *
     * @param key    key
     * @param value  key已存在，1：key赋值成功
     * @param expire 过期时间，单位：秒
     * @return long
     * @author Mr.Wu
     * @date 2020/6/6 22:48
     */
    public static long setnx(String key, String value, int expire) {
        long result = 0;
        Jedis jedis = getJedis();
        if (jedis == null) {
            return result;
        }
        try {
            result = jedis.setnx(key, value);
            jedis.expire(key, expire);
        } catch (Exception e) {
            log.error("设置值失败：" + e.getMessage(), e);
            returnResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 在列表key的头部插入元素
     *
     * @param key    key
     * @param values values
     * @return long
     * @author Mr.Wu
     * @date 2020/6/6 22:47
     */
    public static long lpush(String key, String... values) {
        long result = 0;
        Jedis jedis = getJedis();
        if (jedis == null) {
            return result;
        }
        try {
            result = jedis.lpush(key, values);
        } catch (Exception e) {
            log.error("在列表key的头部插入元素失败：" + e.getMessage(), e);
            returnResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 在列表key的尾部插入元素
     *
     * @param key    key
     * @param values values
     * @return long
     * @author Mr.Wu
     * @date 2020/6/6 22:46
     */
    public static long rpush(String key, String... values) {
        long result = 0;

        Jedis jedis = getJedis();
        if (jedis == null) {
            return result;
        }
        try {
            result = jedis.rpush(key, values);
        } catch (Exception e) {
            log.error("在列表key的尾部插入元素失败：" + e.getMessage(), e);
            returnResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 返回存储在key列表的特定元素
     *
     * @param key   key
     * @param start 开始索引，索引从0开始，0表示第一个元素，1表示第二个元素
     * @param end   结束索引，-1表示最后一个元素，-2表示倒数第二个元素
     * @return java.util.List<java.lang.String>
     * @author Mr.Wu
     * @date 2020/6/6 22:46
     */
    public static List<String> lrange(String key, long start, long end) {
        Jedis jedis = getJedis();
        if (jedis == null) {
            return null;
        }
        List<String> result = null;
        try {
            result = jedis.lrange(key, start, end);
        } catch (Exception e) {
            log.error("查询列表元素失败：" + e.getMessage(), e);
            returnResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取列表长度
     *
     * @param key key
     * @return long
     * @author Mr.Wu
     * @date 2020/6/6 22:45
     */
    public static long llen(String key) {
        Jedis jedis = getJedis();
        if (jedis == null) {
            return 0;
        }
        long result = 0;
        try {
            result = jedis.llen(key);
        } catch (Exception e) {
            log.error("获取列表长度失败：" + e.getMessage(), e);
            returnResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 移除等于value的元素<br/><br/>
     * 当count>0时，从表头开始查找，移除count个；<br/>
     * 当count=0时，从表头开始查找，移除所有等于value的；<br/>
     * 当count<0时，从表尾开始查找，移除count个
     *
     * @param key   key
     * @param count count 查找位置
     * @param value value值
     * @return long
     * @author Mr.Wu
     * @date 2020/6/6 22:44
     */
    public static long lrem(String key, long count, String value) {
        Jedis jedis = getJedis();
        if (jedis == null) {
            return 0;
        }
        long result = 0;
        try {
            result = jedis.lrem(key, count, value);
        } catch (Exception e) {
            log.error("获取列表长度失败：" + e.getMessage(), e);
            returnResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 对列表进行修剪
     *
     * @param key   key
     * @param start 开始位置
     * @param end   结束位置
     * @return java.lang.String
     * @author Mr.Wu
     * @date 2020/6/6 22:43
     */
    public static String ltrim(String key, long start, long end) {
        Jedis jedis = getJedis();
        if (jedis == null) {
            return null;
        }
        String result = "";
        try {
            result = jedis.ltrim(key, start, end);
        } catch (Exception e) {
            log.error("获取列表长度失败：" + e.getMessage(), e);
            returnResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 缓存Map赋值
     *
     * @param key   key
     * @param field field
     * @param value value
     * @return long
     * @author Mr.Wu
     * @date 2020/6/6 22:42
     */
    public static long hset(String key, String field, String value) {
        Jedis jedis = getJedis();
        if (jedis == null) {
            return 0;
        }
        long result = 0L;
        try {
            result = jedis.hset(key, field, value);
        } catch (Exception e) {
            log.error("缓存Map赋值失败：" + e.getMessage(), e);
            returnResource(jedis);
        }
//        finally {
//            returnResource(jedis);
//        }
        return result;
    }

    /**
     * 获取缓存的Map值
     *
     * @param key   key
     * @param field field
     * @return java.lang.String
     * @author Mr.Wu
     * @date 2020/6/6 22:41
     */
    public static String hget(String key, String field) {
        Jedis jedis = getJedis();
        if (jedis == null) {
            return null;
        }
        String result = null;
        try {
            result = jedis.hget(key, field);
        } catch (Exception e) {
            log.error("获取缓存的Map值失败：" + e.getMessage(), e);
            returnResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取map所有的字段和值
     *
     * @param key key
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author Mr.Wu
     * @date 2020/6/6 22:41
     */
    public static Map<String, String> hgetAll(String key) {
        Map<String, String> map = new HashMap<>();
        Jedis jedis = getJedis();
        if (jedis == null) {
            log.warn("Jedis实例获取为空");
            return map;
        }
        try {
            map = jedis.hgetAll(key);
        } catch (Exception e) {
            log.error("获取map所有的字段和值失败：" + e.getMessage(), e);
            returnResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return map;
    }

    /**
     * 查看哈希表 key 中，指定的field字段是否存在。
     *
     * @param key   key
     * @param field field
     * @return java.lang.Boolean
     * @author Mr.Wu
     * @date 2020/6/6 22:40
     */
    public static Boolean hexists(String key, String field) {
        Jedis jedis = getJedis();
        if (jedis == null) {
            log.warn("Jedis实例获取为空");
            return null;
        }
        try {
            return jedis.hexists(key, field);
        } catch (Exception e) {
            log.error("查看哈希表field字段是否存在失败：" + e.getMessage(), e);
            returnResource(jedis);
        } finally {
            returnResource(jedis);
        }

        return null;
    }

    /**
     * 获取所有哈希表中的字段
     *
     * @param key key
     * @return java.util.Set<java.lang.String>
     * @author Mr.Wu
     * @date 2020/6/6 22:40
     */
    public static Set<String> hkeys(String key) {
        Set<String> set = new HashSet<String>();
        Jedis jedis = getJedis();
        if (jedis == null) {
            log.warn("Jedis实例获取为空");
            return set;
        }

        try {
            return jedis.hkeys(key);
        } catch (Exception e) {
            log.error("获取所有哈希表中的字段失败：" + e.getMessage(), e);
            returnResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return null;
    }

    /**
     * 获取所有哈希表中的值
     *
     * @param key key
     * @return java.util.List<java.lang.String>
     * @author Mr.Wu
     * @date 2020/6/6 22:38
     */
    public static List<String> hvals(String key) {
        List<String> list = new ArrayList<String>();
        Jedis jedis = getJedis();
        if (jedis == null) {
            log.warn("Jedis实例获取为空");
            return list;
        }

        try {
            return jedis.hvals(key);
        } catch (Exception e) {
            log.error("获取所有哈希表中的值失败：" + e.getMessage(), e);
            returnResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return null;
    }

    /**
     * 从哈希表key中删除指定的field
     *
     * @param key    key
     * @param fields fields
     * @return long
     * @author Mr.Wu
     * @date 2020/6/6 22:37
     */
    public static long hdel(String key, String... fields) {
        Jedis jedis = getJedis();
        if (jedis == null) {
            log.warn("Jedis实例获取为空");
            return 0;
        }

        try {
            return jedis.hdel(key, fields);
        } catch (Exception e) {
            log.error("map删除指定的field失败：" + e.getMessage(), e);
            returnResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return 0;
    }

    public static Set<String> keys(String pattern) {
        Set<String> keyList = new HashSet<>();
        Jedis jedis = getJedis();
        if (jedis == null) {
            log.warn("Jedis实例获取为空");
            return keyList;
        }
        try {
            keyList = jedis.keys(pattern);
        } catch (Exception e) {
            log.error("操作keys失败：" + e.getMessage(), e);
            returnResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return keyList;
    }

    public static void main(String[] args) {
//            set( "1",  "111");
//        hset("test","1","111");
        hset("test","3","33444455555553");
        System.out.println(hget("test", "2"));
    }
}
