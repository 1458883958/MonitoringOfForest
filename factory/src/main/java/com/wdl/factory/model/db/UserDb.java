package com.wdl.factory.model.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.wdl.factory.model.card.User;
import com.wdl.factory.utils.DiffUiDataCallback;

import java.io.Serializable;
import java.util.Objects;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.model
 * 创建者：   wdl
 * 创建时间： 2018/8/5 13:15
 * 描述：    用户信息的Db实体类
 */
@SuppressWarnings({"unused"})
@Table(database = AppDatabase.class)
public class UserDb extends BaseDbModel<UserDb> implements Serializable {
    @PrimaryKey
    private Integer id;
    @Column
    private String username;
    @Column
    private String alias;
    @Column
    private String phone;
    @Column
    private String mail;
    @Column
    private String about;
    @Column
    private Double money;
    @Column
    private String image;
    @Column
    private String address;

    public UserDb() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserDb{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", alias='" + alias + '\'' +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                ", about='" + about + '\'' +
                ", money=" + money +
                ", image='" + image + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDb userDb = (UserDb) o;
        return Objects.equals(username, userDb.username) &&
                Objects.equals(about, userDb.about) &&
                Objects.equals(image, userDb.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean isSame(UserDb old) {
        return this == old || Objects.equals(id, old.id);
    }

    @Override
    public boolean isUiContentSame(UserDb old) {
        return this == old || (Objects.equals(username, old.username) &&
                Objects.equals(about, old.about) &&
                Objects.equals(image, old.image));
    }
}
