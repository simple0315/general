package com.simple.general.query;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 查询对象公用类
 *
 * @author Mr.Wu
 * @date 2020/4/12 16:32
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BaseQuery implements Serializable {

    private static final long serialVersionUID = 4263286486301548929L;

    @NotNull(message = "page_no不能为空")
    private Integer pageNo;

    @NotNull(message = "page_size不能为空")
    private Integer pageSize;

    private String name;

}
