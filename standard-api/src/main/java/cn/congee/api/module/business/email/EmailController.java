package cn.congee.api.module.business.email;

import cn.congee.api.common.anno.NoValidPrivilege;
import cn.congee.api.common.anno.OperateLog;
import cn.congee.api.common.domain.PageResultDTO;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.constant.SwaggerTagConst;
import cn.congee.api.module.business.email.domain.dto.EmailDTO;
import cn.congee.api.module.business.email.domain.dto.EmailQueryDTO;
import cn.congee.api.module.business.email.domain.dto.EmailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午8:35
 **/
@OperateLog
@RestController
@RequestMapping(value = "/email")
@Api(tags = {SwaggerTagConst.Admin.MANAGER_EMAIL})
public class EmailController {

    @Autowired
    private EmailService emailService;

    @ApiOperation(value = "分页查询",notes = "@author wgb")
    @PostMapping("/page/query")
    @NoValidPrivilege
    public ResponseDTO<PageResultDTO<EmailVO>> queryByPage(@RequestBody @Validated EmailQueryDTO queryDTO) {
        return emailService.queryByPage(queryDTO);
    }

    @ApiOperation(value = "添加",notes = "@author wgb")
    @PostMapping("/add")
    @NoValidPrivilege
    public ResponseDTO<Long> add(@RequestBody @Valid EmailDTO addTO){
        return emailService.add(addTO);
    }

    @ApiOperation(value="修改",notes = "@author wgb")
    @PostMapping("/update")
    @NoValidPrivilege
    public ResponseDTO<Long> update(@RequestBody @Valid EmailDTO updateDTO){
        return emailService.update(updateDTO);
    }


    @ApiOperation(value="删除",notes = "@author wgb")
    @GetMapping("/delete/{id}")
    @NoValidPrivilege
    public ResponseDTO<String> delete(@PathVariable("id") Long id){
        return emailService.delete(id);
    }


    @ApiOperation(value="详情",notes = "@author wgb")
    @GetMapping("/detail/{id}")
    @NoValidPrivilege
    public ResponseDTO<EmailVO> detail(@PathVariable("id") Long id){
        return emailService.detail(id);
    }


    @ApiOperation(value="发送",notes = "@author wgb")
    @GetMapping("/send/{id}")
    @NoValidPrivilege
    public ResponseDTO<String> send(@PathVariable("id") Long id){
        return emailService.send(id);
    }

}
