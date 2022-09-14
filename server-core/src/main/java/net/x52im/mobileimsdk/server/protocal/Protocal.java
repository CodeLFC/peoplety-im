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
 * Protocal.java at 2022-7-12 16:35:57, code by Jack Jiang.
 */
package net.x52im.mobileimsdk.server.protocal;

import com.google.gson.Gson;


public class Protocal {
    protected boolean bridge = false;
    protected int type = 0;
    protected String dataContent = null;
    protected String remark;
    protected String from = "-1";
    protected String to = "-1";
    protected String fp = null;
    protected boolean QoS = false;
    protected int typeu = -1;
    protected int typeMsg = -1;
    protected transient int retryCount = 0;
    protected long sm = -1;

    public Protocal() {
    }

    public Protocal(int type, String dataContent, String from, String to) {
        this(type, dataContent, from, to, -1);
    }

    public Protocal(int type, String dataContent, String from, String to, int typeu) {
        this(type, dataContent, from, to, false, null, typeu);
    }

    public Protocal(int type, String dataContent, String from, String to
            , boolean QoS, String fingerPrint) {
        this(type, dataContent, from, to, QoS, fingerPrint, -1);
    }

    public Protocal(int type, String dataContent, String from, String to
            , boolean QoS, String fingerPrint, int typeu) {
        this.type = type;
        this.dataContent = dataContent;
        this.from = from;
        this.to = to;
        this.QoS = QoS;
        this.typeu = typeu;
        fp = fingerPrint;
    }

    public int getTypeMsg() {
        return typeMsg;
    }

    public void setTypeMsg(int typeMsg) {
        this.typeMsg = typeMsg;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDataContent() {
        return this.dataContent;
    }

    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFp() {
        return this.fp;
    }

    public int getRetryCount() {
        return this.retryCount;
    }

    public void increaseRetryCount() {
        this.retryCount += 1;
    }

    public boolean isQoS() {
        return QoS;
    }

    public void setQoS(boolean qoS) {
        this.QoS = qoS;
    }

    public boolean isBridge() {
        return bridge;
    }

    public void setBridge(boolean bridge) {
        this.bridge = bridge;
    }

    public int getTypeu() {
        return typeu;
    }

    public void setTypeu(int typeu) {
        this.typeu = typeu;
    }


    public long getSm() {
        return sm;
    }

    public void setSm(long sm) {
        this.sm = sm;
    }

    public String toGsonString() {
        return new Gson().toJson(this);
    }

    public byte[] toBytes() {
        return CharsetHelper.getBytes(toGsonString());
    }

    public static long genServerTimestamp() {
        return System.currentTimeMillis();
    }

    public void setFp(String fp) {
        this.fp = fp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
