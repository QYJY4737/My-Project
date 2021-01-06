package cn.congee.api.module.business.peony.dao;

import cn.congee.api.module.business.peony.domain.dto.PeonyQueryDTO;
import cn.congee.api.module.business.peony.domain.entity.PeonyEntity;
import cn.congee.api.module.business.peony.domain.vo.PeonyExcelVO;
import cn.congee.api.module.business.peony.domain.vo.PeonyVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * [ 牡丹花 ]
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午10:02
 **/
@Mapper
@Component
public interface PeonyDao extends BaseMapper<PeonyEntity> {

    /**
     * 分页查询
     * @param queryDTO
     * @return PeonyVO
     */
    IPage<PeonyVO> queryByPage(Page page, @Param(value = "queryDTO") PeonyQueryDTO queryDTO);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    void deleteById(@Param(value = "id") Long id);

    /**
     * 根据id批量删除
     * @param idList
     * @return
     */
    void deleteByIdList(@Param(value = "idList") List<Long> idList);

    /**
     * 查询所有导出数据
     * @param queryDTO
     * @return
     */
    List<PeonyExcelVO> queryAllExportData(@Param(value = "queryDTO") PeonyQueryDTO queryDTO);

    /**
     * 查询批量导出数据
     * @param idList
     * @return
     */
    List<PeonyExcelVO> queryBatchExportData(@Param(value = "idList") List<Long> idList);

}
