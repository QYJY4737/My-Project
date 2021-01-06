package cn.congee.api.module.support.codegenerator.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午10:34
 **/
@Data
@Builder
public class QueryFieldVO {

    @ApiModelProperty("字段名称")
    private String fieldName;

    @ApiModelProperty("列名称")
    private String columnName;

    @ApiModelProperty("列描述")
    private String columnDesc;

    @ApiModelProperty("字段类型")
    private String fieldType;

    @ApiModelProperty("操作员")
    private String sqlOperate;

}
