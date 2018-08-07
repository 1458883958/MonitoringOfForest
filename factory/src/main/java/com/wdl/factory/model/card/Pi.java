package com.wdl.factory.model.card;

import com.wdl.factory.model.db.PiDb;

import java.util.Date;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.model.card
 * 创建者：   wdl
 * 创建时间： 2018/8/6 11:08
 * 描述：    设备实体类
 */
@SuppressWarnings("unused")
public class Pi {
    private Integer pId;
    private String pName;
    private String pRemark;
    private String pIpaddress;
    private String pPassword;
    private Integer pThreshold;
    private Integer pDelayed;
    private Integer pSwitchstate;
    private Integer pBootstate;
    private Double pLongitude;
    private Double pLatitude;
    private Date pCreatetime;
    private Date pStarttime;
    private Date pLivetime;


    public Pi() {
    }

    private transient PiDb piDb;

    public PiDb build() {
        if (piDb == null) {
            PiDb db = new PiDb();
            db.setId(pId);
            db.setName(pName);
            db.setRemark(pRemark);
            db.setAddress(pIpaddress);
            db.setPassword(pPassword);
            db.setThreshold(pThreshold);
            db.setDelayed(pDelayed);
            db.setSwitchState(pSwitchstate);
            db.setBootState(pBootstate);
            this.piDb = db;
        }
        return piDb;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
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

    public String getpPassword() {
        return pPassword;
    }

    public void setpPassword(String pPassword) {
        this.pPassword = pPassword;
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

    public Integer getpSwitchstate() {
        return pSwitchstate;
    }

    public void setpSwitchstate(Integer pSwitchstate) {
        this.pSwitchstate = pSwitchstate;
    }

    public Integer getpBootstate() {
        return pBootstate;
    }

    public void setpBootstate(Integer pBootstate) {
        this.pBootstate = pBootstate;
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

    @Override
    public String toString() {
        return "Pi{" +
                "pId=" + pId +
                ", pName='" + pName + '\'' +
                ", pRemark='" + pRemark + '\'' +
                ", pIpaddress='" + pIpaddress + '\'' +
                ", pPassword='" + pPassword + '\'' +
                ", pThreshold=" + pThreshold +
                ", pDelayed=" + pDelayed +
                ", pSwitchstate=" + pSwitchstate +
                ", pBootstate=" + pBootstate +
                ", pLongitude=" + pLongitude +
                ", pLatitude=" + pLatitude +
                ", pCreatetime=" + pCreatetime +
                ", pStarttime=" + pStarttime +
                ", pLivetime=" + pLivetime +
                '}';
    }
}
