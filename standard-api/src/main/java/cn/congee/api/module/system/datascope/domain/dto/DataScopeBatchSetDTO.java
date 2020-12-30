package cn.congee.api.module.system.datascope.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午2:08
 **/
@Data
public class DataScopeBatchSetDTO {

    @ApiModelProperty("数据范围类型")
    @NotNull(message = "数据范围类型不能为空")
    private Integer dataScopeType;

    @ApiModelProperty("可见范围")
    @NotNull(message = "可见范围不能为空")
    private Integer viewType;

}
