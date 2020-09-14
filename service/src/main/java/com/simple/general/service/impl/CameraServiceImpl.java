package com.simple.general.service.impl;

import com.simple.general.dao.CameraDao;
import com.simple.general.entity.Camera;
import com.simple.general.exception.ParameterException;
import com.simple.general.query.BaseQuery;
import com.simple.general.service.CameraService;
import com.simple.general.utils.DateUtils;
import com.simple.general.utils.MongoConstUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("cameraService")
public class CameraServiceImpl implements CameraService {

    private final CameraDao cameraDao;

    @Autowired
    public CameraServiceImpl(CameraDao cameraDao) {
        this.cameraDao = cameraDao;
    }

    @Override
    public void saveCamera(Camera camera) {
        checkName(camera);
        int id = cameraDao.getId();
        camera.setId(id);
        camera.setCreateTime(DateUtils.now());
        camera.setUpdateTime(DateUtils.now());
        cameraDao.save(camera);
    }

    @Override
    public void updateCamera(Camera camera) {
        Camera checkCamera = cameraDao.findById(camera.getId());
        if (!camera.getName().equals(checkCamera.getName())) {
            checkName(camera);
        }
        camera.setUpdateTime(DateUtils.now());
        cameraDao.update(camera);
    }

    @Override
    public void deleteCamera(List<Integer> ids) {
        cameraDao.deleteByCondition(Criteria.where("_id").in(ids));
    }

    @Override
    public Page<Camera> pageListCameras(BaseQuery cameraQuery) {
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(cameraQuery.getName())) {
            criteria.and(MongoConstUtils.NAME).regex(cameraQuery.getName());
        }
        return cameraDao.pageList(criteria, cameraQuery.getPageNo(), cameraQuery.getPageSize());
    }

    private void checkName(Camera camera) {
        Criteria condition = Criteria.where(MongoConstUtils.NAME).is(camera.getName());
        long count = cameraDao.getCountByCondition(condition);
        if (count > 0) {
            throw new ParameterException("摄像头名称已存在");
        }
    }
}
