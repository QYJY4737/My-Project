package cn.congee.api.module.system.systemconfig.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午6:30
 **/
@Data
public class SystemConfigUpdateDTO extends SystemConfigAddDTO {

    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Long id;

}
