package cn.congee.api.module.system.systemconfig;

import cn.congee.api.common.anno.OperateLog;
import cn.congee.api.common.domain.PageResultDTO;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.constant.SwaggerTagConst;
import cn.congee.api.module.system.systemconfig.domain.dto.SystemConfigAddDTO;
import cn.congee.api.module.system.systemconfig.domain.dto.SystemConfigQueryDTO;
import cn.congee.api.module.system.systemconfig.domain.dto.SystemConfigUpdateDTO;
import cn.congee.api.module.system.systemconfig.domain.dto.SystemConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午3:10
 **/
@OperateLog
@RestController
@RequestMapping(value = "/systemConfig")
@Api(tags = {SwaggerTagConst.Admin.MANAGER_SYSTEM_CONFIG})
public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;

    @ApiOperation(value = "分页查询所有系统配置", notes = "分页查询所有系统配置")
    @PostMapping("/getListPage")
    public ResponseDTO<PageResultDTO<SystemConfigVO>> getSystemConfigPage(@RequestBody @Valid SystemConfigQueryDTO queryDTO) {
        return systemConfigService.getSystemConfigPage(queryDTO);
    }

    @ApiOperation(value = "添加配置参数", notes = "添加配置参数")
    @PostMapping("/add")
    public ResponseDTO<String> addSystemConfig(@RequestBody @Valid SystemConfigAddDTO configAddDTO) {
        return systemConfigService.addSystemConfig(configAddDTO);
    }

    @ApiOperation(value = "修改配置参数", notes = "修改配置参数")
    @PostMapping("/update")
    public ResponseDTO<String> updateSystemConfig(@RequestBody @Valid SystemConfigUpdateDTO updateDTO) {
        return systemConfigService.updateSystemConfig(updateDTO);
    }

    @ApiOperation(value = "根据分组查询所有系统配置", notes = "根据分组查询所有系统配置")
    @GetMapping("/getListByGroup")
    public ResponseDTO<List<SystemConfigVO>> getListByGroup(@RequestParam(value = "group") String group) {
        return systemConfigService.getListByGroup(group);
    }

    @ApiOperation(value = "通过key获取对应的信息", notes = "通过key获取对应的信息")
    @GetMapping("/selectByKey")
    public ResponseDTO<SystemConfigVO> selectByKey(@RequestParam(value = "configKey") String configKey) {
        return systemConfigService.selectByKey(configKey);
    }

}
