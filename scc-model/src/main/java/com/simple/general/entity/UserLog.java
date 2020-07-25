package com.simple.general.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLog implements Serializable {

    private static final long serialVersionUID = 12376900002624846L;

    @BsonProperty("_id")
    private Integer id;

    @BsonProperty("user_id")
    private Integer userId;

    private String username;

    @JsonIgnore
    @BsonProperty("session_id")
    private String sessionId;

    @JSONField(name = "remote_host")
    @BsonProperty("remote_host")
    private String remoteHost;

    @JSONField(name = "login_time")
    @JsonProperty("login_time")
    @BsonProperty("login_time")
    private Integer loginTime;

    @JSONField(name = "logout_time")
    @JsonProperty("logout_time")
    @BsonProperty("logout_time")
    private Integer logoutTime;
}
