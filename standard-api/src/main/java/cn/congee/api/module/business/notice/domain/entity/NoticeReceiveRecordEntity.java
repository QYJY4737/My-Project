package cn.congee.api.module.business.notice.domain.entity;

import cn.congee.api.common.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午9:24
 **/
@Data
@TableName("t_notice_receive_record")
public class NoticeReceiveRecordEntity extends BaseEntity {

    /**
     * 消息id
     */
    private Long noticeId;

    /**
     * 消息接收人
     */
    private Long employeeId;

}
