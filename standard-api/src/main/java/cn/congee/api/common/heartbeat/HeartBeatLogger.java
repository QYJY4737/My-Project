package cn.congee.api.common.heartbeat;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午11:30
 **/
public interface HeartBeatLogger {

    void error(String string);

    void error(String string, Throwable e);

    void info(String string);

}
