package cn.congee.api.module.system.privilege.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午2:48
 **/
@Data
public class PrivilegeMenuVO {

    @ApiModelProperty("菜单名")
    private String menuName;

    @ApiModelProperty("菜单Key")
    private String menuKey;

    @ApiModelProperty("菜单父级Key")
    private String parentKey;

    @ApiModelProperty("顺序")
    private Integer sort;

    @ApiModelProperty("前端路由path")
    private String url;

}
