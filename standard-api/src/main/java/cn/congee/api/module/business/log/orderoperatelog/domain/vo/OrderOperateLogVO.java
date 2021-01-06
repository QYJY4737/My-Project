package cn.congee.api.module.business.log.orderoperatelog.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 操作日志
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午8:58
 **/
@Data
public class OrderOperateLogVO {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("各种单据的id")
    private Long orderId;

    @ApiModelProperty("单据类型")
    private Integer orderType;

    @ApiModelProperty("操作类型")
    private Integer operateType;

    @ApiModelProperty("操作类型 对应的中文")
    private String operateContent;

    @ApiModelProperty("操作备注")
    private String operateRemark;

    @ApiModelProperty("操作备注,包含审批人名使用别名显示")
    private String operateSecondRemark;

    @ApiModelProperty("员工id")
    private Long employeeId;

    @ApiModelProperty("员工名称")
    private String employeeName;

    @ApiModelProperty("员工别名")
    private String employeeSecondName;

    @ApiModelProperty("额外信息")
    private String extData;

    @ApiModelProperty("创建时间")
    private Date createTime;

}
