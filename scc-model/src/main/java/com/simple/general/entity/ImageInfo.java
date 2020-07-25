package com.simple.general.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.simple.general.base.BaseObj;
import com.simple.general.group.SaveGroup;
import com.simple.general.group.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 人像模块
 *
 * @author Mr.Wu
 * @date 2020/5/1 14:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageInfo extends BaseObj implements Serializable {

    private static final long serialVersionUID = -4844895793593752667L;

    @NotNull(groups = {UpdateGroup.class},message = "id不能为空")
    private Integer id;

    @NotEmpty(groups = {SaveGroup.class,UpdateGroup.class},message = "name不能为空")
    private String name;

    @JSONField(name = "department_id")
    @BsonProperty("department_id")
    private String departmentId;

    @JSONField(name = "job_number")
    @BsonProperty("job_number")
    private String jobNumber;

    private String job;

    private String description;

    @JSONField(name = "picture_image")
    @BsonProperty("picture_image")
    @NotEmpty(groups = {SaveGroup.class},message = "picture_image不能为空")
    private String pictureImage;

    @JSONField(name = "repository_id")
    @BsonProperty("repository_id")
    @NotNull(groups = {SaveGroup.class, UpdateGroup.class},message = "repository_id不能为空")
    private Integer repositoryId;

    @JSONField(name = "person_id")
    @BsonProperty("person_id")
    private String personId;

    /**
     * 性别 0女1男
     */
    private Integer gender;

    private String email;

}
