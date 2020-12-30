package cn.congee.api.config;

import cn.congee.api.common.reload.StandardReloadManager;
import cn.congee.api.common.reload.interfaces.StandardReloadThreadLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午8:15
 **/
@Slf4j
@Configuration
public class StandardReloadConfig {

    @Value("${smart-reload.thread-count}")
    private Integer threadCount;

    @Bean
    public StandardReloadManager initSmartReloadManager() {
        /**
         * 创建 Reload Manager 调度器
         */
        StandardReloadManager standardReloadManager = new StandardReloadManager(new StandardReloadThreadLogger() {
            @Override
            public void error(String string) {
                log.error(string);
            }

            @Override
            public void error(String string, Throwable e) {
                log.error(string, e);
            }
        }, threadCount);
        return standardReloadManager;
    }

}
