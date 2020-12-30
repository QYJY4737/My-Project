package cn.congee.api.module.business.notice.domain.entity;

import cn.congee.api.common.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午9:20
 **/
@Data
@TableName("t_notice")
public class NoticeEntity extends BaseEntity {

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息创建人
     */
    private Long createUser;

    /**
     * 发送状态
     */
    private Integer sendStatus;

    /**
     * 删除状态
     */
    private Integer deleted;

}
