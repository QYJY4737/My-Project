package cn.congee.api.common.constant;

import cn.congee.api.common.domain.BaseEnum;

import java.util.Arrays;
import java.util.Optional;

/**
 * [ 是与否]
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午4:51
 **/
public enum JudgeEnum implements BaseEnum {

    NO(0, "否"),

    YES(1, "是");

    private Integer value;
    private String desc;

    JudgeEnum(Integer value, String desc) {
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

    public static JudgeEnum valueOf(Integer status) {
        JudgeEnum[] values = JudgeEnum.values();
        Optional<JudgeEnum> first = Arrays.stream(values).filter(e -> e.getValue().equals(status)).findFirst();
        return !first.isPresent() ? null : first.get();
    }

    public static boolean isExist(Integer status) {
        JudgeEnum judgeEnum = valueOf(status);
        return judgeEnum != null;
    }

}
