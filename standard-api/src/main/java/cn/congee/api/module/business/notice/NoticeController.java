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

    @ApiOperation(value = "分页查询全部消息", notes = "@author yandanyang")
    @PostMapping("/page/query")
    @NoValidPrivilege
    public ResponseDTO<PageResultDTO<NoticeVO>> queryByPage(@RequestBody NoticeQueryDTO queryDTO) {
        return noticeService.queryByPage(queryDTO);
    }

    @ApiOperation(value = "获取已收取的所有消息", notes = "@author yandanyang")
    @PostMapping("/receive/page/query")
    @NoValidPrivilege
    public ResponseDTO<PageResultDTO<NoticeReceiveDTO>> queryReceiveByPage(@RequestBody NoticeReceiveQueryDTO queryDTO) {
        return noticeService.queryReceiveByPage(queryDTO, StandardRequestTokenUtil.getRequestUser());
    }

    @ApiOperation(value = "分页查询未读消息", notes = "@author yandanyang")
    @PostMapping("/unread/page/query")
    @NoValidPrivilege
    public ResponseDTO<PageResultDTO<NoticeVO>> queryUnreadByPage(@RequestBody PageParamDTO queryDTO) {
        return noticeService.queryUnreadByPage(queryDTO, StandardRequestTokenUtil.getRequestUser());
    }

    @ApiOperation(value = "添加", notes = "@author yandanyang")
    @PostMapping("/add")
    @NoValidPrivilege
    public ResponseDTO<String> add(@RequestBody @Valid NoticeAddDTO addTO) {
        return noticeService.add(addTO, StandardRequestTokenUtil.getRequestUser());
    }

    @ApiOperation(value = "修改", notes = "@author yandanyang")
    @PostMapping("/update")
    @NoValidPrivilege
    public ResponseDTO<String> update(@RequestBody @Valid NoticeUpdateDTO updateDTO) {
        return noticeService.update(updateDTO);
    }

    @ApiOperation(value = "删除", notes = "@author yandanyang")
    @GetMapping("/delete/{id}")
    @NoValidPrivilege
    public ResponseDTO<String> delete(@PathVariable("id") Long id) {
        return noticeService.delete(id);
    }

    @ApiOperation(value = "详情", notes = "@author yandanyang")
    @GetMapping("/detail/{id}")
    @NoValidPrivilege
    public ResponseDTO<NoticeDetailVO> detail(@PathVariable("id") Long id) {
        return noticeService.detail(id);
    }

    @ApiOperation(value = "发送", notes = "@author yandanyang")
    @GetMapping("/send/{id}")
    @NoValidPrivilege
    public ResponseDTO<NoticeDetailVO> send(@PathVariable("id") Long id) {
        return noticeService.send(id, StandardRequestTokenUtil.getRequestUser());
    }

    @ApiOperation(value = "读取消息", notes = "@author yandanyang")
    @GetMapping("/read/{id}")
    @NoValidPrivilege
    public ResponseDTO<NoticeDetailVO> read(@PathVariable("id") Long id) {
        return noticeService.read(id, StandardRequestTokenUtil.getRequestUser());
    }

}
