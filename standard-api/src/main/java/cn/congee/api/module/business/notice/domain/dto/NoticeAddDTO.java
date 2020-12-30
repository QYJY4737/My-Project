package cn.congee.api.module.business.notice.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午9:25
 **/
@Data
public class NoticeAddDTO {

    @ApiModelProperty("消息标题")
    @Length(max = 200)
    private String title;

    @ApiModelProperty("消息内容")
    @Length(max = 5000)
    private String content;

}
