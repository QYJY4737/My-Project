package cn.congee.api.module.system.datascope;

import cn.congee.api.module.system.datascope.domain.entity.DataScopeRoleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午2:14
 **/
@Mapper
@Component
public interface DataScopeRoleDao extends BaseMapper<DataScopeRoleEntity> {

    /**
     * 获取某个角色的设置信息
     * @param roleId
     * @return
     */
    List<DataScopeRoleEntity> listByRoleId(@Param("roleId") Long roleId);

    /**
     * 获取某批角色的所有数据范围配置信息
     * @param roleIdList
     * @return
     */
    List<DataScopeRoleEntity> listByRoleIdList(@Param("roleIdList") List<Long> roleIdList);

    /**
     * 删除某个角色的设置信息
     * @param roleId
     * @return
     */
    void deleteByRoleId(@Param("roleId") Long roleId);


    /**
     * 批量添加设置信息
     * @param list
     */
    void batchInsert(@Param("list")List<DataScopeRoleEntity> list);

}
