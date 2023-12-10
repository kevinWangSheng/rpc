package com.kevin.domain;

/**
 * @author wang
 * @create 2023-2023-10-17:53
 */
public class InfoProtocol {
    private String channelId;  // 管道id，netty客户端和服务器连接的channel通道的id

    private Integer msgType;  // 消息的类型，1表示文本，2queryReq查询请求，3，反馈

    private Object msgObj; //消息对象

    public InfoProtocol(String channelId, Integer msgType, Object msgObj) {
        this.channelId = channelId;
        this.msgType = msgType;
        this.msgObj = msgObj;
    }

    public InfoProtocol() {
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Object getMsgObj() {
        return msgObj;
    }

    public void setMsgObj(Object msgObj) {
        this.msgObj = msgObj;
    }
}
