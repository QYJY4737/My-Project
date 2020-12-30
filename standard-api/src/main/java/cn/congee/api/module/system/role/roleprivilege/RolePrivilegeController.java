package cn.congee.api.module.system.role.roleprivilege;

import cn.congee.api.common.anno.OperateLog;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.constant.SwaggerTagConst;
import cn.congee.api.module.system.role.roleprivilege.domain.dto.RolePrivilegeDTO;
import cn.congee.api.module.system.role.roleprivilege.domain.dto.RolePrivilegeTreeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * [ 与员工权限相关：角色权限关系、权限列表 ]
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午3:08
 **/
@OperateLog
@RestController
@RequestMapping(value = "/privilege")
@Api(tags = {SwaggerTagConst.Admin.MANAGER_ROLE_PRIVILEGE})
public class RolePrivilegeController {

    @Autowired
    private RolePrivilegeService rolePrivilegeService;

    @ApiOperation(value = "更新角色权限", notes = "更新角色权限")
    @PostMapping("/updateRolePrivilege")
    public ResponseDTO<String> updateRolePrivilege(@Valid @RequestBody RolePrivilegeDTO updateDTO) {
        return rolePrivilegeService.updateRolePrivilege(updateDTO);
    }

    @ApiOperation(value = "获取角色可选的功能权限", notes = "获取角色可选的功能权限")
    @GetMapping("/listPrivilegeByRoleId/{roleId}")
    public ResponseDTO<RolePrivilegeTreeVO> listPrivilegeByRoleId(@PathVariable("roleId") Long roleId) {
        return rolePrivilegeService.listPrivilegeByRoleId(roleId);
    }

}
