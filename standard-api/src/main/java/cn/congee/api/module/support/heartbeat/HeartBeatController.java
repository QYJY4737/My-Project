package cn.congee.api.module.support.heartbeat;

import cn.congee.api.common.anno.OperateLog;
import cn.congee.api.common.domain.PageParamDTO;
import cn.congee.api.common.domain.PageResultDTO;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.constant.SwaggerTagConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午11:35
 **/
@OperateLog
@RestController
@RequestMapping(value = "/heartBeat")
@Api(tags = {SwaggerTagConst.Admin.MANAGER_HEART_BEAT})
public class HeartBeatController {

    @Autowired
    private HeartBeatService heartBeatService;

    @PostMapping("/query")
    @ApiOperation("查询心跳记录 @author wgb")
    public ResponseDTO<PageResultDTO<HeartBeatRecordVO>> query(@RequestBody @Valid PageParamDTO pageParamDTO){
        return heartBeatService.pageQuery(pageParamDTO);
    }

}
