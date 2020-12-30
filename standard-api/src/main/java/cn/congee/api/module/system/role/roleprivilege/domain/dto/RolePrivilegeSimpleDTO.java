package cn.congee.api.module.system.role.roleprivilege.domain.dto;

import cn.congee.api.common.anno.ApiModelPropertyEnum;
import cn.congee.api.module.system.privilege.constant.PrivilegeTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 角色功能权限
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午3:06
 **/
@Data
public class RolePrivilegeSimpleDTO {

    @ApiModelProperty("父级Key")
    private String parentKey;

    @ApiModelProperty("功能名称")
    private String name;

    @ApiModelPropertyEnum(enumDesc = "类型",value = PrivilegeTypeEnum.class)
    private Integer type;

    @ApiModelProperty("key")
    private String key;

    @ApiModelProperty("url")
    private String url;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("子级列表")
    private List<RolePrivilegeSimpleDTO> children;

}
