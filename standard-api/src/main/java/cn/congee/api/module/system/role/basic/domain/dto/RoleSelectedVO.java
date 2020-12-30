package cn.congee.api.module.system.role.basic.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午2:56
 **/
@Data
public class RoleSelectedVO extends RoleVO {

    @ApiModelProperty("角色名称")
    private Boolean selected;

}
