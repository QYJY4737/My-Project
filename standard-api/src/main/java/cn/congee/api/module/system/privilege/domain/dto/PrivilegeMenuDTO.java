package cn.congee.api.module.system.privilege.domain.dto;

import cn.congee.api.common.anno.ApiModelPropertyEnum;
import cn.congee.api.module.system.privilege.constant.PrivilegeTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午2:48
 **/
@Data
public class PrivilegeMenuDTO {

    @ApiModelPropertyEnum(enumDesc = "菜单类型",value = PrivilegeTypeEnum.class)
    @NotNull
    private Integer type;

    @ApiModelProperty("菜单名")
    @NotNull(message = "菜单名不能为空")
    private String menuName;

    @ApiModelProperty("菜单Key")
    @NotNull(message = "菜单Key不能为空")
    private String menuKey;

    @ApiModelProperty("父级菜单Key,根节点不传")
    private String parentKey;

    @ApiModelProperty("前端路由path")
    @NotNull(message = "前端路由path不能为空")
    private String url;

    @ApiModelProperty("排序字段")
    @NotNull(message = "菜单项顺序不能为空")
    private Integer sort;

}
