package com.simple.general.annotation;

import com.mongodb.client.model.changestream.OperationType;

/**
 * operateLog
 *
 * @author Mr.Wu
 * @date 2020/9/9 23:54
 */
public @interface OperationLogDetail {

    /**
     * 模块描述，可使用占位符获取参数:{{operation}}
     */
    String operation() default "";

    /**
     * 方法描述,可使用占位符获取参数:{{detail}}
     */
    String detail() default "";

}
