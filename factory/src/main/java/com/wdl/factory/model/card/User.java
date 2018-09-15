package com.wdl.factory.model.card;

import com.wdl.factory.model.db.UserDb;

import java.util.Date;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.model.db
 * 创建者：   wdl
 * 创建时间： 2018/8/3 15:14
 * 描述：    用户实体类
 */
@SuppressWarnings("unused")
public class User {
    private Integer uId;
    private String uCid;
    private String uUsername;
    private String uPassword;
    private String uFullname;
    private String uRealmname;
    private String uTelephone;
    private String uEmail;
    private String uAboutme;
    private Double uMoney;
    private String uImagepath;
    private Double uLongitude;
    private Double uLatitude;
    private String uIpaddress;
    private Integer uBelogin;
    private Integer uBeemail;
    private Date uRegtime;
    private Date uLastlogintime;
    private Integer uBeadmin;

    public User() {
    }

    private transient UserDb userDb;
    public UserDb build(){
        if (userDb==null){
            UserDb db = new UserDb();
            db.setId(uId);
            db.setUsername(uUsername);
            db.setAlias(uFullname);
            db.setPhone(uTelephone);
            db.setMail(uEmail);
            db.setAbout(uAboutme);
            db.setMoney(uMoney);
            db.setPushId(uCid);
            db.setImage(uImagepath);
            db.setAddress(uIpaddress);
            this.userDb = db;
        }
        return userDb;
    }

//    private transient UserDb userDbWithCid;
//    public UserDb buildWithCid(){
//        if (userDbWithCid==null){
//            UserDb db = new UserDb();
//            db.setId(uId);
//            db.setUsername(uUsername);
//            db.setAlias(uFullname);
//            db.setPhone(uTelephone);
//            db.setMail(uEmail);
//            db.setAbout(uAboutme);
//            db.setMoney(uMoney);
//            db.setPushId(uCid);
//            db.setImage(uImagepath);
//            db.setAddress(uIpaddress);
//            this.userDbWithCid = db;
//        }
//        return userDbWithCid;
//    }

    public String getuCid() {
        return uCid;
    }

    public void setuCid(String uCid) {
        this.uCid = uCid;
    }

    public Integer getuBeadmin() {
        return uBeadmin;
    }

    public void setuBeadmin(Integer uBeadmin) {
        this.uBeadmin = uBeadmin;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public String getuUsername() {
        return uUsername;
    }

    public void setuUsername(String uUsername) {
        this.uUsername = uUsername;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public String getuFullname() {
        return uFullname;
    }

    public void setuFullname(String uFullname) {
        this.uFullname = uFullname;
    }

    public String getuRealmname() {
        return uRealmname;
    }

    public void setuRealmname(String uRealmname) {
        this.uRealmname = uRealmname;
    }

    public String getuTelephone() {
        return uTelephone;
    }

    public void setuTelephone(String uTelephone) {
        this.uTelephone = uTelephone;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuAboutme() {
        return uAboutme;
    }

    public void setuAboutme(String uAboutme) {
        this.uAboutme = uAboutme;
    }

    public Double getuMoney() {
        return uMoney;
    }

    public void setuMoney(Double uMoney) {
        this.uMoney = uMoney;
    }

    public String getuImagepath() {
        return uImagepath;
    }

    public void setuImagepath(String uImagepath) {
        this.uImagepath = uImagepath;
    }

    public Double getuLongitude() {
        return uLongitude;
    }

    public void setuLongitude(Double uLongitude) {
        this.uLongitude = uLongitude;
    }

    public Double getuLatitude() {
        return uLatitude;
    }

    public void setuLatitude(Double uLatitude) {
        this.uLatitude = uLatitude;
    }

    public String getuIpaddress() {
        return uIpaddress;
    }

    public void setuIpaddress(String uIpaddress) {
        this.uIpaddress = uIpaddress;
    }

    public Integer getuBelogin() {
        return uBelogin;
    }

    public void setuBelogin(Integer uBelogin) {
        this.uBelogin = uBelogin;
    }

    public Integer getuBeemail() {
        return uBeemail;
    }

    public void setuBeemail(Integer uBeemail) {
        this.uBeemail = uBeemail;
    }

    public Date getuRegtime() {
        return uRegtime;
    }

    public void setuRegtime(Date uRegtime) {
        this.uRegtime = uRegtime;
    }

    public Date getuLastlogintime() {
        return uLastlogintime;
    }

    public void setuLastlogintime(Date uLastlogintime) {
        this.uLastlogintime = uLastlogintime;
    }

    @Override
    public String toString() {
        return "User{" +
                "uId=" + uId +
                ", uUsername='" + uUsername + '\'' +
                ", uPassword='" + uPassword + '\'' +
                ", uFullname='" + uFullname + '\'' +
                ", uRealmname='" + uRealmname + '\'' +
                ", uTelephone='" + uTelephone + '\'' +
                ", uEmail='" + uEmail + '\'' +
                ", uAboutme='" + uAboutme + '\'' +
                ", uMoney=" + uMoney +
                ", uImagepath='" + uImagepath + '\'' +
                ", uLongitude=" + uLongitude +
                ", uLatitude=" + uLatitude +
                ", uIpaddress='" + uIpaddress + '\'' +
                ", uBelogin=" + uBelogin +
                ", uBeemail=" + uBeemail +
                ", uRegtime=" + uRegtime +
                ", uLastlogintime=" + uLastlogintime +
                '}';
    }
}
