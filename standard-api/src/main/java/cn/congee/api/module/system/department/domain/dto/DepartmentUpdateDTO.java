package cn.congee.api.module.system.department.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午2:25
 **/
@Data
public class DepartmentUpdateDTO extends DepartmentCreateDTO {

    @ApiModelProperty("部门id")
    @NotNull(message = "部门id不能为空")
    private Long id;

}
