package cn.congee.api.module.business.email.domain.dto;

import cn.congee.api.common.domain.PageParamDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午8:26
 **/
@Data
public class EmailQueryDTO extends PageParamDTO {

    @ApiModelProperty("开始日期")
    private String startDate;

    @ApiModelProperty("结束日期")
    private String endDate;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("发送状态 0未发送 1已发送")
    private Integer sendStatus;

}
