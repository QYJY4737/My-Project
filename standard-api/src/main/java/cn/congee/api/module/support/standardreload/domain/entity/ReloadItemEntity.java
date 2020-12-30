package cn.congee.api.module.support.standardreload.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * t_reload_item 数据表 实体类
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午5:54
 **/
@Data
@TableName(value = "t_reload_item")
public class ReloadItemEntity {

    /**
     * 加载项标签
     */
    @TableId(type = IdType.INPUT)
    private String tag;

    /**
     * 参数
     */
    private String args;

    /**
     * 运行标识
     */
    private String identification;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

}
