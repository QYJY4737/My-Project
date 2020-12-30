package cn.congee.api.module.business.notice.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午9:33
 **/
@Data
public class NoticeUpdateDTO extends NoticeAddDTO {

    @ApiModelProperty("id")
    private Long id;

}
