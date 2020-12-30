package cn.congee.api.module.system.position.domain.dto;

import lombok.Data;

import java.util.Date;

/**
 * 岗位关联关系
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午3:17
 **/
@Data
public class PositionRelationResultDTO {

    /**
     * 岗位ID
     */
    private Long positionId;

    /**
     * 员工ID
     */
    private Long employeeId;

    /**
     * 岗位名称
     */
    private String positionName;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

}
