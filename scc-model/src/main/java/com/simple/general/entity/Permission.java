package com.simple.general.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.simple.general.base.BaseObj;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;

/**
 * 权限
 *
 * @author Mr.Wu
 * @date 2020/4/22 23:14
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class Permission extends BaseObj implements Serializable {

    private static final long serialVersionUID = -2354818891512882488L;

    @BsonProperty("_id")
    private Integer id;

    @JSONField(name = "p_id")
    @JsonProperty("p_id")
    @BsonProperty("p_id")
    private Integer pId;

    private String method;

    @JSONField(name = "permission_id")
    @BsonProperty("permission_id")
    private String permissionId;

    private String uri;

    private String description;

}
