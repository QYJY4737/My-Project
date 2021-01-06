package cn.congee.api.module.support.standardreload.domain.vo;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("加载项标签")
    private String tag;

    @ApiModelProperty("参数")
    private String args;

    @ApiModelProperty("状态标识")
    private String identification;

    @ApiModelProperty("运行结果")
    private Boolean result;

    @ApiModelProperty("异常")
    private String exception;

    @ApiModelProperty("创建时间")
    private Date createTime;

}
