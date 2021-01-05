package cn.congee.api.module.system.login.domain;

import cn.congee.api.module.system.employee.domain.dto.EmployeeDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午2:36
 **/
@Data
public class LoginCacheDTO {

    @ApiModelProperty("基本信息")
    private EmployeeDTO employeeDTO;

    @ApiModelProperty("过期时间")
    private Long expireTime;

}
