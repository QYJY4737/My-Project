package cn.congee.api.module.system.role.basic.domain.entity;

import cn.congee.api.common.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * [ 角色 ]
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午6:35
 **/
@Data
@TableName(value = "t_role")
public class RoleEntity extends BaseEntity {

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色备注
     */
    private String remark;

}
