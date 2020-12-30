package cn.congee.api.module.system.position;

import cn.congee.api.common.constant.ResponseCodeConst;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午3:07
 **/
public class PositionResponseCodeConst extends ResponseCodeConst {

    public static final PositionResponseCodeConst REMOVE_DEFINE = new PositionResponseCodeConst(13000, "还有人关联该岗位,不能删除");

    protected PositionResponseCodeConst(int code, String msg) {
        super(code, msg);
    }

}
