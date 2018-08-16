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
 * 创建时间： 2018/8/15 20:36
 * 描述：    TODO
 */
@Table(database = AppDatabase.class)
@SuppressWarnings("unused")
public class ImageDb extends BaseDbModel<ImageDb> implements Serializable {
    @PrimaryKey
    private Integer id;
    @Column
    private Integer piId;
    @Column
    private String imagePath;
    //原图
    @Column
    private String originalPath;
    //处理图
    @Column
    private String targetPath;
    @Column
    private Double density;
    @Column
    private Date time;

    public ImageDb() {
    }

    public String getOriginalPath() {
        return originalPath;
    }

    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPiId() {
        return piId;
    }

    public void setPiId(Integer piId) {
        this.piId = piId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Double getDensity() {
        return density;
    }

    public void setDensity(Double density) {
        this.density = density;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageDb db = (ImageDb) o;
        return Objects.equals(id, db.id) &&
                Objects.equals(piId, db.piId) &&
                Objects.equals(imagePath, db.imagePath) &&
                Objects.equals(density, db.density) &&
                Objects.equals(time, db.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean isSame(ImageDb old) {
        return this == old || Objects.equals(id, old.id);
    }

    @Override
    public boolean isUiContentSame(ImageDb old) {
        return this == old || (Objects.equals(imagePath, old.imagePath) &&
                Objects.equals(density, old.density) &&
                Objects.equals(time, old.time));
    }
}
