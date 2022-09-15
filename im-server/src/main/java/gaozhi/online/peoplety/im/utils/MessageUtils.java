package gaozhi.online.peoplety.im.utils;

import gaozhi.online.peoplety.entity.Message;
import net.x52im.mobileimsdk.server.protocol.Protocol;

/**
 * @author http://gaozhi.online
 * @version 1.0
 * @description: TODO 消息协议转换
 * @date 2022/9/11 22:15
 */
public class MessageUtils {
    public static Message toMessage(Protocol protocol) {
        Message message = new Message();

        //消息唯一识别码
        message.setId(Long.parseLong(protocol.getFp()));

        //一级消息类型
        message.setType(protocol.getTypeu());
        //消息内容类型
        message.setTypeMsg(protocol.getTypeMsg());

        //消息来自
        message.setFromId(Long.parseLong(protocol.getFrom()));
        //消息发往
        message.setToId(Long.parseLong(protocol.getTo()));

        //消息内容
        message.setMsg(protocol.getDataContent());
        message.setRemark(protocol.getRemark());
        //消息时间
        message.setTime(protocol.getSm());
        return message;
    }

    public static Protocol toProtocol(Message message) {
        Protocol protocol = new Protocol();
        //消息id
        protocol.setFp(String.valueOf(message.getId()));
        //消息小类
        protocol.setTypeMsg(message.getTypeMsg());
        //消息大类
        protocol.setTypeu(message.getType());

        //消息来自和发往
        protocol.setFrom(String.valueOf(message.getFromId()));
        protocol.setTo(String.valueOf(message.getToId()));

        //消息内容
        protocol.setDataContent(message.getMsg());
        protocol.setRemark(message.getRemark());
        //消息时间
        protocol.setSm(message.getTime());
        //消息时间
        return protocol;
    }
}
