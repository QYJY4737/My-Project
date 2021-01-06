package cn.congee.api.module.business.log.userloginlog.domain;

import cn.congee.api.common.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * [ 用户登录日志]
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午6:47
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "t_user_login_log")
public class UserLoginLogEntity extends BaseEntity {

    /**
     * 员工id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户ip
     */
    private String remoteIp;

    /**
     * 用户端口
     */
    private Integer remotePort;

    /**
     * 浏览器
     */
    private String remoteBrowser;

    /**
     * 操作系统
     */
    private String remoteOs;

    /**
     * 登录状态
     */
    private Integer loginStatus;

}
