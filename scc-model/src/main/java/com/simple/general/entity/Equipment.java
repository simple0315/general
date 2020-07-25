package com.simple.general.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.simple.general.base.BaseObj;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 设备模块
 *
 * @author Mr.Wu
 * @date 2020/5/1 14:54
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Equipment extends BaseObj implements Serializable {

    private static final long serialVersionUID = -3930565169206259578L;

    private Integer id;

    private String name;

    private String host;

    private String port;

}
