package cn.congee.api.module.support.codegenerator.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午10:33
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnVO {

    @ApiModelProperty("列名称")
    private String columnName;

    @ApiModelProperty("列类型")
    private String columnType;

    @ApiModelProperty("列描述")
    private String columnDesc;

    @ApiModelProperty("字段类型")
    private String fieldType;

    @ApiModelProperty("字段名称")
    private String fieldName;

    @ApiModelProperty("是否是数字")
    private Boolean isNumber;

}
