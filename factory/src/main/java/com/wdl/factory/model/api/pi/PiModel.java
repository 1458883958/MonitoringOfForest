package com.wdl.factory.model.api.pi;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.model.api.pi
 * 创建者：   wdl
 * 创建时间： 2018/8/9 11:37
 * 描述：    设备model
 */
@SuppressWarnings("unused")
public class PiModel {
    private Integer uId;
    private String pPassword;
    private Integer pId;
    private Integer pSwitchstate;

    public PiModel() {
    }

    public Integer getpSwitchstate() {
        return pSwitchstate;
    }

    public void setpSwitchstate(Integer pSwitchstate) {
        this.pSwitchstate = pSwitchstate;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public String getpPassword() {
        return pPassword;
    }

    public void setpPassword(String pPassword) {
        this.pPassword = pPassword;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }
}
