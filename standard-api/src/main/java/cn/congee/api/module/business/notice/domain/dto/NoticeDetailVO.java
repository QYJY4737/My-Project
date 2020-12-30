package cn.congee.api.module.business.notice.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午9:27
 **/
@Data
public class NoticeDetailVO extends NoticeVO {

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}
