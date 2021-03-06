package cn.congee.api.module.system.datascope.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午2:11
 **/
@Data
public class DataScopeBatchSetRoleDTO {

    @ApiModelProperty("角色id")
    @NotNull(message = "角色id不能为空")
    private Long roleId;

    @ApiModelProperty("设置信息")
    @Valid
    private List<DataScopeBatchSetDTO> batchSetList;

}
