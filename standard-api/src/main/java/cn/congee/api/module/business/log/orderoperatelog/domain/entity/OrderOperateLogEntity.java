package cn.congee.api.module.business.log.orderoperatelog.domain.entity;

import cn.congee.api.common.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * 各种单据操作记录
 * </p>
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午6:51
 **/
@Data
@Builder
@TableName(value = "t_order_operate_log")
public class OrderOperateLogEntity extends BaseEntity {

    /**
     * 各种单据的id
     */
    private Long orderId;
    /**
     * 单据类型
     */
    private Integer orderType;
    /**
     * 操作类型
     */
    private Integer operateType;
    /**
     * 操作类型 对应的中文
     */
    private String operateContent;
    /**
     * 操作备注
     */
    private String operateRemark;
    /**
     * 员工id
     */
    private Long employeeId;
    /**
     * 员工名称
     */
    private String employeeName;
    /**
     * 额外信息
     */
    private String extData;

}
