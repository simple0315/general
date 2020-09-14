package com.simple.general.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BaseObj implements Serializable {

    private static final long serialVersionUID = -1765585901921274803L;

    @JSONField(name = "create_time")
    @JsonProperty("create_time")
    @BsonProperty("create_time")
    protected Integer createTime;
    @JSONField(name = "update_time")
    @JsonProperty("update_time")
    @BsonProperty("update_time")
    protected Integer updateTime;
    @JSONField(name = "created_user")
    @BsonProperty("created_user")
    @JsonProperty("created_user")
    protected String createdUser;

}
