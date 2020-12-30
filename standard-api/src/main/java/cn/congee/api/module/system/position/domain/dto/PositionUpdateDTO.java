package cn.congee.api.module.system.position.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 岗位
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午3:48
 **/
@Data
public class PositionUpdateDTO extends PositionAddDTO {

    @ApiModelProperty("主键")
    private Long id;

}
