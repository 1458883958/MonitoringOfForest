package com.wdl.factory.model.card;

import com.wdl.factory.model.db.SensorDb;

import java.util.Date;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.model.card
 * 创建者：   wdl
 * 创建时间： 2018/9/12 20:11
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public class Sensor {

    private Integer dId;
    private Integer pId;   //设备id
    private Double dTemperature;//温度
    private Double dHumidity;//湿度
    private Double dLightintensity;//光强
    private Integer dSmoke;//烟雾
    private Integer dFlame;//火焰
    private Integer dInfrared;//红外
    private Date dTime;   //时间

    public Sensor() {
    }

    private transient SensorDb sensorDb;

    public SensorDb build() {
        if (sensorDb==null){
            SensorDb db = new SensorDb();
            db.setId(dId);
            db.setPiId(pId);
            db.setTemperature(dTemperature);
            db.setHumidity(dHumidity);
            db.setLight(dLightintensity);
            db.setSmoke(dSmoke);
            db.setFlame(dFlame);
            db.setInfrared(dInfrared);
            db.setTime(dTime);
            this.sensorDb = db;
        }
        return sensorDb;
    }

    public Integer getdId() {
        return dId;
    }

    public void setdId(Integer dId) {
        this.dId = dId;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Double getdTemperature() {
        return dTemperature;
    }

    public void setdTemperature(Double dTemperature) {
        this.dTemperature = dTemperature;
    }

    public Double getdHumidity() {
        return dHumidity;
    }

    public void setdHumidity(Double dHumidity) {
        this.dHumidity = dHumidity;
    }

    public Double getdLightintensity() {
        return dLightintensity;
    }

    public void setdLightintensity(Double dLightintensity) {
        this.dLightintensity = dLightintensity;
    }

    public Integer getdSmoke() {
        return dSmoke;
    }

    public void setdSmoke(Integer dSmoke) {
        this.dSmoke = dSmoke;
    }

    public Integer getdFlame() {
        return dFlame;
    }

    public void setdFlame(Integer dFlame) {
        this.dFlame = dFlame;
    }

    public Integer getdInfrared() {
        return dInfrared;
    }

    public void setdInfrared(Integer dInfrared) {
        this.dInfrared = dInfrared;
    }

    public Date getdTime() {
        return dTime;
    }

    public void setdTime(Date dTime) {
        this.dTime = dTime;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "dId=" + dId +
                ", pId=" + pId +
                ", dTemperature=" + dTemperature +
                ", dHumidity=" + dHumidity +
                ", dLightintensity=" + dLightintensity +
                ", dSmoke=" + dSmoke +
                ", dFlame=" + dFlame +
                ", dInfrared=" + dInfrared +
                ", dTime=" + dTime +
                ", sensorDb=" + sensorDb +
                '}';
    }
}
