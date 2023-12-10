package com.kevin.domain;

import java.util.Date;

/**
 * @author wang
 * @create 2023-2023-10-17:51
 */
public class Devices {
    private String channelId;

    private String number;

    private String ip;

    private Integer port;

    private Date connectionTime;

    public Devices(String channelId, String number, String ip, Integer port, Date connectionTime) {
        this.channelId = channelId;
        this.number = number;
        this.ip = ip;
        this.port = port;
        this.connectionTime = connectionTime;
    }

    public Devices() {
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Date getConnectionTime() {
        return connectionTime;
    }

    public void setConnectionTime(Date connectionTime) {
        this.connectionTime = connectionTime;
    }
}
