package com.simple.general.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.simple.general.base.BaseObj;
import com.simple.general.group.SaveGroup;
import com.simple.general.group.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.codecs.pojo.annotations.BsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 部门模块
 *
 * @author Mr.Wu
 * @date 2020/5/1 14:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Department extends BaseObj implements Serializable {

    private static final long serialVersionUID = 969622115520770787L;

    @BsonProperty("_id")
    @NotNull(groups = UpdateGroup.class,message = "id不能为空")
    private Integer id;

    @JSONField(name = "p_id")
    @JsonProperty("p_id")
    @BsonProperty("p_id")
    @NotNull(groups = {SaveGroup.class,UpdateGroup.class},message = "p_id不能为空")
    private Integer pId;

    @NotNull(groups = {SaveGroup.class,UpdateGroup.class},message = "name不能为空")
    private String name;

}
