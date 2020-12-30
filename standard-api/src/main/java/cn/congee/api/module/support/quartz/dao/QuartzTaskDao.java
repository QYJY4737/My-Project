package cn.congee.api.module.support.quartz.dao;

import cn.congee.api.module.support.quartz.domain.dto.QuartzQueryDTO;
import cn.congee.api.module.support.quartz.domain.dto.QuartzTaskVO;
import cn.congee.api.module.support.quartz.domain.entity.QuartzTaskEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午11:50
 **/
@Mapper
@Component
public interface QuartzTaskDao extends BaseMapper<QuartzTaskEntity> {

    /**
     * 更新任务状态
     * @param taskId
     * @param taskStatus
     */
    void updateStatus(@Param("taskId") Integer taskId, @Param("taskStatus") Integer taskStatus);

    /**
     * 查询列表
     * @param queryDTO
     * @return
     */
    List<QuartzTaskVO> queryList(Page page, @Param("queryDTO") QuartzQueryDTO queryDTO);

}
