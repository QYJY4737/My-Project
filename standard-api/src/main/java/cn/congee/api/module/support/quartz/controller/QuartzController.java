package cn.congee.api.module.support.quartz.controller;

import cn.congee.api.common.anno.NoValidPrivilege;
import cn.congee.api.common.anno.OperateLog;
import cn.congee.api.common.domain.PageResultDTO;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.constant.SwaggerTagConst;
import cn.congee.api.module.support.quartz.domain.dto.*;
import cn.congee.api.module.support.quartz.service.QuartzTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午12:02
 **/
@OperateLog
@RestController
@RequestMapping(value = "/quartz")
@Api(tags = {SwaggerTagConst.Admin.MANAGER_TASK_SCHEDULER})
public class QuartzController {

    @Autowired
    private QuartzTaskService quartzTaskService;


    @PostMapping("/task/query")
    @ApiOperation(value = "查询任务")
    @NoValidPrivilege
    public ResponseDTO<PageResultDTO<QuartzTaskVO>> query(@RequestBody @Valid QuartzQueryDTO queryDTO){
        return quartzTaskService.query(queryDTO);
    }


    @PostMapping("/task/queryLog")
    @ApiOperation(value = "查询任务运行日志")
    @NoValidPrivilege
    public ResponseDTO<PageResultDTO<QuartzTaskLogVO>> queryLog(@RequestBody @Valid QuartzLogQueryDTO queryDTO){
        return quartzTaskService.queryLog(queryDTO);
    }

    @PostMapping("/task/saveOrUpdate")
    @ApiOperation(value = "新建更新任务")
    public ResponseDTO<String> saveOrUpdateTask(@RequestBody @Valid QuartzTaskDTO quartzTaskDTO)throws Exception{
        return quartzTaskService.saveOrUpdateTask(quartzTaskDTO);
    }

    @GetMapping("/task/run/{taskId}")
    @ApiOperation(value = "立即运行某个任务")
    public ResponseDTO<String> runTask(@PathVariable("taskId") Long taskId)throws Exception{
        return quartzTaskService.runTask(taskId);
    }

    @GetMapping("/task/pause/{taskId}")
    @ApiOperation(value = "暂停某个任务")
    public ResponseDTO<String> pauseTask(@PathVariable("taskId")Long taskId)throws Exception{
        return quartzTaskService.pauseTask(taskId);
    }

    @GetMapping("/task/resume/{taskId}")
    @ApiOperation(value = "恢复某个任务")
    public ResponseDTO<String> resumeTask(@PathVariable("taskId")Long taskId)throws Exception{
        return quartzTaskService.resumeTask(taskId);
    }

    @GetMapping("/task/delete/{taskId}")
    @ApiOperation(value = "删除某个任务")
    public ResponseDTO<String> deleteTask(@PathVariable("taskId")Long taskId)throws Exception{
        return quartzTaskService.deleteTask(taskId);
    }

}
