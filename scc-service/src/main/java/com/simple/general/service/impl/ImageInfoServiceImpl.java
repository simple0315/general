package com.simple.general.service.impl;

import com.simple.general.dao.ImageInfoDao;
import com.simple.general.dao.RepositoryDao;
import com.simple.general.entity.ImageInfo;
import com.simple.general.query.ImageQuery;
import com.simple.general.service.ImageInfoService;
import com.simple.general.utils.Base64Utils;
import com.simple.general.utils.DateUtils;
import com.simple.general.utils.MongoConstUtils;
import com.simple.general.utils.PropValUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

@Service("imageInfoService")
public class ImageInfoServiceImpl implements ImageInfoService {

    private final ImageInfoDao imageInfoDao;

    private final RepositoryDao repositoryDao;

    @Autowired
    public ImageInfoServiceImpl(ImageInfoDao imageInfoDao, RepositoryDao repositoryDao) {
        this.imageInfoDao = imageInfoDao;
        this.repositoryDao = repositoryDao;
    }

    @Override
    public void saveImageInfo(ImageInfo imageInfo) {
        int id = imageInfoDao.getId();
        String pictureImage = Base64Utils.replaceEnter(imageInfo.getPictureImage());
        String prefixUrl = PropValUtils.getImageUrl();
        boolean success = Base64Utils.generateImage(pictureImage, String.format("/%s/%s/%s.jpg", prefixUrl, imageInfo.getRepositoryId(), id));
        if (success) {
            imageInfo.setId(id);
            imageInfo.setPictureImage(String.format("/%s/%s.jpg", imageInfo.getRepositoryId(), id));
            int now = DateUtils.now();
            imageInfo.setCreateTime(now);
            imageInfo.setUpdateTime(now);
            imageInfoDao.save(imageInfo);
            repositoryDao.incrementImageNumber(imageInfo.getRepositoryId(), 1);
        } else {
            throw new RuntimeException("人像上传失败");
        }

    }

    @Override
    public void updateImageInfo(ImageInfo imageInfo) {
        Integer id = imageInfo.getId();
        String pictureImage = imageInfo.getPictureImage();
        if (StringUtils.isNotBlank(pictureImage) && !pictureImage.endsWith(".jpg")) {
            String prefixUrl = PropValUtils.getImageUrl();
            boolean success = Base64Utils.generateImage(pictureImage, String.format("/%s/%s/%s.jpg", prefixUrl, imageInfo.getRepositoryId(), id));
            if (!success) {
                throw new RuntimeException("人像更新失败");
            }
            imageInfo.setPictureImage(String.format("/%s/%s.jpg", imageInfo.getRepositoryId(), id));
        }else{
            imageInfo.setPictureImage(null);
        }

        imageInfo.setUpdateTime(DateUtils.now());
        imageInfoDao.update(imageInfo);
    }

    @Override
    public void deleteImageInfo(ImageQuery imageQuery) {
        Integer repositoryId = imageQuery.getRepositoryId();
        Criteria condition = Criteria.where(MongoConstUtils.REPOSITORY_ID).is(repositoryId).and("_id").in(imageQuery.getIdList());
        long count = imageInfoDao.deleteByCondition(condition);
        repositoryDao.incrementImageNumber(repositoryId, -(int) count);
    }

    @Override
    public Page<ImageInfo> pageListImageInfos(ImageQuery imageQuery) {
        Criteria criteria = Criteria.where(MongoConstUtils.REPOSITORY_ID).is(imageQuery.getRepositoryId());
        if (StringUtils.isNotBlank(imageQuery.getName())) {
            criteria.and(MongoConstUtils.NAME).regex(imageQuery.getName());
        }
        return imageInfoDao.pageList(criteria, imageQuery.getPageNo(), imageQuery.getPageSize());
    }
}
