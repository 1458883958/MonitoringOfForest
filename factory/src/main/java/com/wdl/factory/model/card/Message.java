package com.wdl.factory.model.card;

import com.wdl.factory.model.db.MessageDb;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.model.card
 * 创建者：   wdl
 * 创建时间： 2018/9/15 16:40
 * 描述：    TODO
 */
public class Message {

    /**
     * mSubject : test
     * mContent : hello
     * mSender : 14
     * mReceiver : 13
     * mBeemail : 1
     */

    private String mSubject;
    private String mContent;
    private int mSender;
    private int mReceiver;
    private int mBeemail;
    private int tyep;
    private String attach;

    public Message() {
    }

    private transient MessageDb messageDb;

    public MessageDb build(){
        if (messageDb==null){
            String[] strings = mContent.split("-",2);
            MessageDb db = new MessageDb();
            int type = Integer.valueOf(strings[0]);
            String content = strings[1];
            db.setType(type);
            db.setReceiverId(mReceiver);
            db.setSenderId(mSender);
            if (type!=MessageDb.MESSAGE_TYPE_AUDIO)
                db.setContent(content);
            else {
                String[] str = content.split("@",2);
                db.setContent(str[0]);
                db.setAttach(str[1]);
            }

            this.messageDb = db;
        }
        return messageDb;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public int getTyep() {
        return tyep;
    }

    public void setTyep(int tyep) {
        this.tyep = tyep;
    }

    public String getMSubject() {
        return mSubject;
    }

    public void setMSubject(String mSubject) {
        this.mSubject = mSubject;
    }

    public String getMContent() {
        return mContent;
    }

    public void setMContent(String mContent) {
        this.mContent = mContent;
    }

    public int getMSender() {
        return mSender;
    }

    public void setMSender(int mSender) {
        this.mSender = mSender;
    }

    public int getMReceiver() {
        return mReceiver;
    }

    public void setMReceiver(int mReceiver) {
        this.mReceiver = mReceiver;
    }

    public int getMBeemail() {
        return mBeemail;
    }

    public void setMBeemail(int mBeemail) {
        this.mBeemail = mBeemail;
    }

    @Override
    public String toString() {
        return "Message{" +
                "mSubject='" + mSubject + '\'' +
                ", mContent='" + mContent + '\'' +
                ", mSender=" + mSender +
                ", mReceiver=" + mReceiver +
                ", mBeemail=" + mBeemail +
                ", tyep=" + tyep +
                ", messageDb=" + messageDb +
                '}';
    }
}
