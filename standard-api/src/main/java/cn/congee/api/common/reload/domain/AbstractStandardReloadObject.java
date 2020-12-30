package cn.congee.api.common.reload.domain;

import cn.congee.api.common.reload.domain.entity.ReloadItem;
import cn.congee.api.common.reload.domain.entity.SmartReloadResult;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * AbstractSmartReloadObject 处理程序的抽象类
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午3:58
 **/
public abstract class AbstractStandardReloadObject {

    protected String getStackTrace(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    /**
     * 通过reloadItem参数reload，获得结果
     *
     * @param reloadItem
     * @return boolean
     * @author zhuokongming
     * @date 2016年10月21日 下午2:09:44
     */
    public abstract SmartReloadResult reload(ReloadItem reloadItem);

}
