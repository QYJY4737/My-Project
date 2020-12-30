package cn.congee.api.module.support.quartz.dao;

import cn.congee.api.module.support.quartz.domain.dto.QuartzLogQueryDTO;
import cn.congee.api.module.support.quartz.domain.dto.QuartzTaskLogVO;
import cn.congee.api.module.support.quartz.domain.entity.QuartzTaskLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午11:51
 **/
@Mapper
@Component
public interface QuartzTaskLogDao extends BaseMapper<QuartzTaskLogEntity> {

    /**
     * 查询列表
     * @param queryDTO
     * @return
     */
    List<QuartzTaskLogVO> queryList(Page page, @Param("queryDTO") QuartzLogQueryDTO queryDTO);

}
