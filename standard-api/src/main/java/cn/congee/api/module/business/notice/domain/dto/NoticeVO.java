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
 * Time: 上午9:26
 **/
@Data
public class NoticeVO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("消息标题")
    private String title;


    @ApiModelProperty("消息创建人")
    private Long createUser;

    @ApiModelPropertyEnum(enumDesc = "发送状态",value = JudgeEnum.class)
    private Integer sendStatus;

    @ApiModelProperty("消息创建人名称")
    private String createUserName;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
