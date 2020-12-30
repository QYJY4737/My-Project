package cn.congee.api.common.exception;

/**
 * 基本异常消息定义
 *
 * Created by wgb
 * Date: 2020/12/30
 * Time: 14:47
 **/
public enum BaseExceptionMsg implements IExceptionMsg {

    EXECUTE_OK(0, "执行成功");

    private Integer code;

    private String msg;

    BaseExceptionMsg(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

}
