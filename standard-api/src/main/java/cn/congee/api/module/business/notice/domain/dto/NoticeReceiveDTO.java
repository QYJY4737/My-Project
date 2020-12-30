package cn.congee.api.module.business.notice.domain.dto;

import cn.congee.api.common.anno.ApiModelPropertyEnum;
import cn.congee.api.common.constant.JudgeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午9:29
 **/
@Data
public class NoticeReceiveDTO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("消息标题")
    private String title;

    @ApiModelProperty("消息创建人")
    private Long createUser;

    @ApiModelProperty("消息创建人名称")
    private String createUserName;

    @ApiModelProperty("结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date receiveTime;

    @ApiModelPropertyEnum(enumDesc = "读取状态",value = JudgeEnum.class)
    private Integer readStatus;

}
