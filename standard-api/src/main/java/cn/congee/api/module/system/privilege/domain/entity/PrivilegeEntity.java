package cn.congee.api.module.system.privilege.domain.entity;

import cn.congee.api.common.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午3:51
 **/
@Data
@TableName(value = "t_privilege")
public class PrivilegeEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 3848408566432915214L;

    /**
     * 功能权限类型：1.模块 2.页面 3.功能点 4.子模块
     */
    private Integer type;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 路由name 英文关键字
     */
    @TableField(value = "`key`")
    private String key;

    /**
     * url列表
     */
    private String url;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 父级key
     */
    private String parentKey;

}
