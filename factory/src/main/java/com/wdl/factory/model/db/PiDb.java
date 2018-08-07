package com.wdl.factory.model.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.wdl.factory.model.card.Pi;
import com.wdl.factory.utils.DiffUiDataCallback;

import java.io.Serializable;
import java.util.Objects;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.model.db
 * 创建者：   wdl
 * 创建时间： 2018/8/6 11:24
 * 描述：    设备存储的实体类
 */
@SuppressWarnings("unused")
@Table(database = AppDatabase.class)
public class PiDb extends BaseDbModel<PiDb> implements Serializable {
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

    public PiDb() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PiDb piDb = (PiDb) o;
        return Objects.equals(name, piDb.name) &&
                Objects.equals(remark, piDb.remark) &&
                Objects.equals(address, piDb.address) &&
                Objects.equals(threshold, piDb.threshold) &&
                Objects.equals(delayed, piDb.delayed) &&
                Objects.equals(switchState, piDb.switchState) &&
                Objects.equals(bootState, piDb.bootState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean isSame(PiDb old) {
        return this==old || Objects.equals(id,old.id) ;
    }

    @Override
    public boolean isUiContentSame(PiDb old) {
        return this==old||(Objects.equals(name, old.name) &&
                Objects.equals(remark, old.remark) &&
                Objects.equals(address, old.address) &&
                Objects.equals(threshold, old.threshold) &&
                Objects.equals(delayed, old.delayed) &&
                Objects.equals(switchState, old.switchState) &&
                Objects.equals(bootState, old.bootState));
    }
}
