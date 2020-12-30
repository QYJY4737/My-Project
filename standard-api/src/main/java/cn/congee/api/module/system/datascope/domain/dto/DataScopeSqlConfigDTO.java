package cn.congee.api.module.system.datascope.domain.dto;

import cn.congee.api.module.system.datascope.constant.DataScopeTypeEnum;
import cn.congee.api.module.system.datascope.constant.DataScopeWhereInTypeEnum;
import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午9:40
 **/
@Data
public class DataScopeSqlConfigDTO {

    /**
     * 数据范围类型
     * {@link DataScopeTypeEnum}
     */
    private DataScopeTypeEnum dataScopeType;

    /**
     * join sql 具体实现类
     */
    private Class joinSqlImplClazz;

    private String joinSql;

    private Integer whereIndex;

    /**
     * whereIn类型
     * {@link DataScopeWhereInTypeEnum}
     */
    private DataScopeWhereInTypeEnum dataScopeWhereInType;

}
