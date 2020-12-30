package cn.congee.api.module.business.notice.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午9:30
 **/
@Data
public class NoticeReceiveQueryDTO extends NoticeQueryDTO {

    @ApiModelProperty(value = "当前登录人",hidden = true)
    private Long employeeId;

    @ApiModelProperty(value = "发送状态",hidden = true)
    private Integer sendStatus;

}
