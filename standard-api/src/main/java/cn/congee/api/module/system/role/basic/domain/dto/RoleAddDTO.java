package cn.congee.api.module.system.role.basic.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 角色添加DTO
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午2:54
 **/
@Data
public class RoleAddDTO {

    @ApiModelProperty("角色名称")
    @NotNull(message = "角色名称不能为空")
    @Length(min = 1, max = 20, message = "角色名称(1-20)个字符")
    private String roleName;

    @ApiModelProperty("角色描述")
    @Length(max = 255, message = "角色描述最多255个字符")
    private String remark;

}
