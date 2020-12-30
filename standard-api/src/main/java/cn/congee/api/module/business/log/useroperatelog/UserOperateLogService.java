package cn.congee.api.module.business.log.useroperatelog;

import cn.congee.api.common.domain.PageResultDTO;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.module.business.log.useroperatelog.domain.UserOperateLogDTO;
import cn.congee.api.module.business.log.useroperatelog.domain.UserOperateLogEntity;
import cn.congee.api.module.business.log.useroperatelog.domain.UserOperateLogQueryDTO;
import cn.congee.api.util.StandardBeanUtil;
import cn.congee.api.util.StandardPageUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * [ 用户操作日志 ]
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午9:14
 **/
@Slf4j
@Service
public class UserOperateLogService {

    @Autowired
    private UserOperateLogDao userOperateLogDao;

    /**
     * 分页查询用户操作日志
     *
     * @param queryDTO
     * @return
     */
    public ResponseDTO<PageResultDTO<UserOperateLogDTO>> queryByPage(UserOperateLogQueryDTO queryDTO) {
        log.info("分页查询用户操作日志接口入参为queryDTO=[{}]", JSON.toJSONString(queryDTO));
        Page page = StandardPageUtil.convert2QueryPage(queryDTO);
        List<UserOperateLogEntity> entities = userOperateLogDao.queryByPage(page, queryDTO);
        List<UserOperateLogDTO> dtoList = StandardBeanUtil.copyList(entities, UserOperateLogDTO.class);
        page.setRecords(dtoList);
        PageResultDTO<UserOperateLogDTO> pageResultDTO = StandardPageUtil.convert2PageResult(page);
        log.info("分页查询用户操作日志接口入参为pageResultDTO=[{}]", JSON.toJSONString(pageResultDTO));
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 添加日志
     *
     * @param addDTO
     * @return
     */
    public ResponseDTO<String> add(UserOperateLogDTO addDTO) {
        UserOperateLogEntity entity = StandardBeanUtil.copy(addDTO, UserOperateLogEntity.class);
        userOperateLogDao.insert(entity);
        return ResponseDTO.succ();
    }

    /**
     * 更新日志
     *
     * @param updateDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> update(UserOperateLogDTO updateDTO) {
        UserOperateLogEntity entity = StandardBeanUtil.copy(updateDTO, UserOperateLogEntity.class);
        userOperateLogDao.updateById(entity);
        return ResponseDTO.succ();
    }

    /**
     * 根据id删除用户操作日志
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> delete(Long id) {
        log.info("根据id删除用户操作日志接口入参为id=[{}]", id);
        userOperateLogDao.deleteById(id);
        return ResponseDTO.succ();
    }

    /**
     * 根据id查询用户操作日志详情
     *
     * @param id
     * @return
     */
    public ResponseDTO<UserOperateLogDTO> detail(Long id) {
        log.info("根据id查询用户操作日志详情接口入参为id=[{}]", id);
        UserOperateLogEntity entity = userOperateLogDao.selectById(id);
        UserOperateLogDTO dto = StandardBeanUtil.copy(entity, UserOperateLogDTO.class);
        log.info("根据id查询用户操作日志详情接口出参为dto=[{}]", JSON.toJSONString(dto));
        return ResponseDTO.succData(dto);
    }

}
