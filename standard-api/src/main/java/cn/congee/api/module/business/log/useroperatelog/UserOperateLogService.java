package cn.congee.api.module.business.log.useroperatelog;

import cn.congee.api.common.domain.PageResultDTO;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.module.business.log.useroperatelog.domain.UserOperateLogDTO;
import cn.congee.api.module.business.log.useroperatelog.domain.UserOperateLogEntity;
import cn.congee.api.module.business.log.useroperatelog.domain.UserOperateLogQueryDTO;
import cn.congee.api.util.StandardBeanUtil;
import cn.congee.api.util.StandardPageUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午9:14
 **/
@Service
public class UserOperateLogService {

    @Autowired
    private UserOperateLogDao userOperateLogDao;

    /**
     * @author yandanyang
     * @description 分页查询
     * @date 2019-05-15 11:32:14
     */
    public ResponseDTO<PageResultDTO<UserOperateLogDTO>> queryByPage(UserOperateLogQueryDTO queryDTO) {
        Page page = StandardPageUtil.convert2QueryPage(queryDTO);
        List<UserOperateLogEntity> entities = userOperateLogDao.queryByPage(page, queryDTO);
        List<UserOperateLogDTO> dtoList = StandardBeanUtil.copyList(entities, UserOperateLogDTO.class);
        page.setRecords(dtoList);
        PageResultDTO<UserOperateLogDTO> pageResultDTO = StandardPageUtil.convert2PageResult(page);
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * @author yandanyang
     * @description 添加
     * @date 2019-05-15 11:32:14
     */
    public ResponseDTO<String> add(UserOperateLogDTO addDTO) {
        UserOperateLogEntity entity = StandardBeanUtil.copy(addDTO, UserOperateLogEntity.class);
        userOperateLogDao.insert(entity);
        return ResponseDTO.succ();
    }

    /**
     * @author yandanyang
     * @description 编辑
     * @date 2019-05-15 11:32:14
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> update(UserOperateLogDTO updateDTO) {
        UserOperateLogEntity entity = StandardBeanUtil.copy(updateDTO, UserOperateLogEntity.class);
        userOperateLogDao.updateById(entity);
        return ResponseDTO.succ();
    }

    /**
     * @author yandanyang
     * @description 删除
     * @date 2019-05-15 11:32:14
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> delete(Long id) {
        userOperateLogDao.deleteById(id);
        return ResponseDTO.succ();
    }

    /**
     * @author yandanyang
     * @description 根据ID查询
     * @date 2019-05-15 11:32:14
     */
    public ResponseDTO<UserOperateLogDTO> detail(Long id) {
        UserOperateLogEntity entity = userOperateLogDao.selectById(id);
        UserOperateLogDTO dto = StandardBeanUtil.copy(entity, UserOperateLogDTO.class);
        return ResponseDTO.succData(dto);
    }

}
