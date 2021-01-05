package cn.congee.api.module.system.position.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 岗位
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午3:48
 **/
@Data
public class PositionAddDTO {

    @ApiModelProperty("岗位名称")
    @NotBlank(message = "岗位名称不能为空")
    private String positionName;

    @ApiModelProperty("岗位描述")
    private String remark;

}
