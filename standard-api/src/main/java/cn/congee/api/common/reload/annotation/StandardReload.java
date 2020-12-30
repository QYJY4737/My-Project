package cn.congee.api.common.reload.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义 SmartReload 注解
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午4:45
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StandardReload {

    String value();

}
