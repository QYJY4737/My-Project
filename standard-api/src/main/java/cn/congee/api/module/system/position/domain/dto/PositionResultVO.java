package cn.congee.api.module.system.position.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午3:43
 **/
@Data
public class PositionResultVO {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("岗位名称")
    private String positionName;

    @ApiModelProperty("岗位描述")
    private String remark;

}
