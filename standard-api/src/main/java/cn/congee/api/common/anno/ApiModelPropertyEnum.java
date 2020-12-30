package cn.congee.api.common.anno;

import cn.congee.api.common.domain.BaseEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午9:26
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiModelPropertyEnum {

    /**
     * 枚举类对象
     *
     * @return
     */
    Class<? extends BaseEnum> value();

    String example() default "";

    /**
     * 是否隐藏
     *
     * @return
     */
    boolean hidden() default false;

    /**
     * 是否必须
     *
     * @return
     */
    boolean required() default true;

    String dataType() default "";

    String enumDesc() default "";

}
