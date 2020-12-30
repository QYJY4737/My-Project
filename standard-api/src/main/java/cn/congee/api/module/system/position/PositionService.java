package cn.congee.api.module.system.position;

import cn.congee.api.common.domain.PageResultDTO;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.module.system.position.domain.dto.*;
import cn.congee.api.module.system.position.domain.entity.PositionEntity;
import cn.congee.api.util.StandardBeanUtil;
import cn.congee.api.util.StandardPageUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
@Service
public class PositionService {

    @Autowired
    private PositionDao positionDao;

    /**
     * 查询岗位
     *
     * @param queryDTO
     * @return
     */
    public ResponseDTO<PageResultDTO<PositionResultVO>> queryPositionByPage(PositionQueryDTO queryDTO) {
        Page page = StandardPageUtil.convert2QueryPage(queryDTO);
        List<PositionEntity> entityList = positionDao.selectByPage(page, queryDTO);
        page.setRecords(entityList.stream().map(e -> StandardBeanUtil.copy(e, PositionResultVO.class)).collect(Collectors.toList()));
        PageResultDTO<PositionResultVO> pageResultDTO = StandardPageUtil.convert2PageResult(page);
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 新增岗位
     *
     * @param addDTO
     * @return
     */
    public ResponseDTO<String> addPosition(PositionAddDTO addDTO) {
        PositionEntity positionEntity = StandardBeanUtil.copy(addDTO, PositionEntity.class);
        positionDao.insert(positionEntity);
        return ResponseDTO.succ();
    }

    /**
     * 修改岗位
     *
     * @param updateDTO
     * @return
     */
    public ResponseDTO<String> updatePosition(PositionUpdateDTO updateDTO) {
        PositionEntity positionEntity = StandardBeanUtil.copy(updateDTO, PositionEntity.class);
        positionDao.updateById(positionEntity);
        return ResponseDTO.succ();
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    public ResponseDTO<PositionResultVO> queryPositionById(Long id) {
        return ResponseDTO.succData(StandardBeanUtil.copy(positionDao.selectById(id), PositionResultVO.class));
    }

    /**
     * 删除岗位
     */
    public ResponseDTO<String> removePosition(Long id) {
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
        PositionRelationQueryDTO positionRelationQueryDTO = new PositionRelationQueryDTO();
        positionRelationQueryDTO.setEmployeeId(employeeId);
        List<PositionRelationResultDTO> positionRelationList = positionDao.selectRelation(positionRelationQueryDTO);
        return positionRelationList;
    }

}
