package cn.congee.api.module.support.quartz.domain.dto;

import cn.congee.api.common.domain.PageParamDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午11:50
 **/
@Data
public class QuartzLogQueryDTO extends PageParamDTO {

    @ApiModelProperty(value = "任务Id(不能为空)")
    @NotNull(message = "任务Id不能为空")
    private Integer taskId;

}
