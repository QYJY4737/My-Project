package cn.congee.api.module.system.position.domain.dto;

import cn.congee.api.common.domain.PageParamDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 岗位
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午3:39
 **/
@Data
public class PositionQueryDTO extends PageParamDTO {

    @ApiModelProperty("岗位名称")
    private String positionName;

}
