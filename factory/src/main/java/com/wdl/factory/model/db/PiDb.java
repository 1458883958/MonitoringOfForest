package com.wdl.factory.model.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.wdl.factory.model.card.Pi;

import java.io.Serializable;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.model.db
 * 创建者：   wdl
 * 创建时间： 2018/8/6 11:24
 * 描述：    设备存储的实体类
 */
@SuppressWarnings("unused")
@Table(database = AppDatabase.class)
public class PiDb extends BaseModel implements Serializable {
    @PrimaryKey
    private Integer id;
    @Column
    private String name;
    @Column
    private String remark;
    @Column
    private String address;
    @Column
    private String password;
    @Column
    private Integer threshold;
    @Column
    private Integer delayed;
    @Column
    private Integer switchState;
    @Column
    private Integer bootState;

    public PiDb getPi(Pi pi){
        this.id = pi.getpId();
        this.name = pi.getpName();
        this.remark = pi.getpRemark();
        this.address = pi.getpIpaddress();
        this.password = pi.getpPassword();
        this.threshold = pi.getpThreshold();
        this.delayed = pi.getpDelayed();
        this.switchState = pi.getpSwitchstate();
        this.bootState = pi.getpBootstate();
        return this;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public Integer getDelayed() {
        return delayed;
    }

    public void setDelayed(Integer delayed) {
        this.delayed = delayed;
    }

    public Integer getSwitchState() {
        return switchState;
    }

    public void setSwitchState(Integer switchState) {
        this.switchState = switchState;
    }

    public Integer getBootState() {
        return bootState;
    }

    public void setBootState(Integer bootState) {
        this.bootState = bootState;
    }

    @Override
    public String toString() {
        return "PiDb{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                ", threshold=" + threshold +
                ", delayed=" + delayed +
                ", switchState=" + switchState +
                ", bootState=" + bootState +
                '}';
    }
}
