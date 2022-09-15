/*
 * Copyright (C) 2022  即时通讯网(52im.net) & Jack Jiang.
 * The MobileIMSDK v6.1 Project.
 * All rights reserved.
 *
 * > Github地址：https://github.com/JackJiang2011/MobileIMSDK
 * > 文档地址：  http://www.52im.net/forum-89-1.html
 * > 技术社区：  http://www.52im.net/
 * > 技术交流群：320837163 (http://www.52im.net/topic-qqgroup.html)
 * > 作者公众号：“【即时通讯技术圈】”，欢迎关注！
 * > 联系作者：  http://www.52im.net/thread-2792-1-1.html
 *
 * "即时通讯网(52im.net) - 即时通讯开发者社区!" 推荐开源工程。
 *
 * ProtocalFactory.java at 2022-7-12 16:35:59, code by Jack Jiang.
 */
package net.x52im.mobileimsdk.server.protocol;

import com.google.gson.Gson;
import net.x52im.mobileimsdk.server.protocol.c.PKeepAlive;
import net.x52im.mobileimsdk.server.protocol.c.PLoginInfo;
import net.x52im.mobileimsdk.server.protocol.s.PErrorResponse;
import net.x52im.mobileimsdk.server.protocol.s.PKeepAliveResponse;
import net.x52im.mobileimsdk.server.protocol.s.PKickoutInfo;
import net.x52im.mobileimsdk.server.protocol.s.PLoginInfoResponse;

public class ProtocolFactory {
    private static String create(Object c) {
        return new Gson().toJson(c);
    }

    public static <T> T parse(byte[] fullProtocolJSONBytes, int len, Class<T> clazz) {
        return parse(CharsetHelper.getString(fullProtocolJSONBytes, len), clazz);
    }

    public static <T> T parse(String dataContentOfProtocol, Class<T> clazz) {
        return new Gson().fromJson(dataContentOfProtocol, clazz);
    }

    public static Protocol parse(byte[] fullProtocolJSONBytes, int len) {
        return parse(fullProtocolJSONBytes, len, Protocol.class);
    }

    public static Protocol createPKeepAliveResponse(String to_user_id) {
        return new Protocol(ProtocolType.S.FROM_SERVER_TYPE_OF_RESPONSE$KEEP$ALIVE, create(new PKeepAliveResponse()), "0", to_user_id);
    }

    public static PKeepAliveResponse parsePKeepAliveResponse(String dataContentOfProtocol) {
        return parse(dataContentOfProtocol, PKeepAliveResponse.class);
    }

    public static Protocol createPKeepAlive(String from_user_id) {
        return new Protocol(ProtocolType.C.FROM_CLIENT_TYPE_OF_KEEP$ALIVE, create(new PKeepAlive()), from_user_id, "0");
    }

    public static PKeepAlive parsePKeepAlive(String dataContentOfProtocal) {
        return parse(dataContentOfProtocal, PKeepAlive.class);
    }

    public static Protocol createPErrorResponse(int errorCode, String errorMsg, String user_id) {
        return new Protocol(ProtocolType.S.FROM_SERVER_TYPE_OF_RESPONSE$FOR$ERROR, create(new PErrorResponse(errorCode, errorMsg)), "0", user_id);
    }

    public static PErrorResponse parsePErrorResponse(String dataContentOfProtocal) {
        return parse(dataContentOfProtocal, PErrorResponse.class);
    }

    public static Protocol createPLoginoutInfo(String user_id) {
        return new Protocol(ProtocolType.C.FROM_CLIENT_TYPE_OF_LOGOUT, null, user_id, "0");
    }

    public static Protocol createPLoginInfo(PLoginInfo loginInfo) {
        return new Protocol(ProtocolType.C.FROM_CLIENT_TYPE_OF_LOGIN, create(loginInfo), loginInfo.getLoginUserId(), "0");
    }

    public static PLoginInfo parsePLoginInfo(String dataContentOfProtocal) {
        return parse(dataContentOfProtocal, PLoginInfo.class);
    }

    public static Protocol createPLoginInfoResponse(int code, long firstLoginTime, String user_id) {
        return new Protocol(ProtocolType.S.FROM_SERVER_TYPE_OF_RESPONSE$LOGIN, create(new PLoginInfoResponse(code, firstLoginTime)), "0", user_id, false, null);
    }

    public static PLoginInfoResponse parsePLoginInfoResponse(String dataContentOfProtocal) {
        return parse(dataContentOfProtocal, PLoginInfoResponse.class);
    }

    public static Protocol createCommonData(String dataContent, String from_user_id, String to_user_id, boolean QoS, String fingerPrint) {
        return createCommonData(dataContent, from_user_id, to_user_id, QoS, fingerPrint, -1);
    }

    public static Protocol createCommonData(String dataContent, String from_user_id, String to_user_id, boolean QoS, String fingerPrint, int typeu) {
        return new Protocol(ProtocolType.C.FROM_CLIENT_TYPE_OF_COMMON$DATA, dataContent, from_user_id, to_user_id, QoS, fingerPrint, typeu);
    }

    public static Protocol createRecivedBack(String from_user_id, String to_user_id, String recievedMessageFingerPrint) {
        return createRecivedBack(from_user_id, to_user_id, recievedMessageFingerPrint, false);
    }

    public static Protocol createRecivedBack(String from_user_id, String to_user_id, String recievedMessageFingerPrint, boolean bridge) {
        Protocol p = new Protocol(ProtocolType.C.FROM_CLIENT_TYPE_OF_RECIVED, recievedMessageFingerPrint, from_user_id, to_user_id);
        p.setBridge(bridge);
        return p;
    }

    public static Protocol createPKickout(String to_user_id, int code, String reason) {
        return new Protocol(ProtocolType.S.FROM_SERVER_TYPE_OF_KICKOUT, create(new PKickoutInfo(code, reason)), "0", to_user_id);
    }

    public static PKickoutInfo parsePKickoutInfo(String dataContentOfProtocal) {
        return parse(dataContentOfProtocal, PKickoutInfo.class);
    }
}
