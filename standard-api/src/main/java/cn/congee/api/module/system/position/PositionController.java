package cn.congee.api.module.system.position;

import cn.congee.api.common.anno.OperateLog;
import cn.congee.api.common.domain.PageResultDTO;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.constant.SwaggerTagConst;
import cn.congee.api.module.system.position.domain.dto.PositionAddDTO;
import cn.congee.api.module.system.position.domain.dto.PositionQueryDTO;
import cn.congee.api.module.system.position.domain.dto.PositionResultVO;
import cn.congee.api.module.system.position.domain.dto.PositionUpdateDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午2:45
 **/
@OperateLog
@RestController
@RequestMapping(value = "/position")
@Api(tags = {SwaggerTagConst.Admin.MANAGER_JOB})
public class PositionController {

    @Autowired
    private PositionService positionService;

    @ApiOperation(value = "分页查询所有岗位", notes = "分页查询所有岗位 @author wgb")
    @PostMapping("/getListPage")
    public ResponseDTO<PageResultDTO<PositionResultVO>> getJobPage(@RequestBody @Valid PositionQueryDTO queryDTO) {
        return positionService.queryPositionByPage(queryDTO);
    }

    @ApiOperation(value = "添加岗位", notes = "添加岗位 @author wgb")
    @PostMapping("/add")
    public ResponseDTO<String> addJob(@RequestBody @Valid PositionAddDTO addDTO) {
        return positionService.addPosition(addDTO);
    }

    @ApiOperation(value = "更新岗位", notes = "更新岗位 @author wgb")
    @PostMapping("/update")
    public ResponseDTO<String> updateJob(@RequestBody @Valid PositionUpdateDTO updateDTO) {
        return positionService.updatePosition(updateDTO);
    }

    @ApiOperation(value = "根据ID查询岗位", notes = "根据ID查询岗位 @author wgb")
    @GetMapping("/queryById/{id}")
    public ResponseDTO<PositionResultVO> queryJobById(@PathVariable Long id) {
        return positionService.queryPositionById(id);
    }

    @ApiOperation(value = "根据ID删除岗位", notes = "根据ID删除岗位 @author wgb")
    @GetMapping("/remove/{id}")
    public ResponseDTO<String> removeJob(@PathVariable Long id) {
        return positionService.removePosition(id);
    }

}
