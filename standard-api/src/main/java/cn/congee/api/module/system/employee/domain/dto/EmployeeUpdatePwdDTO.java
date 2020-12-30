package cn.congee.api.module.system.employee.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 修改密码所需参数
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午5:41
 **/
@Data
public class EmployeeUpdatePwdDTO {

    @ApiModelProperty("新密码")
    @NotNull
    private String pwd;

    @ApiModelProperty("原密码")
    @NotNull
    private String oldPwd;

}
