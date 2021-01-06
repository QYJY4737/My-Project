package cn.congee.api.module.system.employee.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 不带分页的查询条件
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午3:21
 **/
@Data
public class EmployeeQueryExportDTO {

    @ApiModelProperty(hidden = true)
    private String phone;

    @ApiModelProperty("姓名")
    private String actualName;

    @ApiModelProperty(hidden = true)
    private String keyword;

    @ApiModelProperty(hidden = true)
    private Long departmentId;

    @ApiModelProperty(hidden = true)
    private Integer isLeave;

    @ApiModelProperty(hidden = true)
    private Integer isDisabled;

    @ApiModelProperty(value = "删除状态 0否 1是 不需要传", hidden = true)
    private Integer isDelete;

    @ApiModelProperty(value = "员工ID集合", hidden = true)
    private List<Long> employeeIds;

}
