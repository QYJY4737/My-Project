package cn.congee.api.common.reload.interfaces;

import cn.congee.api.common.reload.domain.entity.ReloadItem;
import cn.congee.api.common.reload.domain.entity.SmartReloadResult;

import java.util.List;

/**
 * 检测是否 Reload 的类
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午4:02
 **/
public interface StandardReloadCommandInterface {

    /**
     * 任务：
     * 读取数据库中 ReloadItem 数据
     * 校验是否发生变化
     * 执行重加载动作
     */
    void doTask();

    /**
     * 该方法返回一个List<ReloadItem></>:<br>
     * ReloadItem对象的tagIdentify为：该tag的 状态（状态其实就是个字符串，如果该字符串跟上次有变化则进行reload操作）<br>
     * ReloadItem对象的args为： reload操作需要的参数<br><br>
     *
     * @return List<ReloadItem>
     */
    List<ReloadItem> readReloadItem();

    /**
     * 处理Reload结果
     *
     * @param reloadResult
     */
    void handleReloadResult(SmartReloadResult reloadResult);

}
