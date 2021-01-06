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

    @ApiOperation(value = "分页查询用户操作日志",notes = "分页查询用户操作日志")
    @PostMapping("/page/query")
    public ResponseDTO<PageResultDTO<UserOperateLogDTO>> queryByPage(@RequestBody UserOperateLogQueryDTO queryDTO) {
        return userOperateLogService.queryByPage(queryDTO);
    }

    @ApiOperation(value="根据id删除用户操作日志",notes = "根据id删除用户操作日志")
    @GetMapping("/delete/{id}")
    public ResponseDTO<String> delete(@PathVariable(value = "id") Long id){
        return userOperateLogService.delete(id);
    }


    @ApiOperation(value="根据id查询用户操作日志详情",notes = "根据id查询用户操作日志详情")
    @GetMapping("/detail/{id}")
    public ResponseDTO<UserOperateLogDTO> detail(@PathVariable(value = "id") Long id){
        return userOperateLogService.detail(id);
    }

}
