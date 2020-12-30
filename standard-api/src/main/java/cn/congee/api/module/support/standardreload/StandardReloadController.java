package cn.congee.api.module.support.standardreload;

import cn.congee.api.common.anno.NoValidPrivilege;
import cn.congee.api.common.anno.OperateLog;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.constant.SwaggerTagConst;
import cn.congee.api.module.support.standardreload.domain.dto.ReloadItemUpdateDTO;
import cn.congee.api.module.support.standardreload.domain.dto.ReloadItemVO;
import cn.congee.api.module.support.standardreload.domain.dto.ReloadResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Standard Reload 路由
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午12:05
 **/
@OperateLog
@RestController
@RequestMapping(value = "/standardReload")
@Api(tags = {SwaggerTagConst.Admin.MANAGER_SMART_RELOAD})
public class StandardReloadController {

    @Autowired
    private StandardReloadService standardReloadService;

    @ApiOperation(value = "获取全部Standard-reload项", notes = "获取全部Standard-reload项")
    @GetMapping("/all")
    @NoValidPrivilege
    public ResponseDTO<List<ReloadItemVO>> listAllReloadItem() {
        return standardReloadService.listAllReloadItem();
    }

    @ApiOperation(value = "获取reload result", notes = "获取reload result")
    @GetMapping("/result/{tag}")
    @NoValidPrivilege
    public ResponseDTO<List<ReloadResultVO>> queryReloadResult(@PathVariable("tag") String tag) {
        return standardReloadService.listReloadItemResult(tag);
    }

    @ApiOperation("通过tag更新标识")
    @PostMapping("/update")
    @NoValidPrivilege
    public ResponseDTO<String> updateByTag(@RequestBody @Valid ReloadItemUpdateDTO updateDTO) {
        return standardReloadService.updateByTag(updateDTO);
    }

}
