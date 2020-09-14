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
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 用户角色
 *
 * @author Mr.Wu
 * @date 2020/4/22 23:13
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseObj implements Serializable {

    private static final long serialVersionUID = 4232019557243098439L;

    @BsonProperty("_id")
    @NotNull(groups = {UpdateGroup.class},message = "id不能为空")
    private Integer id;

    @NotBlank(groups = {SaveGroup.class, UpdateGroup.class},message = "name不能为空")
    private String name;

    @JSONField(name = "permission_id")
    @JsonProperty("permission_id")
    @BsonProperty("permission_id")
    private List<Integer> permissionList;
}
