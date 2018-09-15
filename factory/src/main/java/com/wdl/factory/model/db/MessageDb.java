package com.wdl.factory.model.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.io.Serializable;
import java.util.Objects;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.model.db
 * 创建者：   wdl
 * 创建时间： 2018/9/15 18:30
 * 描述：    TODO
 */
@SuppressWarnings("unused")
@Table(database = AppDatabase.class)
public class MessageDb extends BaseDbModel<MessageDb> implements Serializable{
    @PrimaryKey(autoincrement = true)
    private int id;
    @Column
    private int receiverId;
    @Column
    private int senderId;
    @Column
    private String content;

    public MessageDb() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageDb db = (MessageDb) o;
        return id == db.id &&
                receiverId == db.receiverId &&
                senderId == db.senderId &&
                Objects.equals(content, db.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean isSame(MessageDb old) {
        return this == old || Objects.equals(id, old.id);
    }

    @Override
    public boolean isUiContentSame(MessageDb old) {
        return this == old || (Objects.equals(id, old.id) &&
                Objects.equals(content, old.content));
    }
}
