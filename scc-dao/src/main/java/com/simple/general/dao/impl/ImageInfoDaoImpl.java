package com.simple.general.dao.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.simple.general.dao.ImageInfoDao;
import com.simple.general.entity.ImageInfo;
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

@Repository("imageDao")
public class ImageInfoDaoImpl implements ImageInfoDao {

    MongoCollection<ImageInfo> imageInfoCollection = MongoDBUtils.INSTANCE.getCollection(SystemConstantUtils.SCC_IMAGE,ImageInfo.class);

    @Override
    public void save(ImageInfo imageInfo) {
        MongoDBUtils.INSTANCE.save(imageInfoCollection, imageInfo);
    }

    @Override
    public void update(ImageInfo imageInfo) {
        Integer id = imageInfo.getId();
        Bson bson = Filters.eq("_id", id);
        imageInfo.setId(null);
        MongoDBUtils.INSTANCE.updateOneByCondition(imageInfoCollection, bson, MongoConvertUtils.objectConvertDocument(imageInfo));
    }

    @Override
    public long deleteByCondition(Criteria condition) {
        return MongoDBUtils.INSTANCE.deleteByCondition(imageInfoCollection, MongoConvertUtils.criteriaConvertDocument(condition));
    }

    @Override
    public Page<ImageInfo> pageList(Criteria condition, int pageNo, int pageSize) {
        List<ImageInfo> imageInfoList = MongoDBUtils.INSTANCE.pageByCondition(imageInfoCollection, MongoConvertUtils.criteriaConvertDocument(condition), Filters.eq(MongoConstUtils.CREATE_TIME,-1), pageNo, pageSize, ImageInfo.class);
        long count = getCountByCondition(condition);
        return new PageImpl<>(imageInfoList, new PageRequest(pageNo - 1, pageSize), count);
    }

    @Override
    public int getId() {
        return MongoDBUtils.INSTANCE.getSequence(MongoConstUtils.IMAGE_ID);
    }

    @Override
    public long getCountByCondition(Criteria condition) {
        return MongoDBUtils.INSTANCE.getCountByCondition(imageInfoCollection, MongoConvertUtils.criteriaConvertDocument(condition));
    }
}
