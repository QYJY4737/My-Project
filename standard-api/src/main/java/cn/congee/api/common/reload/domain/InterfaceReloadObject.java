package cn.congee.api.common.reload.domain;

import cn.congee.api.common.reload.domain.entity.ReloadItem;
import cn.congee.api.common.reload.domain.entity.SmartReloadResult;
import cn.congee.api.common.reload.interfaces.StandardReloadable;

/**
 * Reload 处理程序的实现类
 * 用于处理以接口实现的处理类
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午4:04
 **/
public class InterfaceReloadObject extends AbstractStandardReloadObject {

    private StandardReloadable object;

    public InterfaceReloadObject(StandardReloadable object) {
        super();
        this.object = object;
    }

    @Override
    public SmartReloadResult reload(ReloadItem reloadItem) {
        SmartReloadResult reloadResult = new SmartReloadResult();
        reloadResult.setArgs(reloadItem.getArgs());
        reloadResult.setIdentification(reloadItem.getIdentification());
        reloadResult.setTag(reloadItem.getTag());
        try {
            boolean res = object.reload(reloadItem);
            reloadResult.setResult(res);
        } catch (Throwable e) {
            reloadResult.setException(getStackTrace(e));
        }
        return reloadResult;
    }

}
