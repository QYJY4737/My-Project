package cn.congee.api.module.system.role.roleemployee;

import cn.congee.api.common.domain.PageResultDTO;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.module.system.department.DepartmentDao;
import cn.congee.api.module.system.department.domain.entity.DepartmentEntity;
import cn.congee.api.module.system.employee.domain.dto.EmployeeDTO;
import cn.congee.api.module.system.employee.domain.vo.EmployeeVO;
import cn.congee.api.module.system.role.basic.RoleDao;
import cn.congee.api.module.system.role.basic.RoleResponseCodeConst;
import cn.congee.api.module.system.role.basic.domain.dto.RoleBatchDTO;
import cn.congee.api.module.system.role.basic.domain.dto.RoleQueryDTO;
import cn.congee.api.module.system.role.basic.domain.dto.RoleSelectedVO;
import cn.congee.api.module.system.role.basic.domain.entity.RoleEntity;
import cn.congee.api.module.system.role.roleemployee.domain.RoleEmployeeEntity;
import cn.congee.api.util.StandardBeanUtil;
import cn.congee.api.util.StandardPageUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色管理业务
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午3:01
 **/
@Service
public class RoleEmployeeService {

    @Autowired
    private RoleEmployeeDao roleEmployeeDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private DepartmentDao departmentDao;

    /**
     * 通过角色id，分页获取成员员工列表
     *
     * @param queryDTO
     * @return
     */
    public ResponseDTO<PageResultDTO<EmployeeVO>> listEmployeeByName(RoleQueryDTO queryDTO) {
        Page page = StandardPageUtil.convert2QueryPage(queryDTO);
        List<EmployeeDTO> employeeDTOS = roleEmployeeDao.selectEmployeeByNamePage(page, queryDTO);
        employeeDTOS.stream().filter(e -> e.getDepartmentId() != null).forEach(employeeDTO -> {
            DepartmentEntity departmentEntity = departmentDao.selectById(employeeDTO.getDepartmentId());
            employeeDTO.setDepartmentName(departmentEntity.getName());
        });
        PageResultDTO<EmployeeVO> pageResultDTO = StandardPageUtil.convert2PageResult(page, employeeDTOS, EmployeeVO.class);
        return ResponseDTO.succData(pageResultDTO);
    }

    public ResponseDTO<List<EmployeeVO>> getAllEmployeeByRoleId(Long roleId) {
        List<EmployeeDTO> employeeDTOS = roleEmployeeDao.selectEmployeeByRoleId(roleId);
        List<EmployeeVO> list = StandardBeanUtil.copyList(employeeDTOS, EmployeeVO.class);
        return ResponseDTO.succData(list);
    }

    /**
     * 移除员工角色
     *
     * @param employeeId
     * @param roleId
     * @return ResponseDTO<String>
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> removeEmployeeRole(Long employeeId, Long roleId) {
        if (null == employeeId || null == roleId) {
            return ResponseDTO.wrap(RoleResponseCodeConst.ERROR_PARAM);
        }
        roleEmployeeDao.deleteByEmployeeIdRoleId(employeeId, roleId);
        return ResponseDTO.succ();
    }

    /**
     * 批量删除角色的成员员工
     *
     * @param removeDTO
     * @return ResponseDTO<String>
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> batchRemoveEmployeeRole(RoleBatchDTO removeDTO) {
        List<Long> employeeIdList = removeDTO.getEmployeeIds();
        roleEmployeeDao.batchDeleteEmployeeRole(removeDTO.getRoleId(), employeeIdList);
        return ResponseDTO.succ();
    }

    /**
     * 批量添加角色的成员员工
     *
     * @param addDTO
     * @return ResponseDTO<String>
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> batchAddEmployeeRole(RoleBatchDTO addDTO) {
        Long roleId = addDTO.getRoleId();
        List<Long> employeeIdList = addDTO.getEmployeeIds();
        roleEmployeeDao.deleteByRoleId(roleId);
        List<RoleEmployeeEntity> roleRelationEntities = Lists.newArrayList();
        RoleEmployeeEntity employeeRoleRelationEntity;
        for (Long employeeId : employeeIdList) {
            employeeRoleRelationEntity = new RoleEmployeeEntity();
            employeeRoleRelationEntity.setRoleId(roleId);
            employeeRoleRelationEntity.setEmployeeId(employeeId);
            roleRelationEntities.add(employeeRoleRelationEntity);
        }
        roleEmployeeDao.batchInsert(roleRelationEntities);
        return ResponseDTO.succ();
    }

    /**
     * 通过员工id获取员工角色
     *
     * @param employeeId
     * @return
     */
    public ResponseDTO<List<RoleSelectedVO>> getRolesByEmployeeId(Long employeeId) {
        List<Long> roleIds = roleEmployeeDao.selectRoleIdByEmployeeId(employeeId);
        List<RoleEntity> roleList = roleDao.selectList(null);
        List<RoleSelectedVO> result = StandardBeanUtil.copyList(roleList, RoleSelectedVO.class);
        result.stream().forEach(item -> item.setSelected(roleIds.contains(item.getId())));
        return ResponseDTO.succData(result);
    }

}
