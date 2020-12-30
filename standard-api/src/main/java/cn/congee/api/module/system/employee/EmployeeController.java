package cn.congee.api.module.system.employee;

import cn.congee.api.common.anno.NoValidPrivilege;
import cn.congee.api.common.anno.OperateLog;
import cn.congee.api.common.domain.PageResultDTO;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.constant.SwaggerTagConst;
import cn.congee.api.module.system.employee.domain.dto.*;
import cn.congee.api.module.system.employee.domain.vo.EmployeeVO;
import cn.congee.api.module.system.login.domain.RequestTokenBO;
import cn.congee.api.util.StandardRequestTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 员工管理
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午2:32
 **/
@OperateLog
@RestController
@RequestMapping(value = "/employee")
@Api(tags = {SwaggerTagConst.Admin.MANAGER_USER})
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/query")
    @ApiOperation(value = "员工管理查询", notes = "员工管理查询 @author wgb")
    public ResponseDTO<PageResultDTO<EmployeeVO>> query(@RequestBody EmployeeQueryDTO query) {
        return employeeService.selectEmployeeList(query);
    }

    @GetMapping("/get/all")
    @ApiOperation(value = "查询所有员工基本信息，用于选择框", notes = "查询所有员工基本信息，用于选择框")
    @NoValidPrivilege
    public ResponseDTO<List<EmployeeVO>> getAll() {
        return ResponseDTO.succData(employeeService.getAllEmployee());
    }

    @ApiOperation(value = "添加员工", notes = "@author wgb")
    @PostMapping("/add")
    public ResponseDTO<String> addEmployee(@Valid @RequestBody EmployeeAddDTO emp) {
        RequestTokenBO requestToken = StandardRequestTokenUtil.getRequestUser();
        return employeeService.addEmployee(emp, requestToken);
    }

    @ApiOperation(value = "禁用单个员工", notes = "@author wgb")
    @GetMapping("/updateStatus/{employeeId}/{status}")
    public ResponseDTO<String> updateStatus(@PathVariable("employeeId") Long employeeId, @PathVariable("status") Integer status) {
        return employeeService.updateStatus(employeeId, status);
    }

    @ApiOperation(value = "批量禁用", notes = "@author wgb")
    @PostMapping("/batchUpdateStatus")
    public ResponseDTO<String> batchUpdateStatus(@Valid @RequestBody EmployeeBatchUpdateStatusDTO batchUpdateStatusDTO) {
        return employeeService.batchUpdateStatus(batchUpdateStatusDTO);
    }

    @ApiOperation(value = "更新员工信息", notes = "@author wgb")
    @PostMapping("/update")
    public ResponseDTO<String> updateEmployee(@Valid @RequestBody EmployeeUpdateDTO employeeUpdateDto) {
        return employeeService.updateEmployee(employeeUpdateDto);
    }

    @ApiOperation(value = "删除员工信息", notes = "@author wgb")
    @PostMapping("/delete/{employeeId}")
    public ResponseDTO<String> deleteEmployeeById(@PathVariable("employeeId") Long employeeId) {
        return employeeService.deleteEmployeeById(employeeId);
    }

    @ApiOperation(value = "单个员工角色授权", notes = "@author wgb")
    @PostMapping("/updateRoles")
    public ResponseDTO<String> updateRoles(@Valid @RequestBody EmployeeUpdateRolesDTO updateRolesDTO) {
        return employeeService.updateRoles(updateRolesDTO);
    }

    @ApiOperation(value = "修改密码", notes = "@author wgb")
    @PostMapping("/updatePwd")
    public ResponseDTO<String> updatePwd(@Valid @RequestBody EmployeeUpdatePwdDTO updatePwdDTO) {
        RequestTokenBO requestToken = StandardRequestTokenUtil.getRequestUser();
        return employeeService.updatePwd(updatePwdDTO, requestToken);
    }

    @ApiOperation(value = "通过部门id获取当前部门的人员&没有部门的人", notes = "@author wgb")
    @GetMapping("/listEmployeeByDeptId/{deptId}")
    public ResponseDTO<List<EmployeeVO>> listEmployeeByDeptId(@PathVariable Long deptId) {
        return employeeService.getEmployeeByDeptId(deptId);
    }

    @ApiOperation(value = "员工重置密码", notes = "@author wgb")
    @GetMapping("/resetPasswd/{employeeId}")
    public ResponseDTO resetPasswd(@PathVariable("employeeId") Integer employeeId) {
        return employeeService.resetPasswd(employeeId);
    }

}
