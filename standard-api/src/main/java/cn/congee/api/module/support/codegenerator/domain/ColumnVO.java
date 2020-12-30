package cn.congee.api.module.support.codegenerator.domain;

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

    private String columnName;

    private String columnType;

    private String columnDesc;

    private String fieldType;

    private String fieldName;

    private Boolean isNumber;

}
