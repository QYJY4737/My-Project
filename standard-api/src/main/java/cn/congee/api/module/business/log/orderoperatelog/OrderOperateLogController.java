package cn.congee.api.module.business.log.orderoperatelog;

import cn.congee.api.common.anno.OperateLog;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.constant.SwaggerTagConst;
import cn.congee.api.module.business.log.orderoperatelog.constant.OrderOperateLogOrderTypeEnum;
import cn.congee.api.module.business.log.orderoperatelog.domain.vo.OrderOperateLogVO;
import cn.congee.api.util.StandardStringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 单据操作记录Controller
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午8:59
 **/
@OperateLog
@RestController
@RequestMapping(value = "/orderOperateLog")
@Api(tags = {SwaggerTagConst.Admin.MANAGER_ORDER_OPERATE_LOG})
public class OrderOperateLogController {

    @Autowired
    private OrderOperateLogService orderOperateLogService;

    @ApiOperation(value = "查询单据操作日志", notes = "查询单据操作日志")
    @GetMapping("/orderOperateLog/list/{orderId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "orderId", value = "业务id", paramType = "path"), @ApiImplicitParam(name = "orderType", value = "业务类型" + OrderOperateLogOrderTypeEnum.INFO, paramType
            = "query")})
    public ResponseDTO<List<OrderOperateLogVO>> list(@PathVariable Long orderId, String orderType) {
        List<Integer> orderTypeList = StandardStringUtil.splitConverToIntSet(orderType, ",").stream().collect(Collectors.toList());
        return orderOperateLogService.listOrderOperateLogsByOrderTypeAndOrderId(orderId, orderTypeList);
    }

}
