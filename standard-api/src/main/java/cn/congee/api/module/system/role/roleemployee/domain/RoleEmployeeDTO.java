package cn.congee.api.module.system.role.roleemployee.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午3:01
 **/
@Data
public class RoleEmployeeDTO {

    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("员工id")
    private Long employeeId;

}
