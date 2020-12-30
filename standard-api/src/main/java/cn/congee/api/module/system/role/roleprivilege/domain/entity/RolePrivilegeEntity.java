package cn.congee.api.module.system.role.roleprivilege.domain.entity;

import cn.congee.api.common.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * [ 角色 权限关系 ]
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午6:09
 **/
@Data
@TableName(value = "t_role_privilege")
public class RolePrivilegeEntity extends BaseEntity {

    /**
     * 角色 id
     */
    private Long roleId;

    /**
     * 功能权限 id
     */
    private String privilegeKey;

}
