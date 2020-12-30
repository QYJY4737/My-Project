package cn.congee.api.common.exception;

/**
 * [ 全局异常拦截后保留ResponseCode码的异常]
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午12:11
 **/
public class StandardResponseCodeException extends RuntimeException {

    private Integer code;

    public StandardResponseCodeException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

}
