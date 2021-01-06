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
    @ApiOperation(value = "查询员工列表", notes = "查询员工列表")
    public ResponseDTO<PageResultDTO<EmployeeVO>> query(@RequestBody EmployeeQueryDTO query) {
        return employeeService.selectEmployeeList(query);
    }

    @GetMapping("/get/all")
    @ApiOperation(value = "查询所有员工基本信息，用于选择框", notes = "查询所有员工基本信息，用于选择框")
    @NoValidPrivilege
    public ResponseDTO<List<EmployeeVO>> getAll() {
        return ResponseDTO.succData(employeeService.getAllEmployee());
    }

    @ApiOperation(value = "添加员工", notes = "添加员工")
    @PostMapping("/add")
    public ResponseDTO<String> addEmployee(@Valid @RequestBody EmployeeAddDTO emp) {
        RequestTokenBO requestToken = StandardRequestTokenUtil.getRequestUser();
        return employeeService.addEmployee(emp, requestToken);
    }

    @ApiOperation(value = "禁用单个员工", notes = "禁用单个员工")
    @GetMapping("/updateStatus/{employeeId}/{status}")
    public ResponseDTO<String> updateStatus(@PathVariable(value = "employeeId") Long employeeId, @PathVariable(value = "status") Integer status) {
        return employeeService.updateStatus(employeeId, status);
    }

    @ApiOperation(value = "批量更新员工状态", notes = "批量更新员工状态")
    @PostMapping("/batchUpdateStatus")
    public ResponseDTO<String> batchUpdateStatus(@Valid @RequestBody EmployeeBatchUpdateStatusDTO batchUpdateStatusDTO) {
        return employeeService.batchUpdateStatus(batchUpdateStatusDTO);
    }

    @ApiOperation(value = "更新员工信息", notes = "更新员工信息")
    @PostMapping("/update")
    public ResponseDTO<String> updateEmployee(@Valid @RequestBody EmployeeUpdateDTO employeeUpdateDto) {
        return employeeService.updateEmployee(employeeUpdateDto);
    }

    @ApiOperation(value = "根据员工ID删除员工信息", notes = "根据员工ID删除员工信息")
    @PostMapping("/delete/{employeeId}")
    public ResponseDTO<String> deleteEmployeeById(@PathVariable(value = "employeeId") Long employeeId) {
        return employeeService.deleteEmployeeById(employeeId);
    }

    @ApiOperation(value = "单个员工角色授权", notes = "单个员工角色授权")
    @PostMapping("/updateRoles")
    public ResponseDTO<String> updateRoles(@Valid @RequestBody EmployeeUpdateRolesDTO updateRolesDTO) {
        return employeeService.updateRoles(updateRolesDTO);
    }

    @ApiOperation(value = "修改密码", notes = "修改密码")
    @PostMapping("/updatePwd")
    public ResponseDTO<String> updatePwd(@Valid @RequestBody EmployeeUpdatePwdDTO updatePwdDTO) {
        RequestTokenBO requestToken = StandardRequestTokenUtil.getRequestUser();
        return employeeService.updatePwd(updatePwdDTO, requestToken);
    }

    @ApiOperation(value = "通过部门id获取当前部门的人员&没有部门的人", notes = "通过部门id获取当前部门的人员&没有部门的人")
    @GetMapping("/listEmployeeByDeptId/{deptId}")
    public ResponseDTO<List<EmployeeVO>> listEmployeeByDeptId(@PathVariable(value = "deptId") Long deptId) {
        return employeeService.getEmployeeByDeptId(deptId);
    }

    @ApiOperation(value = "员工重置密码", notes = "员工重置密码")
    @GetMapping("/resetPasswd/{employeeId}")
    public ResponseDTO resetPasswd(@PathVariable(value = "employeeId") Integer employeeId) {
        return employeeService.resetPasswd(employeeId);
    }

}
