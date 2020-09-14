package com.simple.general.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.simple.general.base.BaseObj;
import com.simple.general.group.QueryGroup;
import com.simple.general.group.SaveGroup;
import com.simple.general.group.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户
 *
 * @author Mr.Wu
 * @date 2020/4/22 23:14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ToString(callSuper = true)
public class User extends BaseObj implements Serializable {

    private static final long serialVersionUID = -7255676085158637339L;

    @BsonProperty("_id")
    @NotNull(groups = UpdateGroup.class,message = "id不能为空")
    private Integer id;

    @NotBlank(groups = {QueryGroup.class,SaveGroup.class,UpdateGroup.class}, message = "username不能为空")
    private String username;
    @NotBlank(groups = QueryGroup.class,message = "password不能为空")
    private String password;

    @JSONField(name="role_id")
    @BsonProperty("role_id")
    @JsonProperty("role_id")
    private Integer roleId;

    @JSONField(name="role_name")
    @BsonProperty("role_name")
    @JsonProperty("role_name")
    private String roleName;

}
