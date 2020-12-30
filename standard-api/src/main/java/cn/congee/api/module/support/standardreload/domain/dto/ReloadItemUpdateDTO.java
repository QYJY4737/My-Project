package cn.congee.api.module.support.standardreload.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午6:02
 **/
@Data
public class ReloadItemUpdateDTO {

    @ApiModelProperty("标签")
    @NotBlank(message = "标签不能为空")
    private String tag;

    @ApiModelProperty("状态标识")
    @NotBlank(message = "状态标识不能为空")
    private String identification;

    @ApiModelProperty("reload时传入的参数，可以为空")
    private String args;

}
