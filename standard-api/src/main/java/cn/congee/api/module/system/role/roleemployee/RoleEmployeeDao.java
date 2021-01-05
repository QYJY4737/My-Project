package cn.congee.api.module.system.role.roleemployee;

import cn.congee.api.module.system.employee.domain.dto.EmployeeDTO;
import cn.congee.api.module.system.role.basic.domain.dto.RoleQueryDTO;
import cn.congee.api.module.system.role.roleemployee.domain.RoleEmployeeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午3:33
 **/
@Mapper
@Component
public interface RoleEmployeeDao extends BaseMapper<RoleEmployeeEntity> {

    /**
     * 根据员工id 查询所有的角色
     * @param employeeId
     * @return
     */
    List<Long> selectRoleIdByEmployeeId(@Param(value = "employeeId") Long employeeId);

    /**
     *
     * @param page
     * @param queryDTO
     * @return
     */
    List<EmployeeDTO> selectEmployeeByNamePage(Page page, @Param(value = "queryDTO") RoleQueryDTO queryDTO);

    /**
     *
     * @param roleId
     * @return
     */
    List<EmployeeDTO> selectEmployeeByRoleId(@Param(value = "roleId") Long roleId);
    /**
     * 根据员工信息删除
     * @param employeeId
     */
    void deleteByEmployeeId(@Param(value = "employeeId") Long employeeId);

    /**
     * 删除某个角色的所有关系
     * @param roleId
     */
    void deleteByRoleId(@Param(value = "roleId")Long roleId);

    /**
     * 根据员工和 角色删除关系
     * @param employeeId
     * @param roleId
     */
    void deleteByEmployeeIdRoleId(@Param(value = "employeeId") Long employeeId, @Param(value = "roleId") Long roleId);

    /**
     * 批量删除某个角色下的某批用户的关联关系
     * @param roleId
     * @param employeeIds
     */
    void batchDeleteEmployeeRole(@Param(value = "roleId") Long roleId, @Param(value = "employeeIds") List<Long> employeeIds);

    /**
     * 批量新增
     * @param roleRelationList
     */
    void batchInsert(List<RoleEmployeeEntity> roleRelationList);

}
