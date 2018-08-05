package com.wdl.factory.model.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.wdl.factory.model.card.User;

import java.io.Serializable;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.model
 * 创建者：   wdl
 * 创建时间： 2018/8/5 13:15
 * 描述：    用户信息的Db实体类
 */
@SuppressWarnings({"unused"})
@Table(database = AppDatabase.class)
public class UserDb extends BaseModel implements Serializable{
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

    public UserDb(){
    }

    public UserDb getUserDb(User user){
        this.id = user.getuId();
        this.username = user.getuUsername();
        this.alias = user.getuFullname();
        this.phone = user.getuTelephone();
        this.mail = user.getuEmail();
        this.about = user.getuAboutme();
        this.money = user.getuMoney();
        this.image = user.getuImagepath();
        this.address = user.getuIpaddress();
        return this;
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
}
