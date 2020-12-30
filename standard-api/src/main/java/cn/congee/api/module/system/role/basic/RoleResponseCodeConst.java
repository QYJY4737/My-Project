package cn.congee.api.module.system.role.basic;

import cn.congee.api.common.constant.ResponseCodeConst;

/**
 * 角色业务状态码 6001 - 6999
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午3:04
 **/
public class RoleResponseCodeConst extends ResponseCodeConst {

    /**
     * 10501 角色名称已存在
     */
    public static final RoleResponseCodeConst ROLE_NAME_EXISTS = new RoleResponseCodeConst(6001, "角色名称已存在");

    /**
     * 10502 角色不存在
     */
    public static final RoleResponseCodeConst ROLE_NOT_EXISTS = new RoleResponseCodeConst(6002, "角色不存在");

    public RoleResponseCodeConst(int code, String msg) {
        super(code, msg);
    }

}
