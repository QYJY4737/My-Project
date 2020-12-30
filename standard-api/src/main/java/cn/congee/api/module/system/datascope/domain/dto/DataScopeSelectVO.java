package cn.congee.api.module.system.datascope.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午2:11
 **/
@Data
public class DataScopeSelectVO {

    @ApiModelProperty("数据范围id")
    private Integer dataScopeType;

    @ApiModelProperty("可见范围")
    private Integer viewType;

}
