package cn.congee.api.module.system.role.roleprivilege.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午3:06
 **/
@Data
public class RolePrivilegeTreeVO {

    @ApiModelProperty("角色ID")
    private Long roleId;

    @ApiModelProperty("权限列表")
    private List<RolePrivilegeSimpleDTO> privilege;

    @ApiModelProperty("选中的权限")
    private List<String> selectedKey;

}
