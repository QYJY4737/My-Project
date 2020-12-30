package cn.congee.api.common.exception;

/**
 * [ 业务逻辑异常,全局异常拦截后统一返回ResponseCodeConst.SYSTEM_ERROR ]
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午6:18
 **/
public class StandardBusinessException extends RuntimeException {

    public StandardBusinessException() {
    }

    public StandardBusinessException(String message) {
        super(message);
    }

    public StandardBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public StandardBusinessException(Throwable cause) {
        super(cause);
    }

    public StandardBusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
