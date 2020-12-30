package cn.congee.api.common.reload.abstracts;

import cn.congee.api.common.reload.StandardReloadManager;
import cn.congee.api.common.reload.domain.entity.ReloadItem;
import cn.congee.api.common.reload.interfaces.StandardReloadCommandInterface;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 检测是否 Reload 的类
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午5:49
 **/
public abstract class AbstractStandardReloadCommand4Spring implements StandardReloadCommandInterface {

    /**
     * 当前ReloadItem的存储器
     */
    protected ConcurrentHashMap<String, String> currentTags = new ConcurrentHashMap<>();

    /**
     * Reload的执行类
     */
    @Autowired
    protected StandardReloadManager reloadManager;

    //    @PostConstruct
    public void init() {
        List<ReloadItem> readTagStatesFromDb = readReloadItem();
        if (readTagStatesFromDb != null) {
            for (ReloadItem reloadItem : readTagStatesFromDb) {
                String tag = reloadItem.getTag();
                String tagChangeIdentifier = reloadItem.getIdentification();
                this.currentTags.put(tag, tagChangeIdentifier);
            }
        }
    }

    /**
     * 任务：
     * 读取数据库中 ReloadItem 数据
     * 校验是否发生变化
     * 执行重加载动作
     */
    @Override
    public void doTask() {
        // 获取数据库数据
        List<ReloadItem> readTagStatesFromDb = readReloadItem();
        String tag;
        String tagIdentifier;
        String preTagChangeIdentifier;
        for (ReloadItem reloadItem : readTagStatesFromDb) {
            tag = reloadItem.getTag();
            tagIdentifier = reloadItem.getIdentification();
            preTagChangeIdentifier = currentTags.get(tag);
            // 数据不一致
            if (preTagChangeIdentifier == null || ! preTagChangeIdentifier.equals(tagIdentifier)) {
                // 更新map数据
                currentTags.put(tag, tagIdentifier);
                // 执行重新加载此项的动作
                handleReloadResult(this.reloadManager.doReload(reloadItem));
            }
        }
    }

}
