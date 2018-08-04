package com.wdl.factory.model.api.account;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.model.api.account
 * 创建者：   wdl
 * 创建时间： 2018/8/4 10:42
 * 描述：    注册model
 */
@SuppressWarnings("unused")
public class RegisterModel {
    private String uTelephone;
    private String uPassword;
    private String code;

    public RegisterModel(String uTelephone, String uPassword, String code) {
        this.uTelephone = uTelephone;
        this.uPassword = uPassword;
        this.code = code;
    }

    public RegisterModel(String uTelephone) {
        this.uTelephone = uTelephone;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "RegisterModel{" +
                "uTelephone='" + uTelephone + '\'' +
                ", uPassword='" + uPassword + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
