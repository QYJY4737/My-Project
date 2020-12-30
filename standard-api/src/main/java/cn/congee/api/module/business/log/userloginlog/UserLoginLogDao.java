package cn.congee.api.module.business.log.userloginlog;

import cn.congee.api.module.business.log.userloginlog.domain.UserLoginLogEntity;
import cn.congee.api.module.business.log.userloginlog.domain.UserLoginLogQueryDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * [ 用户登录日志 ]
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午6:46
 **/
@Mapper
@Component
public interface UserLoginLogDao extends BaseMapper<UserLoginLogEntity> {

    /**
     * 分页查询
     * @param queryDTO
     * @return UserLoginLogEntity
     */
    List<UserLoginLogEntity> queryByPage(Page page, @Param("queryDTO") UserLoginLogQueryDTO queryDTO);

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
