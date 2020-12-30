package cn.congee.api.module.system.position.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 岗位关系
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午3:40
 **/
@Data
public class PositionRelationQueryDTO {

    @ApiModelProperty("岗位ID")
    private Long positionId;

    @ApiModelProperty("员工ID")
    private Long employeeId;

}
