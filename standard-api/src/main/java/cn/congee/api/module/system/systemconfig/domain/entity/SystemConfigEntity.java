package cn.congee.api.module.system.systemconfig.domain.entity;

import cn.congee.api.common.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统配置参数 实体类
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午3:53
 **/
@Data
@TableName(value = "t_system_config")
public class SystemConfigEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 257284726400352502L;

    /**
     * 参数key
     */
    private String configKey;

    /**
     * 参数的值
     */
    private String configValue;

    /**
     * 参数名称
     */
    private String configName;

    /**
     * 参数类别
     */
    private String configGroup;

    /**
     * 是否使用0 是 1否
     */
    private Integer isUsing;

    /**
     * 备注
     */
    private String remark;

}
