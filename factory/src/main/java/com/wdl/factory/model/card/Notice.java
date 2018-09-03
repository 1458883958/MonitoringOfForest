package com.wdl.factory.model.card;

import java.util.Date;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.model.card
 * 创建者：   wdl
 * 创建时间： 2018/8/6 15:23
 * 描述：    TODO
 */
public class Notice {
    private Integer nId;

    private Integer uId;
    private String nSubject;
    private String nFilepath;
    private Integer nBeemail;

    private Integer nBesms;
    private Date nTime;
    private String nContent;

    public Integer getnId() {
        return nId;
    }

    public void setnId(Integer nId) {
        this.nId = nId;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public String getnSubject() {
        return nSubject;
    }

    public void setnSubject(String nSubject) {
        this.nSubject = nSubject;
    }

    public String getnFilepath() {
        return nFilepath;
    }

    public void setnFilepath(String nFilepath) {
        this.nFilepath = nFilepath;
    }

    public Integer getnBeemail() {
        return nBeemail;
    }

    public void setnBeemail(Integer nBeemail) {
        this.nBeemail = nBeemail;
    }

    public Integer getnBesms() {
        return nBesms;
    }

    public void setnBesms(Integer nBesms) {
        this.nBesms = nBesms;
    }

    public Date getnTime() {
        return nTime;
    }

    public void setnTime(Date nTime) {
        this.nTime = nTime;
    }

    public String getnContent() {
        return nContent;
    }

    public void setnContent(String nContent) {
        this.nContent = nContent;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "nId=" + nId +
                ", uId=" + uId +
                ", nSubject='" + nSubject + '\'' +
                ", nFilepath='" + nFilepath + '\'' +
                ", nBeemail=" + nBeemail +
                ", nBesms=" + nBesms +
                ", nTime=" + nTime +
                ", nContent='" + nContent + '\'' +
                '}';
    }
}
