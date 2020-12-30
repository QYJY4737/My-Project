package cn.congee.api.module.support.quartz.task.test;

import cn.congee.api.common.domain.ITask;
import cn.congee.api.util.StandardDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午12:28
 **/
@Slf4j
@Component("exampleTask")
public class Example implements ITask {

    @Override
    public void execute(String paramJson) throws Exception {
        log.warn("{}-今天搬了{}块砖,paramJson:{}", StandardDateUtil.formatYMDHMS(new Date()),System.currentTimeMillis(),paramJson );
    }

}
