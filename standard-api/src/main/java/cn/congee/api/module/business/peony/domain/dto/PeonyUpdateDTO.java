package cn.congee.api.module.business.peony.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 更新 [ 牡丹花 ]
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午10:01
 **/
@Data
public class PeonyUpdateDTO extends PeonyAddDTO{

    @ApiModelProperty("ID")
    private Long id;

}
