package com.simple.general.dao.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.simple.general.dao.EquipmentDao;
import com.simple.general.entity.Equipment;
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

@Repository("equipmentDao")
public class EquipmentDaoImpl implements EquipmentDao {

    private MongoCollection<Equipment> equipmentCollection = MongoDBUtils.INSTANCE.getCollection(SystemConstantUtils.SCC_EQUIPMENT,Equipment.class);

    @Override
    public void save(Equipment equipment) {
        MongoDBUtils.INSTANCE.save(equipmentCollection, equipment);
    }

    @Override
    public void update(Equipment equipment) {
        Integer id = equipment.getId();
        Bson bson = Filters.eq("_id", id);
        equipment.setId(null);
        MongoDBUtils.INSTANCE.updateOneByCondition(equipmentCollection, bson, MongoConvertUtils.objectConvertDocument(equipment));
    }

    @Override
    public void deleteByCondition(Criteria condition) {
        MongoDBUtils.INSTANCE.deleteByCondition(equipmentCollection,MongoConvertUtils.criteriaConvertDocument(condition));
    }

    @Override
    public Equipment findById(Integer id) {
        Bson bson = Filters.eq("_id", id);
        return MongoDBUtils.INSTANCE.findOneByCondition(equipmentCollection, bson, Equipment.class);
    }

    @Override
    public Page<Equipment> pageList(Criteria condition, int pageNo, int pageSize) {
        List<Equipment> EquipmentList = MongoDBUtils.INSTANCE.pageByCondition(equipmentCollection, MongoConvertUtils.criteriaConvertDocument(condition), Filters.eq(MongoConstUtils.CREATE_TIME,-1), pageNo, pageSize, Equipment.class);
        long count = getCountByCondition(condition);
        return new PageImpl<>(EquipmentList, new PageRequest(pageNo - 1, pageSize), count);
    }

    @Override
    public int getId() {
        return MongoDBUtils.INSTANCE.getSequence(MongoConstUtils.EQUIPMENT_ID);
    }

    @Override
    public long getCountByCondition(Criteria condition) {
        return MongoDBUtils.INSTANCE.getCountByCondition(equipmentCollection, MongoConvertUtils.criteriaConvertDocument(condition));
    }

    @Override
    public Equipment findByName(String name) {
        return MongoDBUtils.INSTANCE.findOneByCondition(equipmentCollection, Filters.eq(MongoConstUtils.NAME,name), Equipment.class);
    }
}
