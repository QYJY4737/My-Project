package cn.congee.api.module.support.websocket;

import cn.congee.api.common.domain.BaseEnum;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午9:10
 **/
public enum MessageTypeEnum implements BaseEnum {

    SYS_NOTICE(1,"系统通知"),

    PRIVATE_LETTER(2,"私信"),

    HEART_BEAT(3,"心跳");


    private Integer value;

    private String desc;


    MessageTypeEnum(Integer value,String desc){
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

}
