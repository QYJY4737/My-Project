package cn.congee.api.module.system.role.basic;

import cn.congee.api.module.system.role.basic.domain.entity.RoleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午6:34
 **/
@Mapper
@Component
public interface RoleDao extends BaseMapper<RoleEntity> {

    RoleEntity getByRoleName(@Param(value = "roleName") String name);

}
