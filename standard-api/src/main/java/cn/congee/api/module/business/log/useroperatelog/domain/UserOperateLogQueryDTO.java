package cn.congee.api.module.business.log.useroperatelog.domain;

import cn.congee.api.common.domain.PageParamDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午6:55
 **/
@Data
public class UserOperateLogQueryDTO extends PageParamDTO {

    @ApiModelProperty("开始日期")
    private String startDate;

    @ApiModelProperty("结束日期")
    private String endDate;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("请求结果 0失败 1成功")
    private Integer resultFlag;

}
