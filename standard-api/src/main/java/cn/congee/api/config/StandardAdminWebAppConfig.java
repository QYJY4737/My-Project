package cn.congee.api.config;

import cn.congee.api.interceptor.StandardAuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午8:05
 **/
@Configuration
public class StandardAdminWebAppConfig implements WebMvcConfigurer {

    @Autowired
    private StandardAuthenticationInterceptor standardAuthenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(standardAuthenticationInterceptor).addPathPatterns("/**");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/druidMonitor").setViewName("redirect:/druid/index.html");
        registry.addViewController("/swaggerApi").setViewName("redirect:/swagger-ui.html");
    }

}
