package cn.congee.api.module.support.websocket.domain;
import cn.congee.api.module.support.websocket.MessageTypeEnum;
import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午9:09
 **/
@Data
public class MessageCommonDTO {

    /**
     * 消息类型 {@link MessageTypeEnum}
     */
    private Integer messageType;

    /**
     * 具体消息内容
     */
    private String jsonStr;

}
