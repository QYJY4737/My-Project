package cn.congee.api.module.system.privilege.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午2:47
 **/
@Data
public class PrivilegeFunctionDTO {

    @ApiModelProperty("功能点名称")
    @NotBlank(message = "功能点名称不能为空")
    private String functionName;

    @ApiModelProperty("所属菜单Key")
    @NotBlank(message = "所属菜单Key不能为空")
    private String menuKey;

    @ApiModelProperty("功能点Key")
    @NotBlank(message = "功能点Key不能为空")
    private String functionKey;

    @ApiModelProperty("url列表")
    private String url;

    @ApiModelProperty("排序")
    @NotNull(message = "请输入功能点顺序")
    private Integer sort;

}
