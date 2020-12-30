package cn.congee.api.module.system.datascope.constant;

import cn.congee.api.common.domain.BaseEnum;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午9:38
 **/
public enum DataScopeWhereInTypeEnum implements BaseEnum {

    EMPLOYEE(0,"以员工IN"),

    DEPARTMENT(1,"以部门IN"),

    CUSTOM_STRATEGY(2,"自定义策略");

    private Integer value;
    private String desc;

    DataScopeWhereInTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDesc() {
        return desc;
    }

}
