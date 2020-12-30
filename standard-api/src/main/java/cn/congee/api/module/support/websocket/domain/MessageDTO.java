package cn.congee.api.module.support.websocket.domain;
import cn.congee.api.module.support.websocket.MessageTypeEnum;
import lombok.Builder;
import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午9:12
 **/
@Data
@Builder
public class MessageDTO {

    /**
     * 消息类型 {@link MessageTypeEnum}
     */
    private Integer messageType;

    /**
     * 消息体
     */
    private String message;

    /**
     * 发送者
     */
    private Long fromUserId;

    /**
     * 接收者，系统通知可为null
     */
    private Long toUserId;

}
