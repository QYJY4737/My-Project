package cn.congee.api.module.business.log.orderoperatelog;

import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.module.business.log.orderoperatelog.domain.dto.OrderOperateLogSaveDTO;
import cn.congee.api.module.business.log.orderoperatelog.domain.entity.OrderOperateLogEntity;
import cn.congee.api.module.business.log.orderoperatelog.domain.vo.OrderOperateLogVO;
import cn.congee.api.util.StandardBeanUtil;
import cn.congee.api.util.StandardStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 各种单据操作记录
 * 服务实现类
 * </p>
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午8:55
 **/
@Service
public class OrderOperateLogService {

    @Autowired
    private OrderOperateLogDao orderOperateLogDao;

    public void batchSaveOrderOperateLog(List<OrderOperateLogSaveDTO> orderOperateLogSaveDTOList) {
        List<OrderOperateLogEntity> entityList = new ArrayList<>();
        orderOperateLogSaveDTOList.forEach(e -> {
            OrderOperateLogEntity orderOperateLogEntity = StandardBeanUtil.copy(e, OrderOperateLogEntity.class);
            orderOperateLogEntity.setOperateType(e.getOperateType().getCode());
            if (StandardStringUtil.isNotBlank(e.getOperateContent())) {
                orderOperateLogEntity.setOperateContent(e.getOperateContent());
            } else {
                orderOperateLogEntity.setOperateContent(e.getOperateType().getMsg());
            }
            orderOperateLogEntity.setOperateRemark(e.getOperateRemark());
            orderOperateLogEntity.setExtData(e.getExtData());
            orderOperateLogEntity.setCreateTime(new Date());
            orderOperateLogEntity.setOrderType(e.getOrderType().getType());
            entityList.add(orderOperateLogEntity);
        });
        //批量添加
        orderOperateLogDao.batchInsert(entityList);
    }

    public ResponseDTO<List<OrderOperateLogVO>> listOrderOperateLogsByOrderTypeAndOrderId(Long orderId, List<Integer> orderTypeList) {
        List<OrderOperateLogEntity> orderOperateLogEntities = orderOperateLogDao.listOrderOperateLogsByOrderTypeAndOrderId(orderId, orderTypeList);
        List<OrderOperateLogVO> dtoList = orderOperateLogEntities.stream().map(e -> StandardBeanUtil.copy(e, OrderOperateLogVO.class)).collect(Collectors.toList());
        return ResponseDTO.succData(dtoList);
    }

    public ResponseDTO<List<OrderOperateLogVO>> listOrderOperateLogsByOrderTypeAndOrderIds(List<Long> orderIds, List<Integer> orderTypeList) {
        List<OrderOperateLogEntity> orderOperateLogEntities = orderOperateLogDao.listOrderOperateLogsByOrderTypeAndOrderIds(orderIds, orderTypeList);
        List<OrderOperateLogVO> dtoList = orderOperateLogEntities.stream().map(e -> StandardBeanUtil.copy(e, OrderOperateLogVO.class)).collect(Collectors.toList());
        return ResponseDTO.succData(dtoList);
    }

}
