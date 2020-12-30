package cn.congee.api.module.system.login;

import cn.congee.api.common.anno.NoNeedLogin;
import cn.congee.api.common.anno.NoValidPrivilege;
import cn.congee.api.common.anno.OperateLog;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.constant.SwaggerTagConst;
import cn.congee.api.module.system.employee.domain.dto.EmployeeLoginFormDTO;
import cn.congee.api.module.system.login.domain.KaptchaVO;
import cn.congee.api.module.system.login.domain.LoginDetailVO;
import cn.congee.api.module.system.login.domain.RequestTokenBO;
import cn.congee.api.util.StandardRequestTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 后台登录
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午2:40
 **/
@OperateLog
@RestController
@RequestMapping(value = "/session")
@Api(tags = {SwaggerTagConst.Admin.MANAGER_USER_LOGIN})
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录")
    @NoNeedLogin
    public ResponseDTO<LoginDetailVO> login(@Valid @RequestBody EmployeeLoginFormDTO loginForm, HttpServletRequest request) {
        return loginService.login(loginForm, request);
    }


    @GetMapping("/get")
    @ApiOperation(value = "获取session", notes = "获取session")
    @NoValidPrivilege
    public ResponseDTO<LoginDetailVO> getSession() {
        RequestTokenBO requestUser = StandardRequestTokenUtil.getRequestUser();
        return ResponseDTO.succData(loginService.getSession(requestUser));
    }

    @GetMapping("/logOut")
    @ApiOperation(value = "退出登陆", notes = "退出登陆")
    @NoValidPrivilege
    public ResponseDTO<Boolean> logOut() {
        RequestTokenBO requestToken = StandardRequestTokenUtil.getRequestUser();
        if (null == requestToken) {
            return ResponseDTO.wrap(LoginResponseCodeConst.LOGIN_ERROR);
        }
        return loginService.logoutByToken(requestToken);
    }

    @GetMapping("/verificationCode")
    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    @NoNeedLogin
    public ResponseDTO<KaptchaVO> verificationCode() {
        return loginService.verificationCode();
    }

}
