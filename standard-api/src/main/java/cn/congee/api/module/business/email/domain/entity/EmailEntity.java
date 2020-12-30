package cn.congee.api.module.business.email.domain.entity;

import cn.congee.api.common.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午8:27
 **/
@Data
@TableName("t_email")
public class EmailEntity extends BaseEntity {

    /**
     * 标题
     */
    private String title;

    /**
     * 收件人
     */
    private String toEmails;

    /**
     * 发送状态 0未发送 1已发送
     */
    private Integer sendStatus;

    /**
     * 邮件内容
     */
    private String content;

}
