package cn.congee.api.module.business.log.useroperatelog;

import cn.congee.api.common.anno.OperateLog;
import cn.congee.api.common.domain.PageResultDTO;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.constant.SwaggerTagConst;
import cn.congee.api.module.business.log.useroperatelog.domain.UserOperateLogDTO;
import cn.congee.api.module.business.log.useroperatelog.domain.UserOperateLogQueryDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午9:16
 **/
@OperateLog
@RestController
@RequestMapping(value = "/userOperateLog")
@Api(tags = {SwaggerTagConst.Admin.MANAGER_USER_OPERATE_LOG})
public class UserOperateLogController {

    @Autowired
    private UserOperateLogService userOperateLogService;

    @ApiOperation(value = "分页查询",notes = "@author wgb")
    @PostMapping("/page/query")
    public ResponseDTO<PageResultDTO<UserOperateLogDTO>> queryByPage(@RequestBody UserOperateLogQueryDTO queryDTO) {
        return userOperateLogService.queryByPage(queryDTO);
    }

    @ApiOperation(value="删除",notes = "@author wgb")
    @GetMapping("/delete/{id}")
    public ResponseDTO<String> delete(@PathVariable("id") Long id){
        return userOperateLogService.delete(id);
    }


    @ApiOperation(value="详情",notes = "@author wgb")
    @GetMapping("/detail/{id}")
    public ResponseDTO<UserOperateLogDTO> detail(@PathVariable("id") Long id){
        return userOperateLogService.detail(id);
    }

}
