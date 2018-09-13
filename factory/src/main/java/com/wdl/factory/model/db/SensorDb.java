package com.wdl.factory.model.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.model.db
 * 创建者：   wdl
 * 创建时间： 2018/9/12 20:09
 * 描述：    传感器数据
 */
@SuppressWarnings("unused")
@Table(database = AppDatabase.class)
public class SensorDb extends BaseDbModel<SensorDb> implements Serializable {

    @PrimaryKey
    private Integer id;
    @Column
    private Integer piId;   //设备id
    @Column
    private Double temperature;//温度
    @Column
    private Double humidity;//湿度
    @Column
    private Double light;//光强
    @Column
    private Integer smoke;//烟雾
    @Column
    private Integer flame;//火焰
    @Column
    private Integer infrared;//红外
    @Column
    private Date time;   //时间

    public SensorDb() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPiId() {
        return piId;
    }

    public void setPiId(Integer piId) {
        this.piId = piId;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getLight() {
        return light;
    }

    public void setLight(Double light) {
        this.light = light;
    }

    public Integer getSmoke() {
        return smoke;
    }

    public void setSmoke(Integer smoke) {
        this.smoke = smoke;
    }

    public Integer getFlame() {
        return flame;
    }

    public void setFlame(Integer flame) {
        this.flame = flame;
    }

    public Integer getInfrared() {
        return infrared;
    }

    public void setInfrared(Integer infrared) {
        this.infrared = infrared;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "SensorDb{" +
                "id=" + id +
                ", piId=" + piId +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", light=" + light +
                ", smoke=" + smoke +
                ", flame=" + flame +
                ", infrared=" + infrared +
                ", time=" + time +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorDb db = (SensorDb) o;
        return Objects.equals(id, db.id) &&
                Objects.equals(piId, db.piId) &&
                Objects.equals(temperature, db.temperature) &&
                Objects.equals(humidity, db.humidity) &&
                Objects.equals(light, db.light) &&
                Objects.equals(smoke, db.smoke) &&
                Objects.equals(flame, db.flame) &&
                Objects.equals(infrared, db.infrared) &&
                Objects.equals(time, db.time);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public boolean isSame(SensorDb old) {
        return this == old || Objects.equals(id, old.id);
    }

    @Override
    public boolean isUiContentSame(SensorDb old) {
        return this == old || (Objects.equals(piId, old.piId) &&
                Objects.equals(temperature, old.temperature) &&
                Objects.equals(light, old.light) &&
                Objects.equals(smoke, old.smoke) &&
                Objects.equals(flame, old.flame) &&
                Objects.equals(infrared, old.infrared) &&
                Objects.equals(humidity, old.humidity));
    }
}
