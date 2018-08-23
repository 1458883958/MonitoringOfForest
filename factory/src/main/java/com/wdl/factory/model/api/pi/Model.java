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
}
