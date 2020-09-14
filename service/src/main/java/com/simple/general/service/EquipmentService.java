package com.simple.general.service;

import com.simple.general.entity.Equipment;
import com.simple.general.query.BaseQuery;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 设备模块
 *
 * @author Mr.Wu
 * @date 2020/5/16 21:30
 */
public interface EquipmentService {

    /**
     * 添加设备
     *
     * @param equipment 添加内容
     * @author Mr.Wu
     * @date 2020/5/16 21:30
     */
    void saveEquipment(Equipment equipment);

    /**
     * 修改设备
     *
     * @param equipment 修改内容
     * @author Mr.Wu
     * @date 2020/5/16 21:30
     */
    void updateEquipment(Equipment equipment);

    /**
     * 单个/批量删除设备
     *
     * @param ids id数组
     * @author Mr.Wu
     * @date 2020/5/16 21:30
     */
    void deleteEquipment(List<Integer> ids);

    /**
     * 分页查询设备
     *
     * @param equipmentQuery 查询条件
     * @return org.springframework.data.domain.Page<com.simple.general.entity.Equipment>
     * @author Mr.Wu
     * @date 2020/5/16 21:30
     */
    Page<Equipment> pageListEquipments(BaseQuery equipmentQuery);

}
