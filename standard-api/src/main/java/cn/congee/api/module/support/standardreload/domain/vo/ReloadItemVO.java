package cn.congee.api.module.support.standardreload.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * initDefines 项 VO 类
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午6:00
 **/
@Data
public class ReloadItemVO {

    @ApiModelProperty("加载项标签")
    private String tag;

    @ApiModelProperty("参数")
    private String args;

    @ApiModelProperty("状态标识")
    private String identification;

    @ApiModelProperty("最后更新时间")
    private Date updateTime;

    @ApiModelProperty("创建时间")
    private Date createTime;

}
