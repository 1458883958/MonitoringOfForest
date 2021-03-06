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
    private int mType;
    private String mFilepath;
    private String attach;

    public Message() {
    }

    private transient MessageDb messageDb;

    public MessageDb build(){
        if (messageDb==null){
            //senderId-mType-content
            MessageDb db = new MessageDb();
            db.setType(mType);
            db.setReceiverId(mReceiver);
            db.setSenderId(mSender);
            if (mType!=MessageDb.MESSAGE_TYPE_AUDIO)
                db.setContent(mContent);
            else {
                db.setContent(mContent);
                db.setAttach(mFilepath);
            }

            this.messageDb = db;
        }
        return messageDb;
    }

    public String getmFilepath() {
        return mFilepath;
    }

    public void setmFilepath(String mFilepath) {
        this.mFilepath = mFilepath;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
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
                ", tyep=" + mType +
                ", messageDb=" + messageDb +
                '}';
    }
}
