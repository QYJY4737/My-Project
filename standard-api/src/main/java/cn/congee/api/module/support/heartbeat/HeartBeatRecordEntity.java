package cn.congee.api.module.support.heartbeat;

import cn.congee.api.common.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 心跳记录日志
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午11:23
 **/
@Data
@TableName(value = "t_heart_beat_record")
public class HeartBeatRecordEntity extends BaseEntity implements Serializable {

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
