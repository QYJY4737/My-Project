package cn.congee.api.module.support.websocket.domain;

import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午9:11
 **/
@Data
public class WebSocketHeartBeatDTO {

    /**
     * 当前登录人id
     */
    private Long employeeId;

}
