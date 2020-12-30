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
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        log.info("通过角色id，分页获取成员员工列表接口入参为queryDTO=[{}]", JSON.toJSONString(queryDTO));
        Page page = StandardPageUtil.convert2QueryPage(queryDTO);
        List<EmployeeDTO> employeeDTOS = roleEmployeeDao.selectEmployeeByNamePage(page, queryDTO);
        employeeDTOS.stream().filter(e -> e.getDepartmentId() != null).forEach(employeeDTO -> {
            DepartmentEntity departmentEntity = departmentDao.selectById(employeeDTO.getDepartmentId());
            employeeDTO.setDepartmentName(departmentEntity.getName());
        });
        PageResultDTO<EmployeeVO> pageResultDTO = StandardPageUtil.convert2PageResult(page, employeeDTOS, EmployeeVO.class);
        log.info("通过角色id，分页获取成员员工列表接口出参为pageResultDTO=[{}]", JSON.toJSONString(pageResultDTO));
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 根据角色id获取角色员工列表(无分页)
     *
     * @param roleId
     * @return
     */
    public ResponseDTO<List<EmployeeVO>> getAllEmployeeByRoleId(Long roleId) {
        log.info("根据角色id获取角色员工列表(无分页)接口入参为roleId=[{}]", roleId);
        List<EmployeeDTO> employeeDTOS = roleEmployeeDao.selectEmployeeByRoleId(roleId);
        List<EmployeeVO> list = StandardBeanUtil.copyList(employeeDTOS, EmployeeVO.class);
        log.info("根据角色id获取角色员工列表(无分页)接口入参为list=[{}]", JSON.toJSONString(list));
        return ResponseDTO.succData(list);
    }

    /**
     * 从角色成员列表中移除员工
     *
     * @param employeeId
     * @param roleId
     * @return ResponseDTO<String>
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> removeEmployeeRole(Long employeeId, Long roleId) {
        log.info("从角色成员列表中移除员工接口入参为employeeId=[{}],roleId=[{}]", employeeId, roleId);
        if (null == employeeId || null == roleId) {
            return ResponseDTO.wrap(RoleResponseCodeConst.ERROR_PARAM);
        }
        roleEmployeeDao.deleteByEmployeeIdRoleId(employeeId, roleId);
        return ResponseDTO.succ();
    }

    /**
     * 从角色成员列表中批量移除员工
     *
     * @param removeDTO
     * @return ResponseDTO<String>
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> batchRemoveEmployeeRole(RoleBatchDTO removeDTO) {
        log.info("从角色成员列表中批量移除员工接口入参为removeDTO=[{}]", JSON.toJSONString(removeDTO));
        List<Long> employeeIdList = removeDTO.getEmployeeIds();
        roleEmployeeDao.batchDeleteEmployeeRole(removeDTO.getRoleId(), employeeIdList);
        return ResponseDTO.succ();
    }

    /**
     * 角色成员列表中批量添加员工
     *
     * @param addDTO
     * @return ResponseDTO<String>
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> batchAddEmployeeRole(RoleBatchDTO addDTO) {
        log.info("角色成员列表中批量添加员工接口入参为addDTO=[{}]", JSON.toJSONString(addDTO));
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
     * 通过员工id获取所有角色以及员工具有的角色
     *
     * @param employeeId
     * @return
     */
    public ResponseDTO<List<RoleSelectedVO>> getRolesByEmployeeId(Long employeeId) {
        log.info("通过员工id获取所有角色以及员工具有的角色接口入参为employeeId=[{}]", employeeId);
        List<Long> roleIds = roleEmployeeDao.selectRoleIdByEmployeeId(employeeId);
        List<RoleEntity> roleList = roleDao.selectList(null);
        List<RoleSelectedVO> result = StandardBeanUtil.copyList(roleList, RoleSelectedVO.class);
        result.stream().forEach(item -> item.setSelected(roleIds.contains(item.getId())));
        log.info("通过员工id获取所有角色以及员工具有的角色接口出参为result=[{}]", JSON.toJSONString(result));
        return ResponseDTO.succData(result);
    }

}
