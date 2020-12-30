package cn.congee.api.common.reload.interfaces;

import cn.congee.api.common.reload.domain.entity.ReloadItem;

/**
 * reload 接口<br>
 * 需要reload的业务实现类
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午4:03
 **/
@FunctionalInterface
public interface StandardReloadable {

    /**
     * reload
     *
     * @param reloadItem
     * @return boolean
     */
    boolean reload(ReloadItem reloadItem);

}
