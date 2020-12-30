package cn.congee.api.module.system.role.roleemployee.domain;

import cn.congee.api.common.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * [ 角色 员工关系]
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午3:32
 **/
@Data
@TableName(value = "t_role_employee")
public class RoleEmployeeEntity extends BaseEntity {

    private Long roleId;

    private Long employeeId;

}
