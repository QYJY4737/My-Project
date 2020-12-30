package cn.congee.api.module.system.privilege.controller;

import cn.congee.api.common.anno.OperateLog;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.common.domain.ValidateList;
import cn.congee.api.constant.SwaggerTagConst;
import cn.congee.api.module.system.privilege.domain.dto.*;
import cn.congee.api.module.system.privilege.service.PrivilegeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * [ 与员工权限相关：角色权限关系、权限列表 ]
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午2:52
 **/
@OperateLog
@RestController
@RequestMapping(value = "/privilege")
@Api(tags = {SwaggerTagConst.Admin.MANAGER_PRIVILEGE})
public class PrivilegeController {

    @Autowired
    private PrivilegeService privilegeService;

    @GetMapping("/getAllUrl")
    @ApiOperation(value = "获取所有请求路径", notes = "获取所有请求路径")
    public ResponseDTO<List<PrivilegeRequestUrlVO>> getAllUrl() {
        return privilegeService.getPrivilegeUrlDTOList();
    }

    @ApiOperation(value = "菜单批量保存", notes = "菜单批量保存")
    @PostMapping("/menu/batchSaveMenu")
    public ResponseDTO<String> menuBatchSave(@Valid @RequestBody ValidateList<PrivilegeMenuDTO> menuList) {
        return privilegeService.menuBatchSave(menuList);
    }

    @ApiOperation(value = "查询所有菜单项", notes = "查询所有菜单项")
    @PostMapping("/menu/queryAll")
    public ResponseDTO<List<PrivilegeMenuVO>> queryAll() {
        return privilegeService.menuQueryAll();
    }

    @ApiOperation(value = "保存更新功能点", notes = "保存更新功能点")
    @PostMapping("/function/saveOrUpdate")
    public ResponseDTO<String> functionSaveOrUpdate(@Valid @RequestBody PrivilegeFunctionDTO privilegeFunctionDTO) {
        return privilegeService.functionSaveOrUpdate(privilegeFunctionDTO);
    }

    @ApiOperation(value = "批量保存功能点", notes = "批量保存功能点")
    @PostMapping("/function/batchSave")
    public ResponseDTO<String>  batchSaveFunctionList(@Valid @RequestBody ValidateList<PrivilegeFunctionDTO> functionList) {
        return privilegeService.batchSaveFunctionList(functionList);
    }

    @ApiOperation(value = "查询菜单功能点", notes = "查询菜单功能点")
    @PostMapping("/function/query/{menuKey}")
    public ResponseDTO<List<PrivilegeFunctionVO>> functionQuery(@PathVariable String menuKey) {
        return privilegeService.functionQuery(menuKey);
    }

}
