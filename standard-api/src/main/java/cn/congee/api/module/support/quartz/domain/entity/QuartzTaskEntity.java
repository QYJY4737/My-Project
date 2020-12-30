package cn.congee.api.module.support.quartz.domain.entity;

import cn.congee.api.common.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午11:45
 **/
@Data
@TableName(value = "t_quartz_task")
public class QuartzTaskEntity extends BaseEntity {

    /**
     * 任务名称参数
     */
    private String taskName;

    /**
     * 任务类
     */
    private String taskBean;

    /**
     * 任务参数
     */
    private String taskParams;

    /**
     * cron
     */
    private String taskCron;

    /**
     * 任务状态
     */
    private Integer taskStatus;

    /**
     * 备注
     */
    private String remark;

}
