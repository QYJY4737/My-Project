package cn.congee.api.module.system.role.basic;

import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.module.system.role.basic.domain.dto.RoleAddDTO;
import cn.congee.api.module.system.role.basic.domain.dto.RoleUpdateDTO;
import cn.congee.api.module.system.role.basic.domain.dto.RoleVO;
import cn.congee.api.module.system.role.basic.domain.entity.RoleEntity;
import cn.congee.api.module.system.role.roleemployee.RoleEmployeeDao;
import cn.congee.api.module.system.role.roleprivilege.RolePrivilegeDao;
import cn.congee.api.util.StandardBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色管理业务
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午2:58
 **/
@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RolePrivilegeDao rolePrivilegeDao;

    @Autowired
    private RoleEmployeeDao roleEmployeeDao;

    /**
     * 新增添加角色
     *
     * @param roleAddDTO
     * @return ResponseDTO
     */
    public ResponseDTO addRole(RoleAddDTO roleAddDTO) {
        RoleEntity employeeRoleEntity = roleDao.getByRoleName(roleAddDTO.getRoleName());
        if (null != employeeRoleEntity) {
            return ResponseDTO.wrap(RoleResponseCodeConst.ROLE_NAME_EXISTS);
        }
        RoleEntity roleEntity = StandardBeanUtil.copy(roleAddDTO, RoleEntity.class);
        roleDao.insert(roleEntity);
        return ResponseDTO.succ();
    }

    /**
     * 根据角色id 删除
     *
     * @param roleId
     * @return ResponseDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO deleteRole(Long roleId) {
        RoleEntity roleEntity = roleDao.selectById(roleId);
        if (null == roleEntity) {
            return ResponseDTO.wrap(RoleResponseCodeConst.ROLE_NOT_EXISTS);
        }
        roleDao.deleteById(roleId);
        rolePrivilegeDao.deleteByRoleId(roleId);
        roleEmployeeDao.deleteByRoleId(roleId);
        return ResponseDTO.succ();
    }

    /**
     * 更新角色
     *
     * @param roleUpdateDTO
     * @return ResponseDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> updateRole(RoleUpdateDTO roleUpdateDTO) {
        if (null == roleDao.selectById(roleUpdateDTO.getId())) {
            return ResponseDTO.wrap(RoleResponseCodeConst.ROLE_NOT_EXISTS);
        }
        RoleEntity employeeRoleEntity = roleDao.getByRoleName(roleUpdateDTO.getRoleName());
        if (null != employeeRoleEntity && ! employeeRoleEntity.getId().equals(roleUpdateDTO.getId())) {
            return ResponseDTO.wrap(RoleResponseCodeConst.ROLE_NAME_EXISTS);
        }
        RoleEntity roleEntity = StandardBeanUtil.copy(roleUpdateDTO, RoleEntity.class);
        roleDao.updateById(roleEntity);
        return ResponseDTO.succ();
    }

    /**
     * 根据id获取角色数据
     *
     * @param roleId
     * @return ResponseDTO<RoleDTO>
     */
    public ResponseDTO<RoleVO> getRoleById(Long roleId) {
        RoleEntity roleEntity = roleDao.selectById(roleId);
        if (null == roleEntity) {
            return ResponseDTO.wrap(RoleResponseCodeConst.ROLE_NOT_EXISTS);
        }
        RoleVO role = StandardBeanUtil.copy(roleEntity, RoleVO.class);
        return ResponseDTO.succData(role);
    }

    /**
     * 获取所有角色列表
     *
     * @return ResponseDTO
     */
    public ResponseDTO<List<RoleVO>> getAllRole() {
        List<RoleEntity> roleEntityList = roleDao.selectList(null);
        List<RoleVO> roleList = StandardBeanUtil.copyList(roleEntityList, RoleVO.class);
        return ResponseDTO.succData(roleList);
    }

}
