package cn.congee.api.common.anno;

import java.lang.annotation.*;

/**
 * [ 用户操作日志 ]
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午6:59
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface OperateLog {
}
