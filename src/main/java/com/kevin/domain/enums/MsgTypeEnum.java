package com.kevin.domain.enums;

/**
 * @author wang
 * @create 2023-12-10-18:44
 */
public enum MsgTypeEnum {

    SUCCESS(1,"成功");

    private Integer type;

    private String decs;

    MsgTypeEnum(Integer type,String decs){
        this.decs = decs;
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDecs() {
        return decs;
    }

    public void setDecs(String decs) {
        this.decs = decs;
    }
}
