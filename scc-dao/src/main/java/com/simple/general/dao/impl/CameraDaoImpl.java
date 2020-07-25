package com.simple.general.dao.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.simple.general.dao.CameraDao;
import com.simple.general.entity.Camera;
import com.simple.general.mongo.MongoConvertUtils;
import com.simple.general.mongo.MongoDBUtils;
import com.simple.general.utils.MongoConstUtils;
import com.simple.general.utils.SystemConstantUtils;
import org.bson.conversions.Bson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("cameraDao")
public class CameraDaoImpl implements CameraDao {

    private MongoCollection<Camera> cameraCollection = MongoDBUtils.INSTANCE.getCollection(SystemConstantUtils.SCC_CAMERA,Camera.class);

    @Override
    public void save(Camera camera) {
        MongoDBUtils.INSTANCE.save(cameraCollection, camera);
    }

    @Override
    public void update(Camera camera) {
        Integer id = camera.getId();
        Bson bson = Filters.eq("_id", id);
        camera.setId(null);
        MongoDBUtils.INSTANCE.updateOneByCondition(cameraCollection, bson, MongoConvertUtils.objectConvertDocument(camera));
    }

    @Override
    public void deleteByCondition(Criteria condition) {
        MongoDBUtils.INSTANCE.deleteByCondition(cameraCollection,MongoConvertUtils.criteriaConvertDocument(condition));
    }

    @Override
    public Camera findById(Integer id) {
        Bson bson = Filters.eq("_id", id);
        return MongoDBUtils.INSTANCE.findOneByCondition(cameraCollection, bson, Camera.class);
    }

    @Override
    public Page<Camera> pageList(Criteria condition, int pageNo, int pageSize) {
        List<Camera> cameraList = MongoDBUtils.INSTANCE.pageByCondition(cameraCollection, MongoConvertUtils.criteriaConvertDocument(condition), Filters.eq(MongoConstUtils.CREATE_TIME,-1), pageNo, pageSize, Camera.class);
        long count = getCountByCondition(condition);
        return new PageImpl<>(cameraList, new PageRequest(pageNo - 1, pageSize), count);
    }

    @Override
    public int getId() {
        return MongoDBUtils.INSTANCE.getSequence(MongoConstUtils.CAMERA_ID);
    }

    @Override
    public long getCountByCondition(Criteria condition) {
        return MongoDBUtils.INSTANCE.getCountByCondition(cameraCollection, MongoConvertUtils.criteriaConvertDocument(condition));
    }

    @Override
    public Camera findByName(String name) {
        return MongoDBUtils.INSTANCE.findOneByCondition(cameraCollection, Filters.eq(MongoConstUtils.NAME,name), Camera.class);
    }
}
