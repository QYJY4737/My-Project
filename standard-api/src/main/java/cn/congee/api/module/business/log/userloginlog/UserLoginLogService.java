package cn.congee.api.module.business.log.userloginlog;

import cn.congee.api.common.domain.PageResultDTO;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.module.business.log.userloginlog.domain.UserLoginLogDTO;
import cn.congee.api.module.business.log.userloginlog.domain.UserLoginLogEntity;
import cn.congee.api.module.business.log.userloginlog.domain.UserLoginLogQueryDTO;
import cn.congee.api.module.support.websocket.WebSocketServer;
import cn.congee.api.module.system.employee.EmployeeService;
import cn.congee.api.module.system.employee.domain.dto.EmployeeQueryDTO;
import cn.congee.api.module.system.employee.domain.vo.EmployeeVO;
import cn.congee.api.util.StandardBeanUtil;
import cn.congee.api.util.StandardPageUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * [ 用户登录日志 ]
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午9:04
 **/
@Slf4j
@Service
public class UserLoginLogService {

    @Autowired
    private UserLoginLogDao userLoginLogDao;

    @Autowired
    private EmployeeService employeeService;

    /**
     * 分页查询用户登录日志
     *
     * @param queryDTO
     * @return
     */
    public ResponseDTO<PageResultDTO<UserLoginLogDTO>> queryByPage(UserLoginLogQueryDTO queryDTO) {
        log.info("分页查询用户登录日志接口入参为queryDTO=[{}]", JSON.toJSONString(queryDTO));
        Page page = StandardPageUtil.convert2QueryPage(queryDTO);
        List<UserLoginLogEntity> entities = userLoginLogDao.queryByPage(page, queryDTO);
        List<UserLoginLogDTO> dtoList = StandardBeanUtil.copyList(entities, UserLoginLogDTO.class);
        page.setRecords(dtoList);
        PageResultDTO<UserLoginLogDTO> pageResultDTO = StandardPageUtil.convert2PageResult(page);
        log.info("分页查询用户登录日志接口入参为pageResultDTO=[{}]", JSON.toJSONString(pageResultDTO));
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 根据id删除用户登录日志
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> delete(Long id) {
        log.info("根据id删除用户登录日志接口入参为id=[{}]", id);
        userLoginLogDao.deleteById(id);
        return ResponseDTO.succ();
    }

    /**
     * 查询员工在线状态
     *
     * @param queryDTO
     * @return
     */
    public ResponseDTO<PageResultDTO<EmployeeVO>> queryUserOnLine(EmployeeQueryDTO queryDTO) {
        log.info("查询员工在线状态接口入参为queryDTO=[{}]", JSON.toJSONString(queryDTO));
        List<Long> onLineUserList = WebSocketServer.getOnLineUserList();
        if (CollectionUtils.isEmpty(onLineUserList)) {
            return ResponseDTO.succ();
        }
        queryDTO.setEmployeeIds(onLineUserList);
        ResponseDTO<PageResultDTO<EmployeeVO>> employeeList = employeeService.selectEmployeeList(queryDTO);
        log.info("查询员工在线状态接口出参为employeeList=[{}]", JSON.toJSONString(employeeList));
        return employeeList;
    }

}
