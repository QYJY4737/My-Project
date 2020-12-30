package cn.congee.api.config;

import cn.congee.api.constant.SystemEnvironmentEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 是否是正式环境
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午8:21
 **/
public class SystemEnvironmentCondition implements Condition {

    @Value("${spring.profiles.active}")
    private String systemEnvironment;

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        return ! SystemEnvironmentEnum.PROD.equalsValue(systemEnvironment);
    }

}
