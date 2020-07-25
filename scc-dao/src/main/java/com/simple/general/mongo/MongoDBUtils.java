package com.simple.general.mongo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.simple.general.utils.PropValUtils;
import com.simple.general.utils.SystemConstantUtils;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * mongo工具类
 *
 * @author Mr.Wu
 * @date 2020/4/12 01:58
 */
@Slf4j
public enum MongoDBUtils {

    /**
     * 定义一个枚举元素，它代表此类的一个实例；
     */
    INSTANCE;

    MongoDBUtils() {
    }

    private static volatile com.mongodb.client.MongoClient mongoClient;
    private static String DBName;

    static {
        String host = PropValUtils.getMongoHost();
        Integer port = PropValUtils.getMongoPort();
        DBName = PropValUtils.getMongoDbName();
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(pojoCodecRegistry).applyConnectionString(new ConnectionString(String.format("mongodb://%s:%s", host, port)))
                .build();
        mongoClient = MongoClients.create(settings);
    }

    /**
     * 获取DB实例
     *
     * @return MongoDatabase
     */
    public static MongoDatabase getMongoDataBase() {
        return mongoClient.getDatabase(DBName);
    }

    /**
     * 创建集合
     *
     * @param table 集合名称
     * @author Mr.Wu
     * @date 2020/4/12 02:39
     */
    public void createCollection(String table) {
        MongoDatabase db = getMongoDataBase();
        db.createCollection(table);
        log.debug("集合创建成功，db：{}, table：{}", db, table);
    }

    /**
     * 删除集合
     *
     * @param table 集合名称
     * @author Mr.Wu
     * @date 2020/4/12 02:42
     */
    public void dropCollection(String table) {
        MongoDatabase db = getMongoDataBase();
        db.getCollection(table).drop();
        log.debug("集合删除成功，db：{}，table：{}", db.getName(), table);
    }

    /**
     * 获取指定集合
     *
     * @param collName 集合名称
     * @return com.mongodb.client.MongoCollection<T>
     * @author Mr.Wu
     * @date 2020/4/12 02:17
     */
    public MongoCollection<Document> getCollection(String collName) {
        if (collName == null) {
            return null;
        }
        return getMongoDataBase().getCollection(collName);
    }

    /**
     * 获取指定集合
     *
     * @param collName 集合名称
     * @return com.mongodb.client.MongoCollection<T>
     * @author Mr.Wu
     * @date 2020/4/12 02:17
     */
    public <T> MongoCollection<T> getCollection(String collName, Class<T> clazz) {
        if (collName == null) {
            return null;
        }
        return getMongoDataBase().getCollection(collName, clazz);
    }

    /**
     * 单个查询
     *
     * @param collection 集合
     * @param condition  条件
     * @param clazz      泛型
     * @return T
     * @author Mr.Wu
     * @date 2020/4/12 17:50
     */
    public <T> T findOneByCondition(MongoCollection<T> collection, Bson condition, Class<T> clazz) {
        T first = collection.find(condition).first();
        log.debug("检索doc完毕，db：{}，condition：{} ", collection, condition);
        return JSONObject.parseObject(JSON.toJSONString(first), clazz);
    }

    /**
     * 通过条件查询
     *
     * @param collection 集合
     * @param condition  条件
     * @return java.util.List<T>
     * @author Mr.Wu
     * @date 2020/4/12 02:53
     */
    public <T> List<T> findByCondition(MongoCollection<T> collection, Bson condition, Class<T> clazz) {
        FindIterable<T> iterable = collection.find(condition);
        List<T> list = new ArrayList<>();
        for (T t : iterable) {
            T t1 = JSONObject.parseObject(JSON.toJSONString(t), clazz);
            list.add(t1);
        }
        log.debug("检索doc完毕，db：{}，condition：{} ", collection, condition);
        return list;
    }

    /**
     * 分页查询
     *
     * @param collection 集合
     * @param condition  条件
     * @param order      排序
     * @param pageNo     页码
     * @param pageSize   查询数量
     * @return java.util.List<T>
     * @author Mr.Wu
     * @date 2020/4/12 14:49
     */
    public <T> List<T> pageByCondition(MongoCollection<T> collection, Bson condition, Bson order, int pageNo, int pageSize, Class<T> clazz) {
        FindIterable<T> iterable = collection.find(condition).skip((pageNo - 1) * pageSize).limit(pageSize).sort(order);
        List<T> list = new ArrayList<>();
        for (T t : iterable) {
            T t1 = JSONObject.parseObject(JSON.toJSONString(t), clazz);
            list.add(t1);
        }
        log.debug("检索doc完毕，db：{}，condition：{},order: {} ", collection, condition, order);
        return list;
    }


    /**
     * 查询所有
     *
     * @param collection 集合
     * @param clazz      泛型
     * @return java.util.List<T>
     * @author Mr.Wu
     * @date 2020/4/12 02:52
     */
    public <T> List<T> findAll(MongoCollection<T> collection, Class<T> clazz) {
        FindIterable<T> iterable = collection.find();
        List<T> list = new ArrayList<>();
        for (T t : iterable) {
            T t1 = JSONObject.parseObject(JSON.toJSONString(t), clazz);
            list.add(t1);
        }
        log.debug("检索全部完毕，db：{}", collection);
        return list;
    }

    /**
     * 添加单个数据
     *
     * @param collection 集合
     * @param t          数据
     * @author Mr.Wu
     * @date 2020/4/12 03:18
     */
    public <T> void save(MongoCollection<T> collection, T t) {
        collection.insertOne(t);
    }

    /**
     * 批量删除
     *
     * @param collection 集合
     * @param list       插入数量
     * @author Mr.Wu
     * @date 2020/4/12 03:13
     */
    public <T> void saveMany(MongoCollection<T> collection, List<T> list) {
        collection.insertMany(list);
    }

    /**
     * 批量删除
     *
     * @param collection 集合
     * @param condition  条件
     * @return boolean
     * @author Mr.Wu
     * @date 2020/4/12 03:07
     */
    public <T> Long deleteByCondition(MongoCollection<T> collection, Bson condition) {
        DeleteResult deleteManyResult = collection.deleteMany(condition);
        long deletedCount = deleteManyResult.getDeletedCount();
        log.debug("文档删除成功，影响条数：{}，condition：{} ", deletedCount, condition.toString());
        return deletedCount;
    }

    /**
     * 删除单个数据
     *
     * @param collection 集合
     * @param condition  条件
     * @return java.lang.Long
     * @author Mr.Wu
     * @date 2020/4/12 03:03
     */
    public <T> Long deleteOneByCondition(MongoCollection<T> collection, Bson condition) {
        DeleteResult deleteOneResult = collection.deleteOne(condition);
        long deletedCount = deleteOneResult.getDeletedCount();
        log.debug("文档删除成功，影响条数：{}，condition：{} ", deletedCount, condition.toString());
        return deletedCount;
    }

    /**
     * 批量修改
     *
     * @param collection 集合
     * @param whereDoc   条件
     * @param updateDoc  内容
     * @return java.lang.Long
     * @author Mr.Wu
     * @date 2020/4/12 02:57
     */
    public <T> Long updateManyByCondition(MongoCollection<T> collection,
                                          Bson whereDoc, Bson updateDoc) {
        UpdateResult updateManyResult = collection.updateMany(whereDoc,
                new Document("$set", updateDoc));
        long updateCount = updateManyResult.getModifiedCount();
        log.debug("文档更新成功，影响条数：{}，whereDoc：{}，updateDoc：{} ", updateCount, whereDoc.toString(), updateDoc.toString());
        return updateCount;
    }

    /**
     * 更新单条数据
     *
     * @param collection 集合
     * @param condition  条件
     * @param update     内容
     * @return java.lang.Long
     * @author Mr.Wu
     * @date 2020/4/12 02:52
     */
    public <T> Long updateOneByCondition(MongoCollection<T> collection, Bson condition, Bson update) {
        UpdateResult updateOneResult = collection.updateOne(condition,
                new Document("$set", update));
        long updateCount = updateOneResult.getModifiedCount();
        log.debug("文档更新成功，影响条数：{}，condition：{}，update：{} ", updateCount, condition.toString(), update.toString());
        return updateCount;
    }

    /**
     * 查询数量
     *
     * @param collection 集合
     * @return long
     * @author Mr.Wu
     * @date 2020/4/12 14:50
     */
    public <T> long getCountByCondition(MongoCollection<T> collection) {
        return collection.countDocuments();
    }


    /**
     * 条件查询数量
     *
     * @param collection 集合
     * @param condition  条件
     * @return long
     * @author Mr.Wu
     * @date 2020/4/12 14:50
     */
    public <T> long getCountByCondition(MongoCollection<T> collection, Bson condition) {
        return collection.countDocuments(condition);
    }

    /**
     * 自增id
     *
     * @param key 自增id模块key
     * @return int
     * @author Mr.Wu
     * @date 2020/4/12 21:00
     */
    public int getSequence(String key) {
        MongoCollection<Document> sequenceCollection = getMongoDataBase().getCollection(SystemConstantUtils.SCC_SEQUENCE);
        Document document = new Document();
        Document update = new Document();
        update.put("sequence_val", 1);
        document.put("$inc", update);
        Document condition = new Document();
        condition.put("_id", key);
        Document result = sequenceCollection.findOneAndUpdate(condition, document);
        if (result == null) {
            Document initDocument = new Document();
            initDocument.put("_id", key);
            initDocument.put("sequence_val", 11);
            sequenceCollection.insertOne(initDocument);
            return 10;
        }
        return Integer.parseInt(result.get("sequence_val").toString());
    }

    /**
     * 指定字段增加指定值
     *
     * @param mongoCollection mongo连接
     * @param condition       条件
     * @param fieldName       字段名
     * @param increment       增量
     * @author Mr.Wu
     * @date 2020/5/8 00:18
     */
    public void increase(MongoCollection<?> mongoCollection, Bson condition, String fieldName, int increment) {
        Document document = new Document();
        document.put("$inc", new Document(fieldName, increment));
        synchronized (this) {
            mongoCollection.updateOne(condition, document);
        }
    }
}
