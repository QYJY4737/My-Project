package cn.congee.api.module.support.codegenerator.domain;

import cn.congee.api.module.support.codegenerator.constant.SqlOperateTypeEnum;
import lombok.Builder;
import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午10:37
 **/
@Data
@Builder
public class CodeGeneratorQueryColumnDTO {

    /**
     * 生成查询方法的查询列名
     */
    private String columnName;

    /**
     * 此列的查询动作
     */
    private SqlOperateTypeEnum sqlOperate;

}
