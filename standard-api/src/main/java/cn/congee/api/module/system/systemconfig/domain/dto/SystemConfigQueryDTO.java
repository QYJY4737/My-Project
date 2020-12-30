package cn.congee.api.module.system.systemconfig.domain.dto;

import cn.congee.api.common.domain.PageParamDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午3:55
 **/
@Data
public class SystemConfigQueryDTO extends PageParamDTO {

    @ApiModelProperty("参数KEY")
    private String key;

    @ApiModelProperty("参数类别")
    private String configGroup;

}
