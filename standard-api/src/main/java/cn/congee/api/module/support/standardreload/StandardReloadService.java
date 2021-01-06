package cn.congee.api.module.support.standardreload;

import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.common.reload.StandardReloadManager;
import cn.congee.api.common.constant.ResponseCodeConst;
import cn.congee.api.module.support.standardreload.dao.ReloadItemDao;
import cn.congee.api.module.support.standardreload.dao.ReloadResultDao;
import cn.congee.api.module.support.standardreload.domain.dto.ReloadItemUpdateDTO;
import cn.congee.api.module.support.standardreload.domain.vo.ReloadItemVO;
import cn.congee.api.module.support.standardreload.domain.vo.ReloadResultVO;
import cn.congee.api.module.support.standardreload.domain.entity.ReloadItemEntity;
import cn.congee.api.module.support.standardreload.domain.entity.ReloadResultEntity;
import cn.congee.api.util.StandardBeanUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Smart initDefines 业务
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午3:57
 **/
@Slf4j
@Service
public class StandardReloadService {

    @Autowired
    private StandardReloadManager standardReloadManager;

    @Autowired
    private StandardReloadCommand smartReloadCommand;

    @Autowired
    private ReloadItemDao reloadItemDao;

    @Autowired
    private ReloadResultDao reloadResultDao;

    @Value("${standard-reload.time-interval}")
    private Long timeInterval;

    @PostConstruct
    public void init() {
        standardReloadManager.addCommand(smartReloadCommand, 10, timeInterval, TimeUnit.SECONDS);
    }

    /**
     * 注册到SmartReload里
     */
    public void register(Object reload) {
        standardReloadManager.register(reload);
    }

    /**
     * 获取全部Standard-reload项
     *
     * @return
     */
    public ResponseDTO<List<ReloadItemVO>> listAllReloadItem() {
        log.info("获取全部Standard-reload项接口入参为空");
        List<ReloadItemEntity> reloadItemEntityList = reloadItemDao.selectList(null);
        List<ReloadItemVO> reloadItemDTOList = StandardBeanUtil.copyList(reloadItemEntityList, ReloadItemVO.class);
        log.info("获取全部Standard-reload项接口出参为reloadItemDTOList=[{}]", JSON.toJSONString(reloadItemDTOList));
        return ResponseDTO.succData(reloadItemDTOList);
    }

    /**
     * 根据 tag
     * 获取所有 initDefines 运行结果
     *
     * @return ResponseDTO
     */
    public ResponseDTO<List<ReloadResultVO>> listReloadItemResult(String tag) {
        log.info("获取reload-result项接口入参为tag=[{}]", tag);
        ReloadResultEntity query = new ReloadResultEntity();
        query.setTag(tag);
        List<ReloadResultEntity> reloadResultEntityList = reloadResultDao.query(tag);
        List<ReloadResultVO> reloadResultDTOList = StandardBeanUtil.copyList(reloadResultEntityList, ReloadResultVO.class);
        log.info("获取reload-result项接口出参为reloadResultDTOList=[{}]", JSON.toJSONString(reloadResultDTOList));
        return ResponseDTO.succData(reloadResultDTOList);
    }

    /**
     * 通过标签更新标识符
     *
     * @param updateDTO
     * @return
     */
    public ResponseDTO<String> updateByTag(ReloadItemUpdateDTO updateDTO) {
        log.info("通过tag更新标识接口入参为updateDTO=[{}]", JSON.toJSONString(updateDTO));
        ReloadItemEntity entity = new ReloadItemEntity();
        entity.setTag(updateDTO.getTag());
        ReloadItemEntity reloadItemEntity = reloadItemDao.selectById(entity.getTag());
        if (null == reloadItemEntity) {
            return ResponseDTO.wrap(ResponseCodeConst.NOT_EXISTS);
        }
        reloadItemEntity.setIdentification(updateDTO.getIdentification());
        reloadItemEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        reloadItemEntity.setArgs(updateDTO.getArgs());
        reloadItemDao.updateById(reloadItemEntity);
        return ResponseDTO.succ();
    }

}
