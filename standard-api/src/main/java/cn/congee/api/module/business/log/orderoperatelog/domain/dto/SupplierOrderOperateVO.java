package cn.congee.api.module.business.log.orderoperatelog.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 预存款申请/提取单流水临时文件
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午9:02
 **/
@Data
public class SupplierOrderOperateVO {

    /**
     * 流水类型
     */
    private Integer tradingType;

    /**
     * 总重
     */
    private BigDecimal totalWeight;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 操作人名称
     */
    private String buyerName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

}
