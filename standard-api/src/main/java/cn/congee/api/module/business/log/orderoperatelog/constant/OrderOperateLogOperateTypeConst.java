package cn.congee.api.module.business.log.orderoperatelog.constant;

import cn.congee.api.common.constant.ResponseCodeConst;

/**
 * [ 8001 -8999 ]
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午3:06
 **/
public class OrderOperateLogOperateTypeConst extends ResponseCodeConst {

    public static final OrderOperateLogOperateTypeConst ADD = new OrderOperateLogOperateTypeConst(8001, "创建并提交");

    public static final OrderOperateLogOperateTypeConst UPDATE = new OrderOperateLogOperateTypeConst(8002, "修改并提交");

    public static final OrderOperateLogOperateTypeConst DELETE = new OrderOperateLogOperateTypeConst(8003, "删除");

    private OrderOperateLogOperateTypeConst(int code, String msg) {
        super(code, msg);
    }

}
