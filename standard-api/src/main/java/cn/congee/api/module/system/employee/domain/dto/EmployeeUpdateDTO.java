package cn.congee.api.module.system.employee.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 添加员工
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午4:58
 **/
@Data
public class EmployeeUpdateDTO extends EmployeeBaseDTO {

    @ApiModelProperty("员工id")
    @NotNull(message = "员工id不能为空")
    private Long id;

    @ApiModelProperty("密码")
    private String loginPwd;

    @ApiModelProperty("岗位ID 集合")
    private List<Long> positionIdList;

}
