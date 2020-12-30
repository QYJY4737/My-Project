package cn.congee.api.module.system.employee.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 批量更新
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午4:58
 **/
@Data
public class EmployeeBatchUpdateStatusDTO {

    @ApiModelProperty("员工ids")
    @NotNull(message = "员工ids不能为空")
    private List<Long> employeeIds;

    @ApiModelProperty("状态")
    @NotNull(message = "状态不能为空")
    private Integer status;

}
