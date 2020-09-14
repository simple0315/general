package com.simple.general.mongo;

import com.alibaba.fastjson.JSON;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;

/**
 * 对象转换工具类
 *
 * @author Mr.Wu
 * @date 2020/4/12 15:43
 */
public class MongoConvertUtils {

    /**
     * criteria 转 document
     *
     * @param criteria 转换对象
     * @return org.bson.Document
     * @author Mr.Wu
     * @date 2020/4/12 15:45
     */
    public static Document criteriaConvertDocument(Criteria criteria) {
        return Document.parse(criteria.getCriteriaObject().toString());
    }

    /**
     * object 转 document
     *
     * @param object object对象
     * @return org.bson.Document
     * @author Mr.Wu
     * @date 2020/4/12 15:46
     */
    public static Document objectConvertDocument(Object object) {
        if (object == null) {
            return null;
        }
        return Document.parse(JSON.toJSONString(object));
    }

}
