package cn.congee.api.module.support.codegenerator.constant;

/**
 * [ gt,lt 目前只支持Date]
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午10:38
 **/
public enum SqlOperateTypeEnum {

    LIKE(1, "like"),

    EQUALS(2, "equals"),

    IN(3, "in");

    private Integer type;

    private String name;

    SqlOperateTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

}
