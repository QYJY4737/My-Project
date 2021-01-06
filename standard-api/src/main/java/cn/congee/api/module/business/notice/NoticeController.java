package cn.congee.api.module.business.notice;

import cn.congee.api.common.anno.NoValidPrivilege;
import cn.congee.api.common.domain.PageParamDTO;
import cn.congee.api.common.domain.PageResultDTO;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.constant.SwaggerTagConst;
import cn.congee.api.module.business.notice.domain.dto.*;
import cn.congee.api.util.StandardRequestTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午9:51
 **/
@RestController
@RequestMapping(value = "/notice")
@Api(tags = {SwaggerTagConst.Admin.MANAGER_NOTICE})
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @ApiOperation(value = "分页查询全部消息", notes = "分页查询全部消息")
    @PostMapping("/page/query")
    @NoValidPrivilege
    public ResponseDTO<PageResultDTO<NoticeVO>> queryByPage(@RequestBody NoticeQueryDTO queryDTO) {
        return noticeService.queryByPage(queryDTO);
    }

    @ApiOperation(value = "获取当前登录人的消息列表", notes = "获取当前登录人的消息列表")
    @PostMapping("/receive/page/query")
    @NoValidPrivilege
    public ResponseDTO<PageResultDTO<NoticeReceiveDTO>> queryReceiveByPage(@RequestBody NoticeReceiveQueryDTO queryDTO) {
        return noticeService.queryReceiveByPage(queryDTO, StandardRequestTokenUtil.getRequestUser());
    }

    @ApiOperation(value = "分页查询我的未读消息", notes = "分页查询我的未读消息")
    @PostMapping("/unread/page/query")
    @NoValidPrivilege
    public ResponseDTO<PageResultDTO<NoticeVO>> queryUnreadByPage(@RequestBody PageParamDTO queryDTO) {
        return noticeService.queryUnreadByPage(queryDTO, StandardRequestTokenUtil.getRequestUser());
    }

    @ApiOperation(value = "添加消息", notes = "添加消息")
    @PostMapping("/add")
    @NoValidPrivilege
    public ResponseDTO<String> add(@RequestBody @Valid NoticeAddDTO addTO) {
        return noticeService.add(addTO, StandardRequestTokenUtil.getRequestUser());
    }

    @ApiOperation(value = "修改消息", notes = "修改消息")
    @PostMapping("/update")
    @NoValidPrivilege
    public ResponseDTO<String> update(@RequestBody @Valid NoticeUpdateDTO updateDTO) {
        return noticeService.update(updateDTO);
    }

    @ApiOperation(value = "根据ID删除消息", notes = "根据ID删除消息")
    @GetMapping("/delete/{id}")
    @NoValidPrivilege
    public ResponseDTO<String> delete(@PathVariable(value = "id") Long id) {
        return noticeService.delete(id);
    }

    @ApiOperation(value = "根据ID查询消息详情", notes = "根据ID查询消息详情")
    @GetMapping("/detail/{id}")
    @NoValidPrivilege
    public ResponseDTO<NoticeDetailVO> detail(@PathVariable(value = "id") Long id) {
        return noticeService.detail(id);
    }

    @ApiOperation(value = "发送消息", notes = "发送消息")
    @GetMapping("/send/{id}")
    @NoValidPrivilege
    public ResponseDTO<NoticeDetailVO> send(@PathVariable(value = "id") Long id) {
        return noticeService.send(id, StandardRequestTokenUtil.getRequestUser());
    }

    @ApiOperation(value = "读取消息", notes = "读取消息")
    @GetMapping("/read/{id}")
    @NoValidPrivilege
    public ResponseDTO<NoticeDetailVO> read(@PathVariable(value = "id") Long id) {
        return noticeService.read(id, StandardRequestTokenUtil.getRequestUser());
    }

}
