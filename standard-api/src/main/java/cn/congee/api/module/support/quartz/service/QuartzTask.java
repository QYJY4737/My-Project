package cn.congee.api.module.support.quartz.service;

import cn.congee.api.common.domain.ITask;
import cn.congee.api.module.support.quartz.constant.QuartzConst;
import cn.congee.api.module.support.quartz.constant.TaskResultEnum;
import cn.congee.api.module.support.quartz.domain.entity.QuartzTaskEntity;
import cn.congee.api.module.support.quartz.domain.entity.QuartzTaskLogEntity;
import cn.congee.api.third.StandardApplicationContext;
import cn.congee.api.util.StandardIPUtil;
import cn.congee.api.util.StandardQuartzUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午11:53
 **/
@Slf4j
public class QuartzTask extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        Object params = context.getMergedJobDataMap().get(QuartzConst.QUARTZ_PARAMS_KEY);
        JobKey jobKey = jobDetail.getKey();

        Long taskId = StandardQuartzUtil.getTaskIdByJobKey(jobKey);
        QuartzTaskService quartzTaskService = (QuartzTaskService) StandardApplicationContext.getBean("quartzTaskService");
        QuartzTaskEntity quartzTaskEntity = quartzTaskService.getByTaskId(taskId);

        QuartzTaskLogService quartzTaskLogService = (QuartzTaskLogService) StandardApplicationContext.getBean("quartzTaskLogService");

        QuartzTaskLogEntity taskLogEntity = new QuartzTaskLogEntity();
        taskLogEntity.setTaskId(taskId);
        taskLogEntity.setIpAddress(StandardIPUtil.getLocalHostIP());
        try {
            taskLogEntity.setTaskName(quartzTaskEntity.getTaskName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String paramsStr = null;
        if (params != null) {
            paramsStr = params.toString();
            taskLogEntity.setTaskParams(paramsStr);
        }
        taskLogEntity.setUpdateTime(new Date());
        taskLogEntity.setCreateTime(new Date());
        //任务开始时间
        long startTime = System.currentTimeMillis();
        try {
            ITask taskClass = (ITask) StandardApplicationContext.getBean(quartzTaskEntity.getTaskBean());
            taskClass.execute(paramsStr);
            taskLogEntity.setProcessStatus(TaskResultEnum.SUCCESS.getStatus());
        } catch (Exception e) {
            log.error("", e);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw, true);
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
            taskLogEntity.setProcessStatus(TaskResultEnum.FAIL.getStatus());
            taskLogEntity.setProcessLog(sw.toString());
        } finally {
            long times = System.currentTimeMillis() - startTime;
            taskLogEntity.setProcessDuration(times);
            quartzTaskLogService.save(taskLogEntity);
        }

    }

}
