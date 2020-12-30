package cn.congee.api.common.exception;

/**
 * Created by wgb
 * Date: 2020/12/30
 * Time: 14:46
 **/
public interface IExceptionMsg {

    /**
     * 获取异常状态码
     *
     * @return
     */
    Integer getCode();

    /**
     * 获取异常消息
     *
     * @return
     */
    String getMsg();

}
