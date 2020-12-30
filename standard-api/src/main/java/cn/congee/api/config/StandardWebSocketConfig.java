package cn.congee.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * [ WebSocketConfig ]
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午8:21
 **/
@Configuration
public class StandardWebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
