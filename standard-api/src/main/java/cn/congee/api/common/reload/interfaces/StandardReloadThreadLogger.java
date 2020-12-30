package cn.congee.api.common.reload.interfaces;

/**
 * SmartReloadThreadLogger 日志类
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午4:01
 **/
public interface StandardReloadThreadLogger {

    void error(String string);

    void error(String string, Throwable e);

}
