package cn.congee.api.common.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * [ 不需要权限验证 ]
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午6:06
 **/
@Retention(RetentionPolicy.RUNTIME)
public @interface NoValidPrivilege {
}
