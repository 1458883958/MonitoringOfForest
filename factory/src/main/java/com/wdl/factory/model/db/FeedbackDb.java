package com.wdl.factory.model.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.wdl.factory.model.card.User;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.model.db
 * 创建者：   wdl
 * 创建时间： 2018/8/14 12:41
 * 描述：    反馈
 */
@SuppressWarnings("unused")
@Table(database = AppDatabase.class)
public class FeedbackDb extends BaseDbModel<FeedbackDb> implements Serializable{
    @PrimaryKey
    private Integer id;
    @Column
    private String subject;
    @Column
    private Integer beread;
    @Column
    private Date time;
    @Column
    private String content;
    @Column
    private int userId;
    public FeedbackDb() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getBeread() {
        return beread;
    }

    public void setBeread(Integer beread) {
        this.beread = beread;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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
        FeedbackDb that = (FeedbackDb) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(subject, that.subject) &&
                Objects.equals(time, that.time) &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean isSame(FeedbackDb old) {
        return this==old || Objects.equals(id,old.id) ;
    }

    @Override
    public boolean isUiContentSame(FeedbackDb old) {
        return this==old||(
                Objects.equals(subject, old.subject) &&
                Objects.equals(content, old.content) &&
                Objects.equals(time, old.time));
    }
}
