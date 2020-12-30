package cn.congee.api.module.business.log.userloginlog.domain;

import cn.congee.api.common.domain.PageParamDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * [ 用户登录日志 ]
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午6:49
 **/
@Data
public class UserLoginLogQueryDTO extends PageParamDTO {

    @ApiModelProperty("开始日期")
    private String startDate;

    @ApiModelProperty("结束日期")
    private String endDate;

    @ApiModelProperty("用户名")
    private String userName;

}
