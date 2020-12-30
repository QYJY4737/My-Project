package cn.congee.api.module.support.quartz.constant;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午11:48
 **/
public enum TaskResultEnum {

    SUCCESS(0,"成功"),

    FAIL(1,"失败");

    public static final String INFO="0:成功，1:失败";

    private Integer status;

    private String desc;

    TaskResultEnum(Integer status , String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
