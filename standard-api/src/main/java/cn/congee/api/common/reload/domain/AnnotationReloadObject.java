package cn.congee.api.common.reload.domain;

import cn.congee.api.common.reload.annotation.StandardReload;
import cn.congee.api.common.reload.domain.entity.ReloadItem;
import cn.congee.api.common.reload.domain.entity.SmartReloadResult;

import java.lang.reflect.Method;

/**
 * Reload 处理程序的实现类
 * 用于包装以注解 SmartReload 实现的处理类
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午4:46
 **/
public class AnnotationReloadObject extends AbstractStandardReloadObject {

    private Object reloadObject;

    private Method method;

    public AnnotationReloadObject(Object reloadObject, Method method) {
        super();
        this.reloadObject = reloadObject;
        this.method = method;
        this.method.setAccessible(true);
    }

    @Override
    public SmartReloadResult reload(ReloadItem reloadItem) {
        SmartReloadResult result = new SmartReloadResult();
        String tag = method.getAnnotation(StandardReload.class).value();
        result.setTag(tag);
        result.setArgs(reloadItem.getArgs());
        result.setIdentification(reloadItem.getIdentification());
        try {
            Object invoke = method.invoke(reloadObject, reloadItem.getArgs());
            result.setResult((Boolean) invoke);
        } catch (Throwable e) {
            result.setException(getStackTrace(e));
        }
        return result;
    }

}
