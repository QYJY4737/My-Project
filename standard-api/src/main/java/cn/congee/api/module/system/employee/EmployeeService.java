package cn.congee.api.module.system.employee;

import cn.congee.api.common.constant.JudgeEnum;
import cn.congee.api.common.domain.PageResultDTO;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.constant.CommonConst;
import cn.congee.api.module.system.department.DepartmentDao;
import cn.congee.api.module.system.department.domain.entity.DepartmentEntity;
import cn.congee.api.module.system.employee.constant.EmployeeResponseCodeConst;
import cn.congee.api.module.system.employee.constant.EmployeeStatusEnum;
import cn.congee.api.module.system.employee.domain.bo.EmployeeBO;
import cn.congee.api.module.system.employee.domain.dto.*;
import cn.congee.api.module.system.employee.domain.entity.EmployeeEntity;
import cn.congee.api.module.system.employee.domain.vo.EmployeeVO;
import cn.congee.api.module.system.login.domain.RequestTokenBO;
import cn.congee.api.module.system.position.PositionDao;
import cn.congee.api.module.system.position.PositionService;
import cn.congee.api.module.system.position.domain.dto.PositionRelationAddDTO;
import cn.congee.api.module.system.position.domain.dto.PositionRelationResultDTO;
import cn.congee.api.module.system.privilege.service.PrivilegeEmployeeService;
import cn.congee.api.module.system.role.roleemployee.RoleEmployeeDao;
import cn.congee.api.module.system.role.roleemployee.domain.RoleEmployeeEntity;
import cn.congee.api.util.StandardBeanUtil;
import cn.congee.api.util.StandardDigestUtil;
import cn.congee.api.util.StandardPageUtil;
import cn.congee.api.util.StandardVerificationUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 员工管理
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午3:11
 **/
@Slf4j
@Service
public class EmployeeService {

    private static final String RESET_PASSWORD = "123456";

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private RoleEmployeeDao roleEmployeeDao;

    @Autowired
    private PositionService positionService;

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private PrivilegeEmployeeService privilegeEmployeeService;

    /**
     * 员工基本信息的缓存
     */
    private static final ConcurrentHashMap<Long, EmployeeBO> employeeCache = new ConcurrentHashMap<>();

    public EmployeeService() {
    }

    /**
     * 查询所有员工基本信息，用于选择框
     *
     * @return
     */
    public List<EmployeeVO> getAllEmployee(){
        log.info("查询所有员工基本信息，用于选择框接口入参为空");
        List<EmployeeVO> employeeVOList = employeeDao.selectAll();
        log.info("查询所有员工基本信息，用于选择框接口出参为employeeVOList=[{}]", JSON.toJSONString(employeeVOList));
        return employeeVOList;
    }

    public EmployeeBO getById(Long id) {
        EmployeeBO employeeBO = employeeCache.get(id);
        if (employeeBO == null) {
            EmployeeEntity employeeEntity = employeeDao.selectById(id);
            if (employeeEntity != null) {
                Boolean superman = privilegeEmployeeService.isSuperman(id);
                employeeBO = new EmployeeBO(employeeEntity, superman);
                employeeCache.put(employeeEntity.getId(), employeeBO);
            }
        }
        return employeeBO;
    }

    /**
     * 查询员工列表
     *
     * @param queryDTO
     * @return
     */
    public ResponseDTO<PageResultDTO<EmployeeVO>> selectEmployeeList(EmployeeQueryDTO queryDTO) {
        log.info("查询员工列表接口入参为queryDTO=[{}]", JSON.toJSONString(queryDTO));
        Page pageParam = StandardPageUtil.convert2QueryPage(queryDTO);
        queryDTO.setIsDelete(JudgeEnum.NO.getValue());
        List<EmployeeDTO> employeeList = employeeDao.selectEmployeeList(pageParam, queryDTO);
        List<Long> employeeIdList = employeeList.stream().map(EmployeeDTO::getId).collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(employeeIdList)) {
            List<PositionRelationResultDTO> positionRelationResultDTOList = positionDao.selectEmployeesRelation(employeeIdList);
            Map<Long, List<PositionRelationResultDTO>> employeePositionMap = new HashedMap();

            for (PositionRelationResultDTO positionRelationResultDTO : positionRelationResultDTOList) {
                List<PositionRelationResultDTO> relationResultDTOList = employeePositionMap.get(positionRelationResultDTO.getEmployeeId());
                //匹配对应的岗位
                if (relationResultDTOList == null) {
                    relationResultDTOList = new ArrayList<>();
                    employeePositionMap.put(positionRelationResultDTO.getEmployeeId(), relationResultDTOList);
                }
                relationResultDTOList.add(positionRelationResultDTO);
            }

            for (EmployeeDTO employeeDTO : employeeList) {
                List<PositionRelationResultDTO> relationResultDTOList = employeePositionMap.get(employeeDTO.getId());
                if(relationResultDTOList != null){
                    employeeDTO.setPositionRelationList(relationResultDTOList);
                    employeeDTO.setPositionName(relationResultDTOList.stream().map(PositionRelationResultDTO::getPositionName).collect(Collectors.joining(",")));
                }
            }
        }
        log.info("查询员工列表接口出参为employeeList=[{}]", JSON.toJSONString(employeeList));
        return ResponseDTO.succData(StandardPageUtil.convert2PageResult(pageParam, employeeList, EmployeeVO.class));
    }

    /**
     * 添加员工
     *
     * @param employeeAddDto
     * @param requestToken
     * @return
     */
    public ResponseDTO<String> addEmployee(EmployeeAddDTO employeeAddDto, RequestTokenBO requestToken) {
        log.info("添加员工接口出参为employeeAddDto=[{}],requestToken=[{}]", JSON.toJSONString(employeeAddDto), JSON.toJSONString(requestToken));
        EmployeeEntity entity = StandardBeanUtil.copy(employeeAddDto, EmployeeEntity.class);
        if (StringUtils.isNotEmpty(employeeAddDto.getIdCard())) {
            boolean checkResult = Pattern.matches(StandardVerificationUtil.ID_CARD, employeeAddDto.getIdCard());
            if (!checkResult) {
                return ResponseDTO.wrap(EmployeeResponseCodeConst.ID_CARD_ERROR);
            }
        }
        if (StringUtils.isNotEmpty(employeeAddDto.getBirthday())) {
            boolean checkResult = Pattern.matches(StandardVerificationUtil.DATE, employeeAddDto.getBirthday());
            if (!checkResult) {
                return ResponseDTO.wrap(EmployeeResponseCodeConst.BIRTHDAY_ERROR);
            }
        }
        //同名员工
        EmployeeDTO sameNameEmployee = employeeDao.getByLoginName(entity.getLoginName(), EmployeeStatusEnum.NORMAL.getValue());
        if (null != sameNameEmployee) {
            return ResponseDTO.wrap(EmployeeResponseCodeConst.LOGIN_NAME_EXISTS);
        }
        //同电话员工
        EmployeeDTO samePhoneEmployee = employeeDao.getByPhone(entity.getLoginName(), EmployeeStatusEnum.NORMAL.getValue());
        if (null != samePhoneEmployee) {
            return ResponseDTO.wrap(EmployeeResponseCodeConst.PHONE_EXISTS);
        }
        Long departmentId = entity.getDepartmentId();
        DepartmentEntity department = departmentDao.selectById(departmentId);
        if (department == null) {
            return ResponseDTO.wrap(EmployeeResponseCodeConst.DEPT_NOT_EXIST);
        }

        //如果没有密码  默认设置为123456
        String pwd = entity.getLoginPwd();
        if (StringUtils.isBlank(pwd)) {
            entity.setLoginPwd(StandardDigestUtil.encryptPassword(CommonConst.Password.SALT_FORMAT, RESET_PASSWORD));
        } else {
            entity.setLoginPwd(StandardDigestUtil.encryptPassword(CommonConst.Password.SALT_FORMAT, entity.getLoginPwd()));
        }

        entity.setCreateUser(requestToken.getRequestUserId());
        if (StringUtils.isEmpty(entity.getBirthday())) {
            entity.setBirthday(null);
        }
        employeeDao.insert(entity);

        PositionRelationAddDTO positionRelAddDTO = new PositionRelationAddDTO(employeeAddDto.getPositionIdList(), entity.getId());
        //存储所选岗位信息
        positionService.addPositionRelation(positionRelAddDTO);

        return ResponseDTO.succ();
    }

    /**
     * 禁用单个员工
     *
     * @param employeeId
     * @param status
     * @return
     */
    public ResponseDTO<String> updateStatus(Long employeeId, Integer status) {
        log.info("禁用单个员工接口入参为employeeId=[{}],status=[{}]", employeeId, status);
        if (null == employeeId) {
            return ResponseDTO.wrap(EmployeeResponseCodeConst.EMP_NOT_EXISTS);
        }
        EmployeeBO entity = getById(employeeId);
        if (null == entity) {
            return ResponseDTO.wrap(EmployeeResponseCodeConst.EMP_NOT_EXISTS);
        }
        List<Long> empIds = Lists.newArrayList();
        empIds.add(employeeId);
        employeeDao.batchUpdateStatus(empIds, status);
        employeeCache.remove(employeeId);
        return ResponseDTO.succ();
    }

    /**
     * 批量更新员工状态
     *
     * @param batchUpdateStatusDTO
     * @return
     */
    public ResponseDTO<String> batchUpdateStatus(EmployeeBatchUpdateStatusDTO batchUpdateStatusDTO) {
        log.info("批量更新员工状态接口入参为batchUpdateStatusDTO=[{}]", JSON.toJSONString(batchUpdateStatusDTO));
        employeeDao.batchUpdateStatus(batchUpdateStatusDTO.getEmployeeIds(), batchUpdateStatusDTO.getStatus());
        if (batchUpdateStatusDTO.getEmployeeIds() != null) {
            batchUpdateStatusDTO.getEmployeeIds().forEach(e -> employeeCache.remove(e));
        }
        return ResponseDTO.succ();
    }

    /**
     * 更新员工信息
     *
     * @param updateDTO
     * @return
     */
    public ResponseDTO<String> updateEmployee(EmployeeUpdateDTO updateDTO) {
        log.info("更新员工信息接口入参为updateDTO=[{}]", JSON.toJSONString(updateDTO));
        Long employeeId = updateDTO.getId();
        EmployeeEntity employeeEntity = employeeDao.selectById(employeeId);
        if (null == employeeEntity) {
            return ResponseDTO.wrap(EmployeeResponseCodeConst.EMP_NOT_EXISTS);
        }
        if (StringUtils.isNotBlank(updateDTO.getIdCard())) {
            boolean checkResult = Pattern.matches(StandardVerificationUtil.ID_CARD, updateDTO.getIdCard());
            if (!checkResult) {
                return ResponseDTO.wrap(EmployeeResponseCodeConst.ID_CARD_ERROR);
            }
        }
        if (StringUtils.isNotEmpty(updateDTO.getBirthday())) {
            boolean checkResult = Pattern.matches(StandardVerificationUtil.DATE, updateDTO.getBirthday());
            if (!checkResult) {
                return ResponseDTO.wrap(EmployeeResponseCodeConst.BIRTHDAY_ERROR);
            }
        }
        Long departmentId = updateDTO.getDepartmentId();
        DepartmentEntity departmentEntity = departmentDao.selectById(departmentId);
        if (departmentEntity == null) {
            return ResponseDTO.wrap(EmployeeResponseCodeConst.DEPT_NOT_EXIST);
        }
        EmployeeDTO sameNameEmployee = employeeDao.getByLoginName(updateDTO.getLoginName(), EmployeeStatusEnum.NORMAL.getValue());
        if (null != sameNameEmployee && !sameNameEmployee.getId().equals(employeeId)) {
            return ResponseDTO.wrap(EmployeeResponseCodeConst.LOGIN_NAME_EXISTS);
        }
        EmployeeDTO samePhoneEmployee = employeeDao.getByPhone(updateDTO.getLoginName(), EmployeeStatusEnum.NORMAL.getValue());
        if (null != samePhoneEmployee && !samePhoneEmployee.getId().equals(employeeId)) {
            return ResponseDTO.wrap(EmployeeResponseCodeConst.PHONE_EXISTS);
        }
        String newPwd = updateDTO.getLoginPwd();
        if (!StringUtils.isBlank(newPwd)) {
            updateDTO.setLoginPwd(StandardDigestUtil.encryptPassword(CommonConst.Password.SALT_FORMAT, updateDTO.getLoginPwd()));
        } else {
            updateDTO.setLoginPwd(employeeEntity.getLoginPwd());
        }
        EmployeeEntity entity = StandardBeanUtil.copy(updateDTO, EmployeeEntity.class);
        entity.setUpdateTime(new Date());
        if (StringUtils.isEmpty(entity.getBirthday())) {
            entity.setBirthday(null);
        }
        if (CollectionUtils.isNotEmpty(updateDTO.getPositionIdList())) {
            //删除旧的关联关系 添加新的关联关系
            positionService.removePositionRelation(entity.getId());
            PositionRelationAddDTO positionRelAddDTO = new PositionRelationAddDTO(updateDTO.getPositionIdList(), entity.getId());
            positionService.addPositionRelation(positionRelAddDTO);
        }
        entity.setIsDisabled(employeeEntity.getIsDisabled());
        entity.setIsLeave(employeeEntity.getIsLeave());
        entity.setCreateUser(employeeEntity.getCreateUser());
        entity.setCreateTime(employeeEntity.getCreateTime());
        entity.setUpdateTime(new Date());
        employeeDao.updateById(entity);
        employeeCache.remove(employeeId);
        return ResponseDTO.succ();
    }

    /**
     * 根据员工ID删除员工信息
     *
     * @param employeeId 员工ID
     * @return
     */
    public ResponseDTO<String> deleteEmployeeById(Long employeeId) {
        log.info("根据员工ID删除员工信息接口入参为employeeId=[{}]", employeeId);
        EmployeeEntity employeeEntity = employeeDao.selectById(employeeId);
        if (null == employeeEntity) {
            return ResponseDTO.wrap(EmployeeResponseCodeConst.EMP_NOT_EXISTS);
        }
        //假删
        employeeEntity.setIsDelete(JudgeEnum.YES.getValue().longValue());
        employeeDao.updateById(employeeEntity);
        employeeCache.remove(employeeId);
        return ResponseDTO.succ();
    }

    /**
     * 更新用户角色 单个员工角色授权
     *
     * @param updateRolesDTO
     * @return
     */
    public ResponseDTO<String> updateRoles(EmployeeUpdateRolesDTO updateRolesDTO) {
        log.info("单个员工角色授权接口入参为updateRolesDTO=[{}]", JSON.toJSONString(updateRolesDTO));
        roleEmployeeDao.deleteByEmployeeId(updateRolesDTO.getEmployeeId());
        if (CollectionUtils.isNotEmpty(updateRolesDTO.getRoleIds())) {
            List<RoleEmployeeEntity> roleEmployeeEntities = Lists.newArrayList();
            RoleEmployeeEntity roleEmployeeEntity;
            for (Long roleId : updateRolesDTO.getRoleIds()) {
                roleEmployeeEntity = new RoleEmployeeEntity();
                roleEmployeeEntity.setEmployeeId(updateRolesDTO.getEmployeeId());
                roleEmployeeEntity.setRoleId(roleId);
                roleEmployeeEntities.add(roleEmployeeEntity);
            }
            roleEmployeeDao.batchInsert(roleEmployeeEntities);
        }
        return ResponseDTO.succ();
    }

    /**
     * 更新密码
     *
     * @param updatePwdDTO
     * @param requestToken
     * @return
     */
    public ResponseDTO<String> updatePwd(EmployeeUpdatePwdDTO updatePwdDTO, RequestTokenBO requestToken) {
        log.info("更新密码接口入参为updatePwdDTO=[{}], requestToken=[{}]", JSON.toJSONString(updatePwdDTO), JSON.toJSONString(requestToken));
        Long employeeId = requestToken.getRequestUserId();
        EmployeeEntity employee = employeeDao.selectById(employeeId);
        if (employee == null) {
            return ResponseDTO.wrap(EmployeeResponseCodeConst.EMP_NOT_EXISTS);
        }
        if (!employee.getLoginPwd().equals(StandardDigestUtil.encryptPassword(CommonConst.Password.SALT_FORMAT, updatePwdDTO.getOldPwd()))) {
            return ResponseDTO.wrap(EmployeeResponseCodeConst.PASSWORD_ERROR);
        }
        employee.setLoginPwd(StandardDigestUtil.encryptPassword(CommonConst.Password.SALT_FORMAT, updatePwdDTO.getPwd()));
        employeeDao.updateById(employee);
        employeeCache.remove(employeeId);
        return ResponseDTO.succ();
    }

    /**
     * 通过部门id获取当前部门的人员&没有部门的人
     *
     * @param departmentId
     * @return
     */
    public ResponseDTO<List<EmployeeVO>> getEmployeeByDeptId(Long departmentId) {
        log.info("通过部门id获取当前部门的人员&没有部门的人接口入参为departmentId=[{}]", departmentId);
        List<EmployeeVO> list = employeeDao.getEmployeeIdByDeptId(departmentId);
        log.info("通过部门id获取当前部门的人员&没有部门的人接口出参为list=[{}]", JSON.toJSONString(list));
        return ResponseDTO.succData(list);
    }

    /**
     * 重置密码
     *
     * @param employeeId
     * @return
     */
    public ResponseDTO resetPasswd(Integer employeeId) {
        log.info("员工重置密码接口入参为employeeId=[{}]", employeeId);
        String md5Password = StandardDigestUtil.encryptPassword(CommonConst.Password.SALT_FORMAT, RESET_PASSWORD);
        employeeDao.updatePassword(employeeId, md5Password);
        employeeCache.remove(employeeId);
        return ResponseDTO.succ();
    }

}
