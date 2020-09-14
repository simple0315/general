package com.simple.general.service.impl;

import com.simple.general.dao.EquipmentDao;
import com.simple.general.entity.Equipment;
import com.simple.general.exception.ParameterException;
import com.simple.general.query.BaseQuery;
import com.simple.general.service.EquipmentService;
import com.simple.general.utils.DateUtils;
import com.simple.general.utils.MongoConstUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("equipmentService")
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentDao equipmentDao;

    @Autowired
    public EquipmentServiceImpl(EquipmentDao equipmentDao) {
        this.equipmentDao = equipmentDao;
    }


    @Override
    public void saveEquipment(Equipment equipment) {
        checkName(equipment);
        int id = equipmentDao.getId();
        equipment.setId(id);
        equipment.setCreateTime(DateUtils.now());
        equipment.setUpdateTime(DateUtils.now());
        equipmentDao.save(equipment);
    }

    @Override
    public void updateEquipment(Equipment equipment) {
        Equipment checkEquipment = equipmentDao.findById(equipment.getId());
        if (!equipment.getName().equals(checkEquipment.getName())) {
            checkName(equipment);
        }
        equipment.setUpdateTime(DateUtils.now());
        equipmentDao.update(equipment);
    }

    @Override
    public void deleteEquipment(List<Integer> ids) {
        equipmentDao.deleteByCondition(Criteria.where("_id").in(ids));
    }

    @Override
    public Page<Equipment> pageListEquipments(BaseQuery equipmentQuery) {
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(equipmentQuery.getName())) {
            criteria.and(MongoConstUtils.NAME).regex(equipmentQuery.getName());
        }
        return equipmentDao.pageList(criteria, equipmentQuery.getPageNo(), equipmentQuery.getPageSize());
    }

    private void checkName(Equipment equipment) {
        Criteria condition = Criteria.where(MongoConstUtils.NAME).is(equipment.getName());
        long count = equipmentDao.getCountByCondition(condition);
        if (count > 0) {
            throw new ParameterException("设备名称已存在");
        }
    }
}
