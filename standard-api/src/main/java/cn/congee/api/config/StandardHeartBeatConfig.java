package cn.congee.api.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午8:08
 **/
@Data
@Configuration
public class StandardHeartBeatConfig {

    /**
     * 延迟执行时间
     */
    @Value("${heart-beat.delayHandlerTime}")
    private Long delayHandlerTime;

    /**
     * 间隔执行时间
     */
    @Value("${heart-beat.intervalTime}")
    private Long intervalTime;

}
