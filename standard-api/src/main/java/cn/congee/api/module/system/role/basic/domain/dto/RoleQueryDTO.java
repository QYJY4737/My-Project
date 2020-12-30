package cn.congee.api.module.system.role.basic.domain.dto;

import cn.congee.api.common.domain.PageParamDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午3:35
 **/
@Data
public class RoleQueryDTO extends PageParamDTO {

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色id")
    private String roleId;

}
