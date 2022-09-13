package gaozhi.online.peoplety.im.utils;

import gaozhi.online.peoplety.entity.Message;
import net.x52im.mobileimsdk.server.protocal.Protocal;

/**
 * @author http://gaozhi.online
 * @version 1.0
 * @description: TODO 消息协议转换
 * @date 2022/9/11 22:15
 */
public class MessageUtils {
    public static Message toMessage(Protocal protocal) {
        Message message = new Message();

        //消息唯一识别码
        message.setId(Long.parseLong(protocal.getFp()));

        //一级消息类型
        message.setType(protocal.getTypeu());
        //消息内容类型
        message.setTypeMsg(protocal.getType());

        //消息来自
        message.setFromId(Long.parseLong(protocal.getFrom()));
        //消息发往
        message.setToId(Long.parseLong(protocal.getTo()));

        //消息内容
        message.setMsg(protocal.getDataContent());
        message.setRemark(protocal.getRemark());
        //消息时间
        message.setTime(protocal.getSm());
        return message;
    }

    public static Protocal toProtocol(Message message) {
        Protocal protocal = new Protocal();
        //消息id
        protocal.setFp(String.valueOf(message.getId()));
        //消息大类
        protocal.setTypeu(message.getType());
        //消息小类
        protocal.setType(message.getTypeMsg());

        //消息来自和发往
        protocal.setFrom(String.valueOf(message.getFromId()));
        protocal.setTo(String.valueOf(message.getToId()));

        //消息内容
        protocal.setDataContent(message.getMsg());
        protocal.setRemark(message.getRemark());
        //消息时间
        protocal.setSm(message.getTime());
        //消息时间
        return protocal;
    }
}
