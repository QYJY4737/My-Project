package cn.congee.api.module.support.quartz.service;

import cn.congee.api.module.support.quartz.dao.QuartzTaskLogDao;
import cn.congee.api.module.support.quartz.domain.entity.QuartzTaskLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午11:56
 **/
@Service
public class QuartzTaskLogService {

    @Autowired
    private QuartzTaskLogDao quartzTaskLogDao;

    public void save(QuartzTaskLogEntity logEntity){
        quartzTaskLogDao.insert(logEntity);
    }

}
