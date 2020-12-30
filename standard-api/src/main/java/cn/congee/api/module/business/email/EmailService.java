package cn.congee.api.module.business.email;

import cn.congee.api.common.constant.ResponseCodeConst;
import cn.congee.api.common.domain.PageResultDTO;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.module.business.email.domain.dto.EmailConfigDTO;
import cn.congee.api.module.business.email.domain.dto.EmailDTO;
import cn.congee.api.module.business.email.domain.dto.EmailQueryDTO;
import cn.congee.api.module.business.email.domain.dto.EmailVO;
import cn.congee.api.module.business.email.domain.entity.EmailEntity;
import cn.congee.api.module.system.systemconfig.SystemConfigService;
import cn.congee.api.module.system.systemconfig.constant.SystemConfigEnum;
import cn.congee.api.util.StandardBeanUtil;
import cn.congee.api.util.StandardPageUtil;
import cn.congee.api.util.StandardSendMailUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午8:28
 **/
@Service
public class EmailService {

    @Autowired
    private EmailDao emailDao;

    @Autowired
    private SystemConfigService systemConfigService;

    /**
     * @author yandanyang
     * @description 分页查询
     * @date 2019-05-13 17:10:16
     */
    public ResponseDTO<PageResultDTO<EmailVO>> queryByPage(EmailQueryDTO queryDTO) {
        Page page = StandardPageUtil.convert2QueryPage(queryDTO);
        List<EmailEntity> entities = emailDao.queryByPage(page, queryDTO);
        List<EmailVO> dtoList = StandardBeanUtil.copyList(entities, EmailVO.class);
        page.setRecords(dtoList);
        PageResultDTO<EmailVO> pageResultDTO = StandardPageUtil.convert2PageResult(page);
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * @author yandanyang
     * @description 添加
     * @date 2019-05-13 17:10:16
     */
    public ResponseDTO<Long> add(EmailDTO addDTO) {
        EmailEntity entity = StandardBeanUtil.copy(addDTO, EmailEntity.class);
        emailDao.insert(entity);
        return ResponseDTO.succData(entity.getId());
    }

    /**
     * @author yandanyang
     * @description 编辑
     * @date 2019-05-13 17:10:16
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<Long> update(EmailDTO updateDTO) {
        EmailEntity entity = StandardBeanUtil.copy(updateDTO, EmailEntity.class);
        emailDao.updateById(entity);
        return ResponseDTO.succData(entity.getId());
    }

    /**
     * @author yandanyang
     * @description 删除
     * @date 2019-05-13 17:10:16
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> delete(Long id) {
        emailDao.deleteById(id);
        return ResponseDTO.succ();
    }

    /**
     * @author yandanyang
     * @description 根据ID查询
     * @date 2019-05-13 17:10:16
     */
    public ResponseDTO<EmailVO> detail(Long id) {
        EmailEntity entity = emailDao.selectById(id);
        EmailVO dto = StandardBeanUtil.copy(entity, EmailVO.class);
        return ResponseDTO.succData(dto);
    }

    /**
     * 发送某个已创建的邮件
     *
     * @param id
     * @return
     */
    public ResponseDTO<String> send(Long id) {
        EmailEntity entity = emailDao.selectById(id);
        EmailConfigDTO emailConfig = systemConfigService.selectByKey2Obj(SystemConfigEnum.Key.EMAIL_CONFIG.name(), EmailConfigDTO.class);
        String toEmails = entity.getToEmails();
        if (StringUtils.isEmpty(toEmails)) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, "收件人信息为空");
        }
        String[] emails = toEmails.split(";");
        StandardSendMailUtil.sendMail(emailConfig.getUsername(), emailConfig.getPassword(), emailConfig.getUsername(), emails, "", emailConfig.getSmtpHost(), entity.getTitle(), entity.getContent());
        entity.setSendStatus(EmailSendStatusEnum.SEND.getType());
        emailDao.updateById(entity);
        return ResponseDTO.succ();
    }

}
