package cn.congee.api.common.anno;

import cn.congee.api.module.system.datascope.constant.DataScopeTypeEnum;
import cn.congee.api.module.system.datascope.constant.DataScopeWhereInTypeEnum;
import cn.congee.api.module.system.datascope.strategy.DataScopePowerStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * [ 数据范围 ]
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午9:35
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataScope {

    DataScopeTypeEnum dataScopeType() default DataScopeTypeEnum.DEFAULT;

    DataScopeWhereInTypeEnum whereInType() default DataScopeWhereInTypeEnum.EMPLOYEE;

    /**
     * DataScopeWhereInTypeEnum.CUSTOM_STRATEGY类型 才可使用joinSqlImplClazz属性
     * @return
     */
    Class<? extends DataScopePowerStrategy> joinSqlImplClazz()  default DataScopePowerStrategy.class;

    /**
     *
     * 第几个where 条件 从0开始
     * @return
     */
    int whereIndex() default 0;

    /**
     * DataScopeWhereInTypeEnum为CUSTOM_STRATEGY类型时，此属性无效
     * @return
     */
    String joinSql() default "";

}
