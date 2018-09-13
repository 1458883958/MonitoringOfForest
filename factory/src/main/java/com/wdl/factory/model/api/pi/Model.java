package com.wdl.factory.model.api.pi;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.model.api.pi
 * 创建者：   wdl
 * 创建时间： 2018/8/22 18:25
 * 描述：    TODO
 */
public class Model {
    private int uId;
    private PiModel record;
    private int pId;
    private int limitNum;

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public int getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(int limitNum) {
        this.limitNum = limitNum;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public PiModel getRecord() {
        return record;
    }

    public void setRecord(PiModel record) {
        this.record = record;
    }

    @Override
    public String toString() {
        return "Model{" +
                "uId=" + uId +
                ", record=" + record +
                ", pId=" + pId +
                ", limitNum=" + limitNum +
                '}';
    }
}
