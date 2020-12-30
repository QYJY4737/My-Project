package cn.congee.api.module.business.email;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午8:35
 **/
public enum EmailSendStatusEnum {

    NOT_SEND(0,"未发送"),

    SEND(1,"已发送");

    private Integer type;
    private String desc;

    EmailSendStatusEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

}
