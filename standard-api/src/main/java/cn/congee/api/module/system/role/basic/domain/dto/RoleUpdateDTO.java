package cn.congee.api.module.system.role.basic.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 角色更新修改DTO
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午2:57
 **/
@Data
public class RoleUpdateDTO extends RoleAddDTO {

    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    @NotNull(message = "角色id不能为空")
    protected Long id;

}
