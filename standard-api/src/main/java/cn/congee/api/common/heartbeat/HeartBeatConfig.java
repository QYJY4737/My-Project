package cn.congee.api.common.heartbeat;

import lombok.Builder;
import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午11:30
 **/
@Data
@Builder
public class HeartBeatConfig {

    /**
     * 延迟执行时间
     */
    private Long delayHandlerTime;

    /**
     * 间隔执行时间
     */
    private Long intervalTime;

}
