package cn.congee.api.module.support.codegenerator.domain;

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

    private String fieldName;

    private String columnName;

    private String columnDesc;

    private String fieldType;

    private String sqlOperate;

}
