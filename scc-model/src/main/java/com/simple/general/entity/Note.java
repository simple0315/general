package com.simple.general.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
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


@Data
@EqualsAndHashCode(callSuper = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Note extends BaseObj implements Serializable {

    private static final long serialVersionUID = -4646875117148476091L;

    @BsonProperty("_id")
    @NotNull(groups = UpdateGroup.class,message = "id不能为空")
    private Integer id;

    @NotNull(groups = {SaveGroup.class,UpdateGroup.class},message = "name不能为空")
    private String name;

    private String weather;

    private String info;

}
