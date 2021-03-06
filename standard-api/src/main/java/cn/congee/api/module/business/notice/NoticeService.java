package cn.congee.api.module.business.notice;

import cn.congee.api.common.constant.JudgeEnum;
import cn.congee.api.common.constant.ResponseCodeConst;
import cn.congee.api.common.domain.PageParamDTO;
import cn.congee.api.common.domain.PageResultDTO;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.module.business.notice.dao.NoticeDao;
import cn.congee.api.module.business.notice.dao.NoticeReceiveRecordDao;
import cn.congee.api.module.business.notice.domain.dto.*;
import cn.congee.api.module.business.notice.domain.entity.NoticeEntity;
import cn.congee.api.module.business.notice.domain.entity.NoticeReceiveRecordEntity;
import cn.congee.api.module.support.websocket.WebSocketServer;
import cn.congee.api.module.system.login.domain.RequestTokenBO;
import cn.congee.api.util.StandardBeanUtil;
import cn.congee.api.util.StandardPageUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午9:46
 **/
@Slf4j
@Service
public class NoticeService {

    @Autowired
    private NoticeDao noticeDao;

    @Autowired
    private NoticeReceiveRecordDao noticeReceiveRecordDao;

    @Autowired
    private NoticeManage noticeManage;

    /**
     * 分页查询全部消息
     *
     * @param queryDTO
     * @return
     */
    public ResponseDTO<PageResultDTO<NoticeVO>> queryByPage(NoticeQueryDTO queryDTO) {
        log.info("分页查询全部消息接口入参为queryDTO=[{}]", JSON.toJSONString(queryDTO));
        queryDTO.setDeleted(JudgeEnum.NO.getValue());
        Page page = StandardPageUtil.convert2QueryPage(queryDTO);
        List<NoticeVO> dtoList = noticeDao.queryByPage(page, queryDTO);
        page.setRecords(dtoList);
        PageResultDTO<NoticeVO> pageResultDTO = StandardPageUtil.convert2PageResult(page);
        log.info("分页查询全部消息接口出参为pageResultDTO=[{}]", JSON.toJSONString(pageResultDTO));
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 获取当前登录人的消息列表
     *
     * @param queryDTO
     * @param requestToken
     * @return
     */
    public ResponseDTO<PageResultDTO<NoticeReceiveDTO>> queryReceiveByPage(NoticeReceiveQueryDTO queryDTO, RequestTokenBO requestToken) {
        log.info("获取当前登录人的消息列表接口入参为queryDTO=[{}],requestToken=[{}]", JSON.toJSONString(queryDTO), JSON.toJSONString(requestToken));
        queryDTO.setEmployeeId(requestToken.getRequestUserId());
        queryDTO.setSendStatus(JudgeEnum.YES.getValue());
        Page page = StandardPageUtil.convert2QueryPage(queryDTO);
        List<NoticeReceiveDTO> dtoList = noticeDao.queryReceiveByPage(page, queryDTO);
        dtoList.forEach(e -> {
            if (e.getReceiveTime() == null) {
                e.setReadStatus(JudgeEnum.NO.getValue());
            } else {
                e.setReadStatus(JudgeEnum.YES.getValue());
            }
        });
        page.setRecords(dtoList);
        PageResultDTO<NoticeReceiveDTO> pageResultDTO = StandardPageUtil.convert2PageResult(page);
        log.info("获取当前登录人的消息列表接口出参为pageResultDTO=[{}]", JSON.toJSONString(pageResultDTO));
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 获取我的未读消息
     *
     * @param queryDTO
     * @param requestToken
     * @return
     */
    public ResponseDTO<PageResultDTO<NoticeVO>> queryUnreadByPage(PageParamDTO queryDTO, RequestTokenBO requestToken) {
        log.info("分页查询我的未读消息接口入参为queryDTO=[{}],requestToken=[{}]", JSON.toJSONString(queryDTO), JSON.toJSONString(requestToken));
        Page page = StandardPageUtil.convert2QueryPage(queryDTO);
        List<NoticeVO> dtoList = noticeDao.queryUnreadByPage(page, requestToken.getRequestUserId(), JudgeEnum.YES.getValue());
        page.setRecords(dtoList);
        PageResultDTO<NoticeVO> pageResultDTO = StandardPageUtil.convert2PageResult(page);
        log.info("分页查询我的未读消息接口出参为pageResultDTO=[{}]", JSON.toJSONString(pageResultDTO));
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 添加消息
     *
     * @param addDTO
     * @param requestToken
     * @return
     */
    public ResponseDTO<String> add(NoticeAddDTO addDTO, RequestTokenBO requestToken) {
        log.info("添加消息接口入参为addDTO=[{}],requestToken=[{}]", JSON.toJSONString(addDTO), JSON.toJSONString(requestToken));
        NoticeEntity entity = StandardBeanUtil.copy(addDTO, NoticeEntity.class);
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        entity.setCreateUser(requestToken.getRequestUserId());
        entity.setSendStatus(JudgeEnum.NO.getValue());
        entity.setDeleted(JudgeEnum.NO.getValue());
        noticeDao.insert(entity);
        return ResponseDTO.succ();
    }

    /**
     * 修改消息
     *
     * @param updateDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> update(NoticeUpdateDTO updateDTO) {
        log.info("修改消息接口入参为updateDTO=[{}]", JSON.toJSONString(updateDTO));
        NoticeEntity entity = noticeDao.selectById(updateDTO.getId());
        if (entity == null) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, "此系统通知不存在");
        }
        if (JudgeEnum.YES.getValue().equals(entity.getSendStatus())) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, "此系统通知已发送无法修改");
        }
        noticeManage.update(entity, updateDTO);
        return ResponseDTO.succ();
    }

    /**
     * 根据ID删除消息
     *
     * @param id
     * @return
     */
    public ResponseDTO<String> delete(Long id) {
        log.info("根据ID删除消息接口入参为id=[{}]", id);
        NoticeEntity entity = noticeDao.selectById(id);
        if (entity == null) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, "此系统通知不存在");
        }
        noticeManage.delete(entity);
        return ResponseDTO.succ();
    }

    /**
     * 根据ID查询消息详情
     *
     * @param id
     * @return
     */
    public ResponseDTO<NoticeDetailVO> detail(Long id) {
        log.info("根据ID查询消息详情接口入参为id=[{}]", id);
        NoticeDetailVO noticeDTO = noticeDao.detail(id);
        log.info("根据ID查询消息详情接口出参为noticeDTO=[{}]", JSON.toJSONString(noticeDTO));
        return ResponseDTO.succData(noticeDTO);
    }

    /**
     * 获取某人的未读消息数
     *
     * @param employeeId
     * @return
     */
    private Integer getUnreadCount(Long employeeId) {
        return noticeDao.noticeUnreadCount(employeeId, JudgeEnum.YES.getValue());
    }

    /**
     * 发送给所有在线用户未读消息数
     *
     * @param id
     * @param requestToken
     * @return
     */
    public ResponseDTO<NoticeDetailVO> send(Long id, RequestTokenBO requestToken) {
        log.info("发送消息接口入参为id=[{}],requestToken=[{}]", id, JSON.toJSONString(requestToken));
        NoticeEntity entity = noticeDao.selectById(id);
        if (entity == null) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, "此系统通知不存在");
        }
        noticeManage.send(entity, requestToken);
        this.sendMessage(requestToken);
        return ResponseDTO.succ();
    }

    /**
     * 发送系统通知 ，发送人不进行接收,需再事务外调用 以防止数据隔离级别不同造成未读消息数异常
     *
     * @param requestToken
     */
    private void sendMessage(RequestTokenBO requestToken) {
        List<Long> onLineEmployeeIds = WebSocketServer.getOnLineUserList();
        if (CollectionUtils.isEmpty(onLineEmployeeIds)) {
            return;
        }
        //在线用户已读消息数
        Map<Long, Integer> readCountMap = new HashMap<>();
        List<NoticeReadCountDTO> readCountList = noticeDao.readCount(onLineEmployeeIds);
        if (CollectionUtils.isNotEmpty(readCountList)) {
            readCountMap = readCountList.stream().collect(Collectors.toMap(NoticeReadCountDTO :: getEmployeeId, NoticeReadCountDTO :: getReadCount));
        }
        //已发送消息数
        Integer noticeCount = noticeDao.noticeCount(JudgeEnum.YES.getValue());
        for (Long employeeId : onLineEmployeeIds) {
            Integer readCount = readCountMap.get(employeeId) == null ? 0 : readCountMap.get(employeeId);
            Integer unReadCount = noticeCount - readCount;
            if (! requestToken.getRequestUserId().equals(employeeId)) {
                WebSocketServer.sendOneOnLineUser(unReadCount.toString(), employeeId);
            }
        }
    }

    /**
     * 读取消息
     *
     * @param id
     * @param requestToken
     * @return
     */
    public ResponseDTO<NoticeDetailVO> read(Long id, RequestTokenBO requestToken) {
        log.info("读取消息接口入参为id=[{}],requestToken=[{}]", id, JSON.toJSONString(requestToken));
        NoticeDetailVO noticeDTO = noticeDao.detail(id);
        log.info("读取消息接口出参为noticeDTO=[{}]", JSON.toJSONString(noticeDTO));
        NoticeReceiveRecordEntity recordEntity = noticeReceiveRecordDao.selectByEmployeeAndNotice(requestToken.getRequestUserId(), id);
        if (recordEntity != null) {
            return ResponseDTO.succData(noticeDTO);
        }
        noticeManage.saveReadRecord(id, requestToken);
        this.sendMessage(requestToken);
        return ResponseDTO.succData(noticeDTO);
    }

}
