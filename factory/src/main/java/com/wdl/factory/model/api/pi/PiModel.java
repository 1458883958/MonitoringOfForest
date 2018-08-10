package com.wdl.factory.model.api.pi;

import java.util.Date;

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
    private String pName;
    private String pRemark;
    private String pIpaddress;
    private Integer pThreshold;
    private Integer pDelayed;
    private Double pLongitude;
    private Double pLatitude;
    private Date pCreatetime;
    private Date pStarttime;
    private Date pLivetime;
    public PiModel() {
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpRemark() {
        return pRemark;
    }

    public void setpRemark(String pRemark) {
        this.pRemark = pRemark;
    }

    public String getpIpaddress() {
        return pIpaddress;
    }

    public void setpIpaddress(String pIpaddress) {
        this.pIpaddress = pIpaddress;
    }

    public Integer getpThreshold() {
        return pThreshold;
    }

    public void setpThreshold(Integer pThreshold) {
        this.pThreshold = pThreshold;
    }

    public Integer getpDelayed() {
        return pDelayed;
    }

    public void setpDelayed(Integer pDelayed) {
        this.pDelayed = pDelayed;
    }

    public Double getpLongitude() {
        return pLongitude;
    }

    public void setpLongitude(Double pLongitude) {
        this.pLongitude = pLongitude;
    }

    public Double getpLatitude() {
        return pLatitude;
    }

    public void setpLatitude(Double pLatitude) {
        this.pLatitude = pLatitude;
    }

    public Date getpCreatetime() {
        return pCreatetime;
    }

    public void setpCreatetime(Date pCreatetime) {
        this.pCreatetime = pCreatetime;
    }

    public Date getpStarttime() {
        return pStarttime;
    }

    public void setpStarttime(Date pStarttime) {
        this.pStarttime = pStarttime;
    }

    public Date getpLivetime() {
        return pLivetime;
    }

    public void setpLivetime(Date pLivetime) {
        this.pLivetime = pLivetime;
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
