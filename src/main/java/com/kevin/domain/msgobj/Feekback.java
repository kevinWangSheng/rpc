package com.kevin.domain.msgobj;

/**
 * @author wang
 * @create 2023-2023-10-17:56
 */
public class Feekback {
    private String channelId;

    private Integer stateType;

    private Integer msgState;

    public Feekback(String channelId, Integer stateType, Integer msgState) {
        this.channelId = channelId;
        this.stateType = stateType;
        this.msgState = msgState;
    }

    public Feekback() {
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Integer getStateType() {
        return stateType;
    }

    public void setStateType(Integer stateType) {
        this.stateType = stateType;
    }

    public Integer getMsgState() {
        return msgState;
    }

    public void setMsgState(Integer msgState) {
        this.msgState = msgState;
    }
}
