package cn.congee.api.module.business.log.userloginlog;

import cn.congee.api.common.anno.OperateLog;
import cn.congee.api.common.domain.PageResultDTO;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.constant.SwaggerTagConst;
import cn.congee.api.module.business.log.userloginlog.domain.UserLoginLogDTO;
import cn.congee.api.module.business.log.userloginlog.domain.UserLoginLogQueryDTO;
import cn.congee.api.module.system.employee.domain.dto.EmployeeQueryDTO;
import cn.congee.api.module.system.employee.domain.vo.EmployeeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午9:13
 **/
@OperateLog
@RestController
@RequestMapping(value = "/userLoginLog")
@Api(tags = {SwaggerTagConst.Admin.MANAGER_USER_LOGIN_LOG})
public class UserLoginLogController {

    @Autowired
    private UserLoginLogService userLoginLogService;

    @ApiOperation(value = "分页查询用户登录日志", notes = "@author wgb")
    @PostMapping("/page/query")
    public ResponseDTO<PageResultDTO<UserLoginLogDTO>> queryByPage(@RequestBody UserLoginLogQueryDTO queryDTO) {
        return userLoginLogService.queryByPage(queryDTO);
    }

    @ApiOperation(value = "删除用户登录日志", notes = "@author wgb")
    @GetMapping("/delete/{id}")
    public ResponseDTO<String> delete(@PathVariable("id") Long id) {
        return userLoginLogService.delete(id);
    }

    @ApiOperation(value = "查询员工在线状态", notes = "@author wgb")
    @PostMapping("/userOnLine/query")
    public ResponseDTO<PageResultDTO<EmployeeVO>> queryUserOnLine(@RequestBody @Valid EmployeeQueryDTO queryDTO) {
        return userLoginLogService.queryUserOnLine(queryDTO);
    }

}
