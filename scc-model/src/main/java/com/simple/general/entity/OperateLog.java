package com.simple.general.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperateLog implements Serializable {

    private static final long serialVersionUID = 671709610349672874L;

    @BsonProperty("_id")
    private Integer id;

    @BsonProperty("user_id")
    private Integer userId;

    private String username;

    @JSONField(name = "remote_host")
    @BsonProperty("remote_host")
    private String remoteHost;

    private String operation;

    private String detail;

    private Integer timestamp;
}
