package cn.congee.api.module.system.login.domain;

import cn.congee.api.common.anno.ApiModelPropertyEnum;
import cn.congee.api.module.system.privilege.constant.PrivilegeTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午2:37
 **/
@Data
public class LoginPrivilegeDTO {

    @ApiModelProperty("权限key")
    private String key;

    @ApiModelPropertyEnum(enumDesc = "菜单类型",value = PrivilegeTypeEnum.class)
    private Integer type;

    @ApiModelProperty("url")
    private String url;

    @ApiModelProperty("父级key")
    private String parentKey;

}
