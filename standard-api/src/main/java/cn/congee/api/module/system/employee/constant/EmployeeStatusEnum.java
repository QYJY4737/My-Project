package cn.congee.api.module.system.employee.constant;

import cn.congee.api.common.domain.BaseEnum;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午4:55
 **/
public enum EmployeeStatusEnum implements BaseEnum {

    /**
     * 用户正常状态 1
     */
    NORMAL(0, "正常"),

    /**
     * 用户已被禁用 0
     */
    DISABLED(1, "禁用");

    private Integer value;

    private String desc;

    EmployeeStatusEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 获取枚举类的值
     *
     * @return Integer
     */
    @Override
    public Integer getValue() {
        return value;
    }

    /**
     * 获取枚举类的说明
     *
     * @return String
     */
    @Override
    public String getDesc() {
        return this.desc;
    }

}
