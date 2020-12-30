package cn.congee.api.module.system.systemconfig.constant;

import cn.congee.api.util.StandardVerificationUtil;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午6:17
 **/
public enum  SystemConfigDataType {

    /**
     * 整数
     */
    INTEGER(StandardVerificationUtil.INTEGER),
    /**
     * 文本
     */
    TEXT(null),
    /**
     * url地址
     */
    URL(StandardVerificationUtil.URL),
    /**
     *  邮箱
     */
    EMAIL(StandardVerificationUtil.EMAIL),
    /**
     * JSON 字符串
     */
    JSON(null),
    /**
     * 2019-08
     */
    YEAR_MONTH(StandardVerificationUtil.YEAR_MONTH),
    /**
     * 2019-08-01
     */
    DATE(StandardVerificationUtil.DATE),
    /**
     * 2019-08-01 10:23
     */
    DATE_TIME(StandardVerificationUtil.DATE_TIME),
    /**
     * 10:23-10:56
     */
    TIME_SECTION(StandardVerificationUtil.TIME_SECTION),
    /**
     * 10:23
     */
    TIME(StandardVerificationUtil.TIME);

    private String valid;


    SystemConfigDataType(String valid){
        this.valid = valid;
    }

    public String getValid() {
        return valid;
    }

}
