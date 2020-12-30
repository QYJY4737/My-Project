package cn.congee.api.module.business.log.useroperatelog;

import cn.congee.api.module.business.log.useroperatelog.domain.UserOperateLogEntity;
import cn.congee.api.module.business.log.useroperatelog.domain.UserOperateLogQueryDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午6:54
 **/
@Mapper
@Component
public interface UserOperateLogDao extends BaseMapper<UserOperateLogEntity> {

    /**
     * 分页查询
     * @param queryDTO
     * @return UserOperateLogEntity
     */
    List<UserOperateLogEntity> queryByPage(Page page, @Param("queryDTO") UserOperateLogQueryDTO queryDTO);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    void deleteById(@Param("id") Long id);

    /**
     * 批量删除
     * @param idList
     * @return
     */
    void deleteByIds(@Param("idList") List<Long> idList);

}
