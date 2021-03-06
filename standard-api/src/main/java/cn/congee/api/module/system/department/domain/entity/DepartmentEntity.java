package cn.congee.api.module.system.department.domain.entity;

import cn.congee.api.common.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 部门实体类
 * t_department 数据表
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午3:26
 **/
@Data
@TableName(value = "t_department")
public class DepartmentEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -6787726615141147044L;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门简称
     */
    private String shortName;

    /**
     * 负责人员工 id
     */
    private Long managerId;

    /**
     * 部门父级id
     */
    private Long parentId;

    /**
     * 排序
     */
    private Long sort;

}
