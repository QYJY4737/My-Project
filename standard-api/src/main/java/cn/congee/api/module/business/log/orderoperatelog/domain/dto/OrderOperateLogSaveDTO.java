package cn.congee.api.module.business.log.orderoperatelog.domain.dto;

import cn.congee.api.module.business.log.orderoperatelog.constant.OrderOperateLogOperateTypeConst;
import cn.congee.api.module.business.log.orderoperatelog.constant.OrderOperateLogOrderTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午8:56
 **/
@Data
public class OrderOperateLogSaveDTO {

    @ApiModelProperty("各种单据的id")
    private Long orderId;

    @ApiModelProperty("单据类型")
    private OrderOperateLogOrderTypeEnum orderType;

    @ApiModelProperty("操作类型")
    private OrderOperateLogOperateTypeConst operateType;

    @ApiModelProperty("操作类型 对应的中文")
    private String operateContent;

    @ApiModelProperty("操作备注")
    private String operateRemark;

    @ApiModelProperty("员工id")
    private Long employeeId;

    @ApiModelProperty("员工名称")
    private String employeeName;

    @ApiModelProperty("额外信息")
    private String extData;

    public OrderOperateLogSaveDTO() {
    }

    public OrderOperateLogSaveDTO(Long orderId, OrderOperateLogOrderTypeEnum orderType, OrderOperateLogOperateTypeConst operateType, String
            operateRemark, Long employeeId, String employeeName, String extData) {
        this.orderId = orderId;
        this.orderType = orderType;
        this.operateType = operateType;
        this.operateRemark = operateRemark;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.extData = extData;
    }

    @Override
    public String toString() {
        return "OrderOperateLogSaveDTO{" + "orderId=" + orderId + ", orderType=" + orderType + ", operateType=" + operateType + ", operateRemark='"
                + operateRemark + '\'' + ", employeeId=" + employeeId + ", employeeName='" + employeeName + '\'' + ", extData='" + extData + '\'' + '}';
    }

}
