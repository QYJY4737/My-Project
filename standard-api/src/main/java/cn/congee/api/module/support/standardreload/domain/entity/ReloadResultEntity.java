package cn.congee.api.module.support.standardreload.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午5:57
 **/
@Data
@TableName(value = "t_reload_result")
public class ReloadResultEntity {

    /**
     * 加载项标签
     */
    private String tag;

    /**
     * 运行标识
     */
    private String identification;

    /**
     * 参数
     */
    private String args;

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
