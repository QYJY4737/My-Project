package cn.congee.api.module.system.datascope.domain.entity;

import cn.congee.api.common.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * [ 数据范围与角色关系 ]
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午2:05
 **/
@Data
@TableName("t_role_data_scope")
public class DataScopeRoleEntity extends BaseEntity {

    /**
     * 数据范围id
     */
    private Integer dataScopeType;

    /**
     * 数据范围类型
     */
    private Integer viewType;

    /**
     * 角色id
     */
    private Long roleId;

}
