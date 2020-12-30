package cn.congee.api.module.business.peony.domain.dto;

import cn.congee.api.common.domain.PageParamDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * [ 牡丹花 ]
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午10:01
 **/
@Data
public class PeonyQueryDTO extends PageParamDTO {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("品种")
    private String kind;

    @ApiModelProperty("名字")
    private String name;

    @ApiModelProperty("颜色")
    private String color;

    @ApiModelProperty("创建时间-开始")
    private Date createTimeBegin;

    @ApiModelProperty("创建时间-截止")
    private Date createTimeEnd;

    @ApiModelProperty("上次更新时间-开始")
    private Date updateTimeBegin;

    @ApiModelProperty("上次更新创建时间-开始")
    private Date updateTimeEnd;

}
