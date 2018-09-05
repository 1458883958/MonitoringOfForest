package com.wdl.factory.model.card;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.model.card
 * 创建者：   wdl
 * 创建时间： 2018/9/3 19:47
 * 描述：    版本更新表
 */
public class Version {

    /**
     * vId : 1
     * vPiversion : 0.1.1
     * vPicontent : 增加了GPS定位
     * vPipath : https://www.xmhhs.top/myfile/MyPi.jar
     * vAppname : Forest
     * vAppversion : 0.1.1
     * vAppcontent : 修复了定位错误的BUG。
     * vTime : 2018-09-03 19:24:55
     */

    private int vId;
    private String vPiversion;
    private String vPicontent;
    private String vPipath;
    private String vAppname;
    private String vAppversion;
    private String vAppcontent;
    private String vTime;

    public int getVId() {
        return vId;
    }

    public void setVId(int vId) {
        this.vId = vId;
    }

    public String getVPiversion() {
        return vPiversion;
    }

    public void setVPiversion(String vPiversion) {
        this.vPiversion = vPiversion;
    }

    public String getVPicontent() {
        return vPicontent;
    }

    public void setVPicontent(String vPicontent) {
        this.vPicontent = vPicontent;
    }

    public String getVPipath() {
        return vPipath;
    }

    public void setVPipath(String vPipath) {
        this.vPipath = vPipath;
    }

    public String getVAppname() {
        return vAppname;
    }

    public void setVAppname(String vAppname) {
        this.vAppname = vAppname;
    }

    public String getVAppversion() {
        return vAppversion;
    }

    public void setVAppversion(String vAppversion) {
        this.vAppversion = vAppversion;
    }

    public String getVAppcontent() {
        return vAppcontent;
    }

    public void setVAppcontent(String vAppcontent) {
        this.vAppcontent = vAppcontent;
    }

    public String getVTime() {
        return vTime;
    }

    public void setVTime(String vTime) {
        this.vTime = vTime;
    }

    @Override
    public String toString() {
        return "Version{" +
                "vId=" + vId +
                ", vPiversion='" + vPiversion + '\'' +
                ", vPicontent='" + vPicontent + '\'' +
                ", vPipath='" + vPipath + '\'' +
                ", vAppname='" + vAppname + '\'' +
                ", vAppversion='" + vAppversion + '\'' +
                ", vAppcontent='" + vAppcontent + '\'' +
                ", vTime='" + vTime + '\'' +
                '}';
    }
}
