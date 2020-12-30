package cn.congee.api.module.system.position;

import cn.congee.api.common.domain.PageResultDTO;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.module.system.position.domain.dto.*;
import cn.congee.api.module.system.position.domain.entity.PositionEntity;
import cn.congee.api.util.StandardBeanUtil;
import cn.congee.api.util.StandardPageUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午3:37
 **/
@Slf4j
@Service
public class PositionService {

    @Autowired
    private PositionDao positionDao;

    /**
     * 分页查询所有岗位
     *
     * @param queryDTO
     * @return
     */
    public ResponseDTO<PageResultDTO<PositionResultVO>> queryPositionByPage(PositionQueryDTO queryDTO) {
        log.info("分页查询所有岗位接口入参为queryDTO=[{}]", JSON.toJSONString(queryDTO));
        Page page = StandardPageUtil.convert2QueryPage(queryDTO);
        List<PositionEntity> entityList = positionDao.selectByPage(page, queryDTO);
        page.setRecords(entityList.stream().map(e -> StandardBeanUtil.copy(e, PositionResultVO.class)).collect(Collectors.toList()));
        PageResultDTO<PositionResultVO> pageResultDTO = StandardPageUtil.convert2PageResult(page);
        log.info("分页查询所有岗位接口出参为pageResultDTO=[{}]", JSON.toJSONString(pageResultDTO));
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 新增岗位
     *
     * @param addDTO
     * @return
     */
    public ResponseDTO<String> addPosition(PositionAddDTO addDTO) {
        log.info("新增岗位接口入参为addDTO=[{}]", JSON.toJSONString(addDTO));
        PositionEntity positionEntity = StandardBeanUtil.copy(addDTO, PositionEntity.class);
        positionDao.insert(positionEntity);
        return ResponseDTO.succ();
    }

    /**
     * 更新岗位
     *
     * @param updateDTO
     * @return
     */
    public ResponseDTO<String> updatePosition(PositionUpdateDTO updateDTO) {
        log.info("更新岗位接口入参为updateDTO=[{}]", JSON.toJSONString(updateDTO));
        PositionEntity positionEntity = StandardBeanUtil.copy(updateDTO, PositionEntity.class);
        positionDao.updateById(positionEntity);
        return ResponseDTO.succ();
    }

    /**
     * 根据ID查询岗位
     *
     * @param id
     * @return
     */
    public ResponseDTO<PositionResultVO> queryPositionById(Long id) {
        log.info("根据ID查询岗位接口入参为id=[{}]", id);
        PositionResultVO positionResultVO = StandardBeanUtil.copy(positionDao.selectById(id), PositionResultVO.class);
        log.info("根据ID查询岗位接口出参为positionResultVO=[{}]", JSON.toJSONString(positionResultVO));
        return ResponseDTO.succData(positionResultVO);
    }

    /**
     * 根据ID删除岗位
     */
    public ResponseDTO<String> removePosition(Long id) {
        log.info("根据ID删除岗位接口入参为id=[{}]", id);
        //查询是否还有人关联该岗位
        PositionRelationQueryDTO positionRelationQueryDTO = new PositionRelationQueryDTO();
        positionRelationQueryDTO.setPositionId(id);
        List<PositionRelationResultDTO> dtoList = positionDao.selectRelation(positionRelationQueryDTO);
        if (CollectionUtils.isNotEmpty(dtoList)) {
            return ResponseDTO.wrap(PositionResponseCodeConst.REMOVE_DEFINE);
        }
        positionDao.deleteById(id);
        return ResponseDTO.succ();
    }

    /**
     * 添加岗位关联关系
     *
     * @param positionRelAddDTO
     * @return
     */
    public ResponseDTO<String> addPositionRelation(PositionRelationAddDTO positionRelAddDTO) {
        positionDao.insertBatchRelation(positionRelAddDTO);
        return ResponseDTO.succ();
    }

    /**
     * 删除指定用户的岗位关联关系
     *
     * @param employeeId
     * @return
     */
    public ResponseDTO<String> removePositionRelation(Long employeeId) {
        positionDao.deleteRelationByEmployeeId(employeeId);
        return ResponseDTO.succ();
    }

    /**
     * 根据员工ID查询 所关联的岗位信息
     *
     * @param employeeId
     * @return
     */
    public List<PositionRelationResultDTO> queryPositionByEmployeeId(Long employeeId) {
        log.info("根据员工ID查询 所关联的岗位信息接口入参为employeeId=[{}]", employeeId);
        PositionRelationQueryDTO positionRelationQueryDTO = new PositionRelationQueryDTO();
        positionRelationQueryDTO.setEmployeeId(employeeId);
        List<PositionRelationResultDTO> positionRelationList = positionDao.selectRelation(positionRelationQueryDTO);
        log.info("根据员工ID查询 所关联的岗位信息接口出参为positionRelationList=[{}]", JSON.toJSONString(positionRelationList));
        return positionRelationList;
    }

}
