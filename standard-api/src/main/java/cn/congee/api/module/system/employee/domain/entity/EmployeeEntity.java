package cn.congee.api.module.system.employee.domain.entity;

import cn.congee.api.common.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午3:13
 **/
@Data
@TableName("t_employee")
public class EmployeeEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -8794328598524272806L;

    /**
     * 登录账号
     */
    private String loginName;

    /**
     * 登录密码
     */
    private String loginPwd;

    /**
     * 员工名称
     */
    private String actualName;

    /**
     * 别名
     */
    private String nickName;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 出生日期
     */
    private String birthday;


    /**
     * 部门id
     */
    private Long departmentId;

    /**
     * 是否离职
     */
    private Integer isLeave;

    /**
     * 是否被禁用
     */
    private Integer isDisabled;
    /**
     * 邮箱
     */
    private String email;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建者id
     */
    private Long createUser;

    /**
     * 删除状态 0否 1是
     */
    private Long isDelete;

}
