package cn.congee.api.module.system.datascope.strategy;

import cn.congee.api.module.system.datascope.constant.DataScopeViewTypeEnum;
import cn.congee.api.module.system.datascope.domain.dto.DataScopeSqlConfigDTO;

/**
 * [ 数据范围策略 ,使用DataScopeWhereInTypeEnum.CUSTOM_STRATEGY类型，DataScope注解的joinSql属性无用]
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午9:39
 **/
public abstract class DataScopePowerStrategy {

    /**
     * 获取joinsql 字符串
     * @param viewTypeEnum 查看的类型
     * @param sqlConfigDTO
     * @return
     */
    public abstract String getCondition(DataScopeViewTypeEnum viewTypeEnum, DataScopeSqlConfigDTO sqlConfigDTO);

}
