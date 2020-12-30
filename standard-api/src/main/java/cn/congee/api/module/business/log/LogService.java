package cn.congee.api.module.business.log;

import cn.congee.api.module.business.log.orderoperatelog.OrderOperateLogDao;
import cn.congee.api.module.business.log.orderoperatelog.domain.entity.OrderOperateLogEntity;
import cn.congee.api.module.business.log.userloginlog.UserLoginLogDao;
import cn.congee.api.module.business.log.userloginlog.domain.UserLoginLogEntity;
import cn.congee.api.module.business.log.useroperatelog.UserOperateLogDao;
import cn.congee.api.module.business.log.useroperatelog.domain.UserOperateLogEntity;
import cn.congee.api.util.StandardThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午6:46
 **/
@Slf4j
@Service
public class LogService {

    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private UserLoginLogDao userLoginLogDao;

    @Autowired
    private OrderOperateLogDao orderOperateLogDao;

    @Autowired
    private UserOperateLogDao userOperateLogDao;

    @PostConstruct
    void init() {
        if (threadPoolExecutor == null) {
            threadPoolExecutor = new ThreadPoolExecutor(1, 1, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(2000), StandardThreadFactory.create("LogAspect"));
        }
    }

    @PreDestroy
    void destroy() {
        if (threadPoolExecutor != null) {
            threadPoolExecutor.shutdown();
            threadPoolExecutor = null;
        }
    }

    public void addLog(Object object) {
        try {
            if (object instanceof UserLoginLogEntity) {
                threadPoolExecutor.execute(() -> userLoginLogDao.insert((UserLoginLogEntity) object));
            }
            if (object instanceof OrderOperateLogEntity) {
                threadPoolExecutor.execute(() -> orderOperateLogDao.insert((OrderOperateLogEntity) object));
            }
            if (object instanceof UserOperateLogEntity) {
                threadPoolExecutor.execute(() -> userOperateLogDao.insert((UserOperateLogEntity) object));
            }
        } catch (Throwable e) {
            log.error("userLogAfterAdvice:{}", e);
        }
    }

}
