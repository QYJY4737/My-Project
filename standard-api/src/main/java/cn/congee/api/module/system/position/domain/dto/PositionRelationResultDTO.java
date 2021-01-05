package cn.congee.api.module.system.position.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 岗位关联关系
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午3:17
 **/
@Data
public class PositionRelationResultDTO {

    @ApiModelProperty("岗位ID")
    private Long positionId;

    @ApiModelProperty("员工ID")
    private Long employeeId;

    @ApiModelProperty("岗位名称")
    private String positionName;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("创建时间")
    private Date createTime;

}
