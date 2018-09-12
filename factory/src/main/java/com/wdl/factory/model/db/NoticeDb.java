package com.wdl.factory.model.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.model.db
 * 创建者：   wdl
 * 创建时间： 2018/9/12 14:22
 * 描述：    TODO
 */
@SuppressWarnings("unused")
@Table(database = AppDatabase.class)
public class NoticeDb extends BaseDbModel<NoticeDb> implements Serializable {
    @PrimaryKey
    private Integer id;
    @Column
    private Integer userId;
    @Column
    private String subject;
    @Column
    private String filePath;
    @Column
    private Date time;
    @Column
    private String content;

    public NoticeDb() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
        NoticeDb noticeDb = (NoticeDb) o;
        return Objects.equals(id, noticeDb.id) &&
                Objects.equals(userId, noticeDb.userId) &&
                Objects.equals(subject, noticeDb.subject) &&
                Objects.equals(filePath, noticeDb.filePath) &&
                Objects.equals(time, noticeDb.time) &&
                Objects.equals(content, noticeDb.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean isSame(NoticeDb old) {
        return this == old || Objects.equals(id, old.id);
    }

    @Override
    public boolean isUiContentSame(NoticeDb old) {
        return this == old || (Objects.equals(id, old.id) &&
                Objects.equals(userId, old.userId) &&
                Objects.equals(subject, old.subject) &&
                Objects.equals(content, old.content) &&
                Objects.equals(filePath, old.filePath) &&
                Objects.equals(time, old.time));
    }
}
