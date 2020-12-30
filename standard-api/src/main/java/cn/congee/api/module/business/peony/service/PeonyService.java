package cn.congee.api.module.business.peony.service;

import cn.congee.api.common.domain.PageResultDTO;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.module.business.peony.dao.PeonyDao;
import cn.congee.api.module.business.peony.domain.dto.PeonyAddDTO;
import cn.congee.api.module.business.peony.domain.dto.PeonyQueryDTO;
import cn.congee.api.module.business.peony.domain.dto.PeonyUpdateDTO;
import cn.congee.api.module.business.peony.domain.entity.PeonyEntity;
import cn.congee.api.module.business.peony.domain.vo.PeonyExcelVO;
import cn.congee.api.module.business.peony.domain.vo.PeonyVO;
import cn.congee.api.util.StandardBeanUtil;
import cn.congee.api.util.StandardPageUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * [ 牡丹花 ]
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午10:04
 **/
@Slf4j
@Service
public class PeonyService {

    @Autowired
    private PeonyDao peonyDao;

    /**
     * 根据id查询
     */
    public PeonyEntity getById(Long id){
        return  peonyDao.selectById(id);
    }

    /**
     * 分页查询牡丹花
     * @param queryDTO
     * @return
     */
    public ResponseDTO<PageResultDTO<PeonyVO>> queryByPage(PeonyQueryDTO queryDTO) {
        log.info("分页查询牡丹花接口入参为queryDTO=[{}]", JSON.toJSONString(queryDTO));
        Page page = StandardPageUtil.convert2QueryPage(queryDTO);
        IPage<PeonyVO> voList = peonyDao.queryByPage(page, queryDTO);
        PageResultDTO<PeonyVO> pageResultDTO = StandardPageUtil.convert2PageResult(voList);
        log.info("分页查询牡丹花接口出参为pageResultDTO=[{}]", JSON.toJSONString(pageResultDTO));
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 添加牡丹花
     * @param addDTO
     * @return
     */
    public ResponseDTO<String> add(PeonyAddDTO addDTO) {
        log.info("添加牡丹花接口入参为addDTO=[{}]", JSON.toJSONString(addDTO));
        PeonyEntity entity = StandardBeanUtil.copy(addDTO, PeonyEntity.class);
        peonyDao.insert(entity);
        return ResponseDTO.succ();
    }

    /**
     * 修改牡丹花
     * @param updateDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> update(PeonyUpdateDTO updateDTO) {
        log.info("修改牡丹花接口入参为updateDTO=[{}]", JSON.toJSONString(updateDTO));
        PeonyEntity entity = StandardBeanUtil.copy(updateDTO, PeonyEntity.class);
        peonyDao.updateById(entity);
        return ResponseDTO.succ();
    }

    /**
     * 批量删除牡丹花
     * @param idList
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> deleteByIds(List<Long> idList) {
        log.info("批量删除牡丹花接口入参为idList=[{}]", JSON.toJSONString(idList));
        peonyDao.deleteByIdList(idList);
        return ResponseDTO.succ();
    }

    /**
     * 查询全部导出对象
     * @param queryDTO
     * @return
     */
    public List<PeonyExcelVO> queryAllExportData(PeonyQueryDTO queryDTO) {
        log.info("查询全部导出对象接口入参为queryDTO=[{}]", JSON.toJSONString(queryDTO));
        List<PeonyExcelVO> peonyExcelVOS = peonyDao.queryAllExportData(queryDTO);
        log.info("查询全部导出对象接口出参为peonyExcelVOS=[{}]", JSON.toJSONString(peonyExcelVOS));
        return peonyExcelVOS;
    }

    /**
     * 批量查询导出对象
     * @param idList
     * @return
     */
    public List<PeonyExcelVO> queryBatchExportData(List<Long> idList) {
        log.info("批量查询导出对象接口入参为idList=[{}]", JSON.toJSONString(idList));
        List<PeonyExcelVO> peonyExcelVOS = peonyDao.queryBatchExportData(idList);
        log.info("批量查询导出对象接口出参为peonyExcelVOS=[{}]", JSON.toJSONString(peonyExcelVOS));
        return peonyExcelVOS;
    }

}
