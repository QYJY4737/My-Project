package cn.congee.api.module.business.email;

import cn.congee.api.module.business.email.domain.dto.EmailQueryDTO;
import cn.congee.api.module.business.email.domain.entity.EmailEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午8:27
 **/
@Mapper
@Component
public interface EmailDao extends BaseMapper<EmailEntity> {

    /**
     * 分页查询
     * @param queryDTO
     * @return EmailEntity
     */
    List<EmailEntity> queryByPage(Page page, @Param(value = "queryDTO") EmailQueryDTO queryDTO);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    void deleteById(@Param(value = "id") Long id);

    /**
     * 批量删除
     * @param idList
     * @return
     */
    void deleteByIds(@Param(value = "idList") List<Long> idList);

}
