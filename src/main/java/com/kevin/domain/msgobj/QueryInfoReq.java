package com.kevin.domain.msgobj;

/**
 * @author wang
 * @create 2023-2023-10-17:57
 */
public class QueryInfoReq {
    private Integer stateType; //类型

    public QueryInfoReq(Integer stateType) {
        this.stateType = stateType;
    }

    public QueryInfoReq() {
    }

    public Integer getStateType() {
        return stateType;
    }

    public void setStateType(Integer stateType) {
        this.stateType = stateType;
    }
}
