package cn.congee.api.module.support.standardreload.domain.dto;

import lombok.Data;

import java.util.Date;

/**
 * reload_result DTO 类
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午6:02
 **/
@Data
public class ReloadResultVO {

    /**
     * 加载项标签
     */
    private String tag;

    /**
     * 参数
     */
    private String args;

    /**
     *  状态标识
     */
    private String identification;

    /**
     * 运行结果
     */
    private Boolean result;

    /**
     * 异常
     */
    private String exception;

    /**
     * 创建时间
     */
    private Date createTime;

}
