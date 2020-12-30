package cn.congee.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

/**
 * [ 对于枚举类进行swagger注解，与前端的vue-enum相匹配 ]
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午8:18
 **/
@Configuration
@Profile({"dev", "sit", "pre", "prod"})
public class StandardSwaggerApiModelEnumConfig {

    @Bean
    @Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER + 1)
    public StandardSwaggerApiModelEnumConfig swaggerEnum(){
        return new StandardSwaggerApiModelEnumConfig();
    }

}
