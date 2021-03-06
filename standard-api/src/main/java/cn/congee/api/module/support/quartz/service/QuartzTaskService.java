package cn.congee.api.module.support.quartz.service;

import cn.congee.api.common.constant.ResponseCodeConst;
import cn.congee.api.common.domain.PageResultDTO;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.module.support.quartz.constant.QuartzConst;
import cn.congee.api.module.support.quartz.constant.TaskStatusEnum;
import cn.congee.api.module.support.quartz.dao.QuartzTaskDao;
import cn.congee.api.module.support.quartz.dao.QuartzTaskLogDao;
import cn.congee.api.module.support.quartz.domain.dto.*;
import cn.congee.api.module.support.quartz.domain.entity.QuartzTaskEntity;
import cn.congee.api.module.support.quartz.domain.vo.QuartzTaskLogVO;
import cn.congee.api.module.support.quartz.domain.vo.QuartzTaskVO;
import cn.congee.api.third.StandardApplicationContext;
import cn.congee.api.util.StandardBeanUtil;
import cn.congee.api.util.StandardPageUtil;
import cn.congee.api.util.StandardQuartzUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午11:56
 **/
@Slf4j
@Service
public class QuartzTaskService {

    @Autowired
    private QuartzTaskDao quartzTaskDao;

    @Autowired
    private QuartzTaskLogDao quartzTaskLogDao;

    @Autowired
    private Scheduler scheduler;

    /**
     * 查询列表
     *
     * @param queryDTO
     * @return
     */
    public ResponseDTO<PageResultDTO<QuartzTaskVO>> query(QuartzQueryDTO queryDTO) {
        log.info("查询列表接口入参为queryDTO=[{}]", JSON.toJSONString(queryDTO));
        Page pageParam = StandardPageUtil.convert2QueryPage(queryDTO);
        List<QuartzTaskVO> taskList = quartzTaskDao.queryList(pageParam, queryDTO);
        pageParam.setRecords(taskList);
        PageResultDTO pageResultDTO = StandardPageUtil.convert2PageResult(pageParam);
        log.info("查询列表接口出参为pageResultDTO=[{}]", JSON.toJSONString(pageResultDTO));
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 查询任务运行日志
     *
     * @param queryDTO
     * @return
     */
    public ResponseDTO<PageResultDTO<QuartzTaskLogVO>> queryLog(QuartzLogQueryDTO queryDTO) {
        log.info("查询任务运行日志接口入参为queryDTO=[{}]", JSON.toJSONString(queryDTO));
        Page pageParam = StandardPageUtil.convert2QueryPage(queryDTO);
        List<QuartzTaskLogVO> taskList = quartzTaskLogDao.queryList(pageParam, queryDTO);
        pageParam.setRecords(taskList);
        PageResultDTO pageResultDTO = StandardPageUtil.convert2PageResult(pageParam);
        log.info("查询任务运行日志接口出参为pageResultDTO=[{}]", JSON.toJSONString(pageResultDTO));
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 保存或更新
     *
     * @param quartzTaskDTO
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Throwable.class)
    public ResponseDTO<String> saveOrUpdateTask(QuartzTaskDTO quartzTaskDTO) throws Exception {
        log.info("新建更新任务接口入参为quartzTaskDTO=[{}]", JSON.toJSONString(quartzTaskDTO));
        ResponseDTO baseValid = this.baseValid(quartzTaskDTO);
        if (!baseValid.isSuccess()) {
            return baseValid;
        }
        Long taskId = quartzTaskDTO.getId();
        if (taskId == null) {
            return this.saveTask(quartzTaskDTO);
        } else {
            return this.updateTask(quartzTaskDTO);
        }
    }

    /**
     * baseValid
     *
     * @param quartzTaskDTO
     * @return
     */
    private ResponseDTO<String> baseValid(QuartzTaskDTO quartzTaskDTO) {
        Object taskBean = null;
        try {
            taskBean = StandardApplicationContext.getBean(quartzTaskDTO.getTaskBean());
        } catch (Exception e) {
            log.error("taskBean 不存在{}", e);
        }
        if (taskBean == null) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, "taskBean 不存在");
        }
        if (!CronExpression.isValidExpression(quartzTaskDTO.getTaskCron())) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, "请传入正确的正则表达式");
        }
        return ResponseDTO.succ();
    }

    /**
     * 保存任务
     *
     * @param quartzTaskDTO
     * @return
     * @throws Exception
     */
    private ResponseDTO<String> saveTask(QuartzTaskDTO quartzTaskDTO) throws Exception {
        QuartzTaskEntity taskEntity = StandardBeanUtil.copy(quartzTaskDTO, QuartzTaskEntity.class);
        taskEntity.setTaskStatus(TaskStatusEnum.NORMAL.getStatus());
        taskEntity.setUpdateTime(new Date());
        taskEntity.setCreateTime(new Date());
        quartzTaskDao.insert(taskEntity);
        this.createQuartzTask(scheduler, taskEntity);
        return ResponseDTO.succ();
    }

    /**
     * 更新任务
     *
     * @param quartzTaskDTO
     * @return
     * @throws Exception
     */
    private ResponseDTO<String> updateTask(QuartzTaskDTO quartzTaskDTO) throws Exception {
        QuartzTaskEntity updateEntity = quartzTaskDao.selectById(quartzTaskDTO.getId());
        if (updateEntity == null) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, "task不存在");
        }
        QuartzTaskEntity taskEntity = StandardBeanUtil.copy(quartzTaskDTO, QuartzTaskEntity.class);
        //任务状态不能更新
        taskEntity.setTaskStatus(updateEntity.getTaskStatus());
        taskEntity.setUpdateTime(new Date());
        quartzTaskDao.updateById(taskEntity);
        if(this.checkExist(taskEntity.getId())){
            this.updateQuartzTask(scheduler, taskEntity);
        }else{
            this.createQuartzTask(scheduler,taskEntity);
        }

        return ResponseDTO.succ();
    }

    /**
     * 立即运行某个任务
     *
     * @param taskId
     * @return
     * @throws Exception
     */
    public ResponseDTO<String> runTask(Long taskId) throws Exception {
        log.info("立即运行某个任务接口入参为taskId=[{}]", taskId);
        QuartzTaskEntity quartzTaskEntity = quartzTaskDao.selectById(taskId);
        if (quartzTaskEntity == null) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, "task不存在");
        }
        this.runQuartzTask(scheduler, quartzTaskEntity);
        return ResponseDTO.succ();
    }

    /**
     * 暂停运行某个任务
     *
     * @param taskId
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Throwable.class)
    public ResponseDTO<String> pauseTask(Long taskId) throws Exception {
        log.info("暂停运行某个任务接口入参为taskId=[{}]", taskId);
        QuartzTaskEntity quartzTaskEntity = quartzTaskDao.selectById(taskId);
        if (quartzTaskEntity == null) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, "task不存在");
        }
        quartzTaskEntity.setTaskStatus(TaskStatusEnum.PAUSE.getStatus());
        quartzTaskDao.updateById(quartzTaskEntity);
        this.pauseQuartzTask(scheduler, quartzTaskEntity);
        return ResponseDTO.succ();
    }

    /**
     * 恢复运行某个任务
     *
     * @param taskId
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Throwable.class)
    public ResponseDTO<String> resumeTask(Long taskId) throws Exception {
        log.info("恢复运行某个任务接口入参为taskId=[{}]", taskId);
        QuartzTaskEntity quartzTaskEntity = quartzTaskDao.selectById(taskId);
        if (quartzTaskEntity == null) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, "task不存在");
        }
        quartzTaskEntity.setTaskStatus(TaskStatusEnum.NORMAL.getStatus());
        quartzTaskDao.updateById(quartzTaskEntity);
        this.resumeQuartzTask(scheduler, quartzTaskEntity);
        return ResponseDTO.succ();
    }

    /**
     * 删除某个任务
     *
     * @param taskId
     * @return
     * @throws Exception
     */
    public ResponseDTO<String> deleteTask(Long taskId) throws Exception {
        log.info("删除某个任务接口入参为taskId=[{}]", taskId);
        QuartzTaskEntity quartzTaskEntity = quartzTaskDao.selectById(taskId);
        if (quartzTaskEntity == null) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, "task不存在");
        }
        quartzTaskDao.deleteById(taskId);
        this.deleteQuartzTask(scheduler, taskId);
        return ResponseDTO.succ();
    }

    /**
     * 通过任务Id 获取任务实体
     *
     * @param taskId
     * @return
     */
    public QuartzTaskEntity getByTaskId(Long taskId) {
        return quartzTaskDao.selectById(taskId);
    }

    /**
     * 创建任务
     *
     * @param scheduler
     * @param taskEntity
     * @throws Exception
     */
    public void createQuartzTask(Scheduler scheduler, QuartzTaskEntity taskEntity) throws Exception {
        JobKey jobKey = StandardQuartzUtil.getJobKey(taskEntity.getId());
        JobDetail jobDetail = JobBuilder.newJob(QuartzTask.class).withIdentity(jobKey).build();

        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(taskEntity.getTaskCron()).withMisfireHandlingInstructionDoNothing();

        TriggerKey triggerKey = StandardQuartzUtil.getTriggerKey(Long.valueOf(taskEntity.getId()));
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

        jobDetail.getJobDataMap().put(QuartzConst.QUARTZ_PARAMS_KEY, taskEntity.getTaskParams());
        scheduler.scheduleJob(jobDetail, trigger);
        //如果任务是暂停状态，则暂停任务
        if (TaskStatusEnum.PAUSE.getStatus().equals(taskEntity.getTaskStatus())) {
            this.pauseQuartzTask(scheduler, taskEntity);
        }
    }

    /**
     * 更新任务
     *
     * @param scheduler
     * @param taskEntity
     * @throws Exception
     */
    private void updateQuartzTask(Scheduler scheduler, QuartzTaskEntity taskEntity) throws Exception {
        TriggerKey triggerKey = StandardQuartzUtil.getTriggerKey(Long.valueOf(taskEntity.getId()));

        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(taskEntity.getTaskCron()).withMisfireHandlingInstructionDoNothing();

        CronTrigger trigger = this.getCronTrigger(scheduler, Long.valueOf(taskEntity.getId()));

        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

        trigger.getJobDataMap().put(QuartzConst.QUARTZ_PARAMS_KEY, taskEntity.getTaskParams());

        scheduler.rescheduleJob(triggerKey, trigger);
        //如果更新之前任务是暂停状态，此时再次暂停任务
        if (TaskStatusEnum.PAUSE.getStatus().equals(taskEntity.getTaskStatus())) {
            this.pauseQuartzTask(scheduler, taskEntity);
        }
    }

    /**
     * 获取触发器key
     *
     * @param scheduler
     * @param taskId
     * @return
     * @throws Exception
     */
    private CronTrigger getCronTrigger(Scheduler scheduler, Long taskId) throws Exception {
        TriggerKey triggerKey = StandardQuartzUtil.getTriggerKey(taskId);
        return (CronTrigger) scheduler.getTrigger(triggerKey);
    }

    /**
     * 立即运行
     *
     * @param scheduler
     * @param taskEntity
     * @throws Exception
     */
    private void runQuartzTask(Scheduler scheduler, QuartzTaskEntity taskEntity) throws Exception {
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(QuartzConst.QUARTZ_PARAMS_KEY, taskEntity.getTaskParams());
        JobKey jobKey = StandardQuartzUtil.getJobKey(taskEntity.getId());
        if(!scheduler.checkExists(jobKey)){
            this.createQuartzTask(scheduler,taskEntity);
            scheduler.triggerJob(jobKey, dataMap);
            return;
        }
        scheduler.triggerJob(jobKey, dataMap);
    }

    /**
     * 暂停任务
     *
     * @param scheduler
     * @param quartzTaskEntity
     * @throws Exception
     */
    private void pauseQuartzTask(Scheduler scheduler, QuartzTaskEntity quartzTaskEntity) throws Exception {
        JobKey jobKey = StandardQuartzUtil.getJobKey(quartzTaskEntity.getId());
        if(!scheduler.checkExists(jobKey)){
            this.createQuartzTask(scheduler,quartzTaskEntity);
            scheduler.pauseJob(jobKey);
            return;
        }
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复任务
     *
     * @param scheduler
     * @param quartzTaskEntity
     * @throws Exception
     */
    private void resumeQuartzTask(Scheduler scheduler, QuartzTaskEntity quartzTaskEntity) throws Exception {
        JobKey jobKey = StandardQuartzUtil.getJobKey(quartzTaskEntity.getId());
        if(!scheduler.checkExists(jobKey)){
            this.createQuartzTask(scheduler,quartzTaskEntity);
            return;
        }
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除任务
     *
     * @param scheduler
     * @param taskId
     * @throws Exception
     */
    private void deleteQuartzTask(Scheduler scheduler, Long taskId) throws Exception {
        JobKey jobKey = StandardQuartzUtil.getJobKey(taskId);
        if(!scheduler.checkExists(jobKey)){
            return;
        }
        scheduler.deleteJob(jobKey);
    }

    /**
     * checkExist
     *
     * @param taskId
     * @return
     * @throws Exception
     */
    private Boolean checkExist(Long taskId) throws Exception{
        JobKey jobKey = StandardQuartzUtil.getJobKey(taskId);
        return scheduler.checkExists(jobKey);
    }

}
