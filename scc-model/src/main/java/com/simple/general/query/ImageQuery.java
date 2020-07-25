package com.simple.general.query;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.simple.general.group.DeleteGroup;
import com.simple.general.group.QueryGroup;
import com.simple.general.group.SaveGroup;
import com.simple.general.group.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 查询人像
 *
 * @author Mr.Wu
 * @date 2020/5/9 00:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageQuery extends BaseQuery{

    private static final long serialVersionUID = -587437753796129590L;

    @JSONField(name = "id")
    @JsonProperty("id")
    @BsonProperty("id")
    @NotEmpty(groups = {UpdateGroup.class, DeleteGroup.class},message = "id不能为空")
    private List<Integer> idList;

    @JSONField(name = "repository_id")
    @JsonProperty("repository_id")
    @NotNull(groups = {QueryGroup.class, SaveGroup.class,UpdateGroup.class,DeleteGroup.class},message = "repository_id不能为空")
    private Integer repositoryId;
}
