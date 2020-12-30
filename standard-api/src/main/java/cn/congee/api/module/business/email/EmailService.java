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
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class EmailService {

    @Autowired
    private EmailDao emailDao;

    @Autowired
    private SystemConfigService systemConfigService;

    /**
     * 分页查询邮件
     *
     * @param queryDTO
     * @return
     */
    public ResponseDTO<PageResultDTO<EmailVO>> queryByPage(EmailQueryDTO queryDTO) {
        log.info("分页查询邮件接口入参为queryDTO=[{}]", JSON.toJSONString(queryDTO));
        Page page = StandardPageUtil.convert2QueryPage(queryDTO);
        List<EmailEntity> entities = emailDao.queryByPage(page, queryDTO);
        List<EmailVO> dtoList = StandardBeanUtil.copyList(entities, EmailVO.class);
        page.setRecords(dtoList);
        PageResultDTO<EmailVO> pageResultDTO = StandardPageUtil.convert2PageResult(page);
        log.info("分页查询邮件接口出参为pageResultDTO=[{}]", JSON.toJSONString(pageResultDTO));
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 添加邮件
     *
     * @param addDTO
     * @return
     */
    public ResponseDTO<Long> add(EmailDTO addDTO) {
        log.info("添加邮件接口入参为addDTO=[{}]", JSON.toJSONString(addDTO));
        EmailEntity entity = StandardBeanUtil.copy(addDTO, EmailEntity.class);
        emailDao.insert(entity);
        return ResponseDTO.succData(entity.getId());
    }

    /**
     * 更新邮件
     *
     * @param updateDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<Long> update(EmailDTO updateDTO) {
        log.info("更新邮件接口入参为updateDTO=[{}]", JSON.toJSONString(updateDTO));
        EmailEntity entity = StandardBeanUtil.copy(updateDTO, EmailEntity.class);
        emailDao.updateById(entity);
        return ResponseDTO.succData(entity.getId());
    }

    /**
     * 根据id删除邮件
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> delete(Long id) {
        log.info("根据id删除邮件接口入参为id=[{}]", id);
        emailDao.deleteById(id);
        return ResponseDTO.succ();
    }

    /**
     * 根据id查询邮件详情
     *
     * @param id
     * @return
     */
    public ResponseDTO<EmailVO> detail(Long id) {
        log.info("根据id查询邮件详情接口入参为id=[{}]", id);
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
        log.info("发送某个已创建的邮件接口入参为id=[{}]", id);
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
