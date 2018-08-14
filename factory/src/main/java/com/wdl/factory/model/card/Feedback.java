package com.wdl.factory.model.card;

import com.wdl.factory.model.db.FeedbackDb;

import java.util.Date;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.model.card
 * 创建者：   wdl
 * 创建时间： 2018/8/13 10:40
 * 描述：    反馈
 */
@SuppressWarnings("unused")
public class Feedback {
    private Integer fId;
    private Integer uId;
    private String fSubject;
    private Integer fBeread;
    private Date fTime;
    private String fContent;

    public Feedback() {
    }

    private transient FeedbackDb feedbackDb;
    public FeedbackDb build(){
        if (feedbackDb==null){
            FeedbackDb db = new FeedbackDb();
            db.setId(fId);
            db.setContent(fContent);
            db.setSubject(fSubject);
            db.setBeread(fBeread);
            db.setTime(fTime);
            this.feedbackDb = db;
        }
        return feedbackDb;
    }

    public Integer getfId() {
        return fId;
    }

    public void setfId(Integer fId) {
        this.fId = fId;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public String getfSubject() {
        return fSubject;
    }

    public void setfSubject(String fSubject) {
        this.fSubject = fSubject;
    }

    public Integer getfBeread() {
        return fBeread;
    }

    public void setfBeread(Integer fBeread) {
        this.fBeread = fBeread;
    }

    public Date getfTime() {
        return fTime;
    }

    public void setfTime(Date fTime) {
        this.fTime = fTime;
    }

    public String getfContent() {
        return fContent;
    }

    public void setfContent(String fContent) {
        this.fContent = fContent;
    }
}
