package cn.congee.api.module.system.role.basic;

import cn.congee.api.common.anno.OperateLog;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.constant.SwaggerTagConst;
import cn.congee.api.module.system.role.basic.domain.dto.RoleAddDTO;
import cn.congee.api.module.system.role.basic.domain.dto.RoleUpdateDTO;
import cn.congee.api.module.system.role.basic.domain.dto.RoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 角色管理路由
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午2:59
 **/
@OperateLog
@RestController
@RequestMapping(value = "/role")
@Api(tags = {SwaggerTagConst.Admin.MANAGER_ROLE})
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "添加角色", notes = "添加角色")
    @PostMapping("/add")
    public ResponseDTO addRole(@Valid @RequestBody RoleAddDTO roleAddDTO) {
        return roleService.addRole(roleAddDTO);
    }

    @ApiOperation(value = "删除角色", notes = "根据id删除角色")
    @GetMapping("/delete/{roleId}")
    public ResponseDTO<String> deleteRole(@PathVariable(value = "roleId") Long roleId) {
        return roleService.deleteRole(roleId);
    }

    @ApiOperation(value = "更新角色", notes = "更新角色")
    @PostMapping("/update")
    public ResponseDTO<String> updateRole(@Valid @RequestBody RoleUpdateDTO roleUpdateDTO) {
        return roleService.updateRole(roleUpdateDTO);
    }

    @ApiOperation(value = "获取角色数据", notes = "根据id获取角色数据")
    @GetMapping("/get/{roleId}")
    public ResponseDTO<RoleVO> getRole(@PathVariable(value = "roleId") Long roleId) {
        return roleService.getRoleById(roleId);
    }

    @ApiOperation(value = "获取所有角色", notes = "获取所有角色数据")
    @GetMapping("/getAll")
    public ResponseDTO<List<RoleVO>> getAllRole() {
        return roleService.getAllRole();
    }

}
