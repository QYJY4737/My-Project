package cn.congee.api.module.system.role.roleprivilege;

import cn.congee.api.module.system.privilege.domain.entity.PrivilegeEntity;
import cn.congee.api.module.system.role.roleprivilege.domain.entity.RolePrivilegeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午6:09
 **/
@Mapper
@Component
public interface RolePrivilegeDao extends BaseMapper<RolePrivilegeEntity> {

    /**
     * 根据角色id删除
     * @param roleId
     */
    void deleteByRoleId(@Param(value = "roleId")Long roleId);

    /**
     * 删除权限所关联的角色信息
     * @param privilegeKeyList
     */
    void deleteByPrivilegeKey(@Param(value = "privilegeKeyList") List<String> privilegeKeyList);


    /**
     * 批量添加
     * @param rolePrivilegeList
     */
    void batchInsert(List<RolePrivilegeEntity> rolePrivilegeList);

    /**
     * 查询某批角色的权限
     * @param roleIds
     * @return
     */
    List<PrivilegeEntity> listByRoleIds(@Param(value = "roleIds")List<Long> roleIds, @Param(value = "normalStatus")Integer normalStatus);

    /**
     * 查询某个角色的权限
     * @param roleId
     * @return
     */
    List<PrivilegeEntity> listByRoleId(@Param(value = "roleId")Long roleId);

}
