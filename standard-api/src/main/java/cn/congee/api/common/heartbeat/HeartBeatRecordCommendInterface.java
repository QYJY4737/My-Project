package cn.congee.api.common.heartbeat;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午11:28
 **/
public interface HeartBeatRecordCommendInterface {

    /**
     * 处理
     * @param heartBeatRecord
     */
    void handler(HeartBeatRecordDTO heartBeatRecord);

}
