package com.simple.general.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户
 *
 * @author Mr.Wu
 * @date 2020/5/28 23:22
 */
@Data
public class UserVO implements Serializable {

    private static final long serialVersionUID = -1363553304054892298L;

    private Integer id;

    private String username;

    private String password;

    @JSONField(name = "role_id")
    @JsonProperty("role_id")
    private Integer roleId;

    @JSONField(name = "role_name")
    @JsonProperty("role_name")
    private String roleName;

    @JSONField(name = "menu_list")
    @JsonProperty("menu_list")
    private List<String> menuList;

    @JSONField(name = "permission_list")
    @JsonProperty("permission_list")
    private List<String> permissionList;

}
