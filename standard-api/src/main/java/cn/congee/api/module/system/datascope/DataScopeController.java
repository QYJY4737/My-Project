package cn.congee.api.module.system.datascope;

import cn.congee.api.common.anno.NoValidPrivilege;
import cn.congee.api.common.anno.OperateLog;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.constant.SwaggerTagConst;
import cn.congee.api.module.system.datascope.domain.dto.DataScopeAndViewTypeVO;
import cn.congee.api.module.system.datascope.domain.dto.DataScopeBatchSetRoleDTO;
import cn.congee.api.module.system.datascope.domain.dto.DataScopeSelectVO;
import cn.congee.api.module.system.datascope.service.DataScopeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午2:23
 **/
@OperateLog
@RestController
@RequestMapping(value = "/dataScope")
@Api(tags = {SwaggerTagConst.Admin.MANAGER_DATA_SCOPE})
public class DataScopeController {

    @Autowired
    private DataScopeService dataScopeService;

    @ApiOperation(value = "获取当前系统所配置的所有数据范围", notes = "获取当前系统所配置的所有数据范围")
    @GetMapping("/list")
    @NoValidPrivilege
    public ResponseDTO<List<DataScopeAndViewTypeVO>> dataScopeList() {
        return dataScopeService.dataScopeList();
    }

    @ApiOperation(value = "获取某角色所设置的数据范围", notes = "获取某角色所设置的数据范围")
    @GetMapping("/listByRole/{roleId}")
    @NoValidPrivilege
    public ResponseDTO<List<DataScopeSelectVO>> dataScopeListByRole(@PathVariable Long roleId) {
        return dataScopeService.dataScopeListByRole(roleId);
    }

    @ApiOperation(value = "批量设置某角色数据范围", notes = "批量设置某角色数据范围")
    @PostMapping("/batchSet")
    @NoValidPrivilege
    public ResponseDTO<String> dataScopeBatchSet(@RequestBody @Valid DataScopeBatchSetRoleDTO batchSetRoleDTO) {
        return dataScopeService.dataScopeBatchSet(batchSetRoleDTO);
    }

}
