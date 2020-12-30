package cn.congee.api.module.system.role.basic.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 批量添加角色员工DTO
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午2:55
 **/
@Data
public class RoleBatchDTO {

    @ApiModelProperty("角色id")
    @NotNull(message = "角色id不能为空")
    protected Long roleId;

    /**
     * 员工id集合
     */
    @ApiModelProperty(value = "员工id集合")
    @NotEmpty(message = "员工id不能为空")
    protected List<Long> employeeIds;

}
