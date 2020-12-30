package cn.congee.api.module.business.notice.domain.dto;

import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午9:29
 **/
@Data
public class NoticeReadCountDTO {

    /**
     * 员工id
     */
    private Long employeeId;

    /**
     * 已读消息数
     */
    private Integer readCount;

}
