package cn.congee.api.module.system.department;

import cn.congee.api.common.anno.OperateLog;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.constant.SwaggerTagConst;
import cn.congee.api.module.system.department.domain.dto.DepartmentCreateDTO;
import cn.congee.api.module.system.department.domain.dto.DepartmentUpdateDTO;
import cn.congee.api.module.system.department.domain.dto.DepartmentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 部门管理路由器
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午2:28
 **/
@OperateLog
@RestController
@RequestMapping(value = "/department")
@Api(tags = {SwaggerTagConst.Admin.MANAGER_DEPARTMENT})
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation(value = "查询部门树形列表", notes = "查询部门列表")
    @GetMapping("/list")
    public ResponseDTO<List<DepartmentVO>> listDepartment() {
        return departmentService.listDepartment();
    }

    @ApiOperation(value = "查询部门及员工列表", notes = "查询部门及员工列表")
    @GetMapping("/listEmployee")
    public ResponseDTO<List<DepartmentVO>> listDepartmentEmployee() {
        return departmentService.listAllDepartmentEmployee(null);
    }

    @ApiOperation(value = "根据部门名称查询部门及员工列表", notes = "根据部门名称查询部门及员工列表")
    @GetMapping("/listEmployeeByDepartmentName")
    public ResponseDTO<List<DepartmentVO>> listDepartmentEmployee(String departmentName) {
        return departmentService.listAllDepartmentEmployee(departmentName);
    }

    @ApiOperation(value = "添加部门", notes = "添加部门")
    @PostMapping("/add")
    public ResponseDTO<String> addDepartment(@Valid @RequestBody DepartmentCreateDTO departmentCreateDTO) {
        return departmentService.addDepartment(departmentCreateDTO);
    }

    @ApiOperation(value = "更新部门信息", notes = "更新部门信息")
    @PostMapping("/update")
    public ResponseDTO<String> updateDepartment(@Valid @RequestBody DepartmentUpdateDTO departmentUpdateDTO) {
        return departmentService.updateDepartment(departmentUpdateDTO);
    }

    @ApiOperation(value = "根据id删除部门", notes = "根据id删除部门")
    @PostMapping("/delete/{departmentId}")
    public ResponseDTO<String> delDepartment(@PathVariable("departmentId") Long departmentId) {
        return departmentService.delDepartment(departmentId);
    }

    @ApiOperation(value = "根据id获取部门信息", notes = "根据id获取部门信息")
    @GetMapping("/query/{departmentId}")
    public ResponseDTO<DepartmentVO> getDepartment(@PathVariable("departmentId") Long departmentId) {
        return departmentService.getDepartmentById(departmentId);
    }

    @ApiOperation(value = "查询部门列表", notes = "查询部门列表")
    @GetMapping("/listAll")
    public ResponseDTO<List<DepartmentVO>> listAll() {
        return departmentService.listAll();
    }


    @ApiOperation(value = "上下移动", notes = "上下移动")
    @GetMapping("/upOrDown/{departmentId}/{swapId}")
    public ResponseDTO<String> upOrDown(@PathVariable("departmentId") Long departmentId,@PathVariable("swapId") Long swapId) {
        return departmentService.upOrDown(departmentId,swapId);
    }

    @ApiOperation(value = "部门升级", notes = "部门升级")
    @GetMapping("/upgrade/{departmentId}")
    public ResponseDTO<String> upgrade(@PathVariable("departmentId") Long departmentId) {
        return departmentService.upgrade(departmentId);
    }

    @ApiOperation(value = "部门降级", notes = "部门降级")
    @GetMapping("/downgrade/{departmentId}/{preId}")
    public ResponseDTO<String> downgrade(@PathVariable("departmentId") Long departmentId,@PathVariable("preId") Long preId) {
        return departmentService.downgrade(departmentId,preId);
    }

}
