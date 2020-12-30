package cn.congee.api.common.heartbeat;

import lombok.Data;

import java.util.Date;

/**
 * 心跳记录日志
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午11:28
 **/
@Data
public class HeartBeatRecordDTO {

    /**
     * 项目名字
     */
    private String projectPath;

    /**
     * 服务器ip
     */
    private String serverIp;

    /**
     * 进程号
     */
    private Integer processNo;

    /**
     * 进程开启时间
     */
    private Date processStartTime;

    /**
     * 心跳当前时间
     */
    private Date heartBeatTime;

}
