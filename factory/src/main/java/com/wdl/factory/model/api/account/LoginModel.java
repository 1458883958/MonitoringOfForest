package com.wdl.factory.model.api.account;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.model.api.account
 * 创建者：   wdl
 * 创建时间： 2018/8/3 15:00
 * 描述：    登陆的model
 */
@SuppressWarnings({"unused","unchecked"})
public class LoginModel {
   private String uTelephone;
   private String uPassword;
   private String uPushId;

    public LoginModel(String uTelephone, String uPassword) {
        this.uTelephone = uTelephone;
        this.uPassword = uPassword;
    }

    public LoginModel(String uTelephone, String uPassword, String uPushId) {
        this.uTelephone = uTelephone;
        this.uPassword = uPassword;
        this.uPushId = uPushId;
    }

    public String getuPushId() {
        return uPushId;
    }

    public void setuPushId(String uPushId) {
        this.uPushId = uPushId;
    }

    public String getuTelephone() {
        return uTelephone;
    }

    public void setuTelephone(String uTelephone) {
        this.uTelephone = uTelephone;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

}
