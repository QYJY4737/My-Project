package cn.congee.api.module.system.role.roleprivilege.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午3:05
 **/
@Data
public class RolePrivilegeDTO {

    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    @NotNull(message = "角色id不能为空")
    private Long roleId;

    /**
     * 功能权限id 集合
     */
    @ApiModelProperty("功能权限Key集合")
    @NotNull(message = "功能权限集合不能为空")
    private List<String> privilegeKeyList;

}
