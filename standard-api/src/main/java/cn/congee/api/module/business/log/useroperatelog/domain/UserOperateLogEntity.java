package cn.congee.api.module.business.log.useroperatelog.domain;

import cn.congee.api.common.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午6:53
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "t_user_operate_log")
public class UserOperateLogEntity extends BaseEntity {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;
    /**
     * 操作模块
     */
    private String module;

    /**
     * 操作内容
     */
    private String content;

    /**
     * 请求路径
     */
    private String url;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求参数
     */
    private String param;

    /**
     * 请求结果 0失败 1成功
     */
    private Integer result;

    /**
     * 失败原因
     */
    private String failReason;

}
