package com.kevin.domain;

import java.util.Date;

/**
 * @author wang
 * @create 2023-2023-10-17:55
 */
public class ServerInfo {
    private Integer typeInfo;

    private String ip;

    private Integer port;

    private Date connetionTime;

    public ServerInfo() {
    }

    public ServerInfo(Integer typeInfo, String ip, Integer port, Date connetionTime) {
        this.typeInfo = typeInfo;
        this.ip = ip;
        this.port = port;
        this.connetionTime = connetionTime;
    }

    public Integer getTypeInfo() {
        return typeInfo;
    }

    public void setTypeInfo(Integer typeInfo) {
        this.typeInfo = typeInfo;
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

    public Date getConnetionTime() {
        return connetionTime;
    }

    public void setConnetionTime(Date connetionTime) {
        this.connetionTime = connetionTime;
    }
}
