package cn.congee.api.module.support.standardreload;


import cn.congee.api.common.reload.abstracts.AbstractStandardReloadCommand4Spring;
import cn.congee.api.common.reload.domain.entity.ReloadItem;
import cn.congee.api.common.reload.domain.entity.SmartReloadResult;
import cn.congee.api.module.support.standardreload.dao.ReloadItemDao;
import cn.congee.api.module.support.standardreload.dao.ReloadResultDao;
import cn.congee.api.module.support.standardreload.domain.entity.ReloadItemEntity;
import cn.congee.api.module.support.standardreload.domain.entity.ReloadResultEntity;
import cn.congee.api.util.StandardBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Smart Reload 业务
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午5:46
 **/
@Component
public class StandardReloadCommand extends AbstractStandardReloadCommand4Spring {

    @Autowired
    private ReloadItemDao reloadItemDao;

    @Autowired
    private ReloadResultDao reloadResultDao;

    /**
     * 读取数据库中SmartReload项
     *
     * @return List<ReloadItem>
     */
    @Override
    public List<ReloadItem> readReloadItem() {
        List<ReloadItemEntity> reloadItemEntityList = reloadItemDao.selectList(null);
        return StandardBeanUtil.copyList(reloadItemEntityList, ReloadItem.class);
    }

    /**
     * 保存reload结果
     *
     * @param smartReloadResult
     */
    @Override
    public void handleReloadResult(SmartReloadResult smartReloadResult) {
        ReloadResultEntity reloadResultEntity = StandardBeanUtil.copy(smartReloadResult, ReloadResultEntity.class);
        reloadResultDao.insert(reloadResultEntity);
    }

}
