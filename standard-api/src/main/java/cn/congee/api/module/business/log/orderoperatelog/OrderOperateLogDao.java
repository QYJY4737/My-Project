package cn.congee.api.module.business.log.orderoperatelog;

import cn.congee.api.module.business.log.orderoperatelog.domain.entity.OrderOperateLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 各种单据操作记录
 * Mapper 接口
 * </p>
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午6:50
 **/
@Mapper
@Component
public interface OrderOperateLogDao extends BaseMapper<OrderOperateLogEntity> {

    List<OrderOperateLogEntity> listOrderOperateLogsByOrderTypeAndOrderId(@Param("orderId") Long orderId, @Param("orderTypeList") List<Integer> orderTypeList);

    List<OrderOperateLogEntity> listOrderOperateLogsByOrderTypeAndOrderIds(@Param("orderIds") List<Long> orderIds, @Param("orderTypeList") List<Integer> orderTypeList);

    void batchInsert(List<OrderOperateLogEntity> list);

}
