package cn.congee.api.module.business.notice.domain.dto;

import cn.congee.api.common.domain.PageParamDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午9:28
 **/
@Data
public class NoticeQueryDTO extends PageParamDTO {

    @ApiModelProperty("开始日期")
    private String startDate;

    @ApiModelProperty("结束日期")
    private String endDate;

    @ApiModelProperty("消息标题")
    private String title;

    @ApiModelProperty(value = "是否删除",hidden = true)
    private Integer deleted;

}
