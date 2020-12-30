package cn.congee.api.module.system.privilege.constant;

import cn.congee.api.common.domain.BaseEnum;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午6:19
 **/
public enum PrivilegeTypeEnum implements BaseEnum {

    MENU(1,"菜单"),

    POINTS(2,"功能点");

    private Integer value;

    private String desc;

    PrivilegeTypeEnum(Integer value,String desc){
        this.value = value;
        this.desc = desc;
    }
    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    public static PrivilegeTypeEnum selectByValue(Integer value) {
        Optional<PrivilegeTypeEnum> first = Arrays.stream(PrivilegeTypeEnum.values()).filter(e -> e.getValue().equals(value)).findFirst();
        return !first.isPresent() ? null : first.get();
    }

}
