package com.simple.general.dao;

import com.simple.general.entity.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Criteria;

/**
 * 设备
 *
 * @author Mr.Wu
 * @date 2020/5/16 21:17
 */
public interface EquipmentDao {
    /**
     * 添加设备
     *
     * @param equipment 内容
     * @author Mr.Wu
     * @date 2020/5/16 21:17
     */
    void save(Equipment equipment);

    /**
     * 修改设备
     *
     * @param equipment 信息
     * @author Mr.Wu
     * @date 2020/5/16 21:17
     */
    void update(Equipment equipment);

    /**
     * 删除设备
     *
     * @param condition 条件
     * @author Mr.Wu
     * @date 2020/5/16 21:17
     */
    void deleteByCondition(Criteria condition);

    /**
     * 查询单个设备
     *
     * @param id 用户id
     * @return com.simple.general.entity.Equipment
     * @author Mr.Wu
     * @date 2020/5/16 21:17
     */
    Equipment findById(Integer id);

    /**
     * 分页查询设备
     *
     * @param condition 条件
     * @param pageNo    页码
     * @param pageSize  查询数量
     * @return org.springframework.data.domain.Page<com.simple.general.entity.Equipment>
     * @author Mr.Wu
     * @date 2020/5/16 21:17
     */
    Page<Equipment> pageList(Criteria condition, int pageNo, int pageSize);

    /**
     * 获取id
     *
     * @author Mr.Wu
     * @date 2020/5/16 21:17
     */
    int getId();

    /**
     * 查询数量
     *
     * @param condition 条件
     * @return long
     * @author Mr.Wu
     * @date 2020/5/16 21:17
     */
    long getCountByCondition(Criteria condition);

    /**
     * 查询单个设备
     *
     * @param name 名称
     * @return com.simple.general.entity.Equipment
     * @author Mr.Wu
     * @date 2020/5/16 21:17
     */
    Equipment findByName(String name);
}
