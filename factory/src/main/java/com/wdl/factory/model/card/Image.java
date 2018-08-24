package com.wdl.factory.model.card;

import com.wdl.common.common.Common;
import com.wdl.factory.model.db.ImageDb;

import java.util.Date;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.model.card
 * 创建者：   wdl
 * 创建时间： 2018/8/13 11:11
 * 描述：    图片数据
 */
@SuppressWarnings("unused")
public class Image {
    private Integer iId;
    private Integer pId;
    private String iImagepath;
    private Double iDensity;
    private Date iTime;

    public Image() {
    }

    private transient ImageDb imageDb;

    public ImageDb build() {
        if (imageDb == null) {
            ImageDb db = new ImageDb();
            db.setId(iId);
            db.setPiId(pId);
            db.setDensity(iDensity);
            db.setImagePath(iImagepath);
            db.setTime(iTime);
            //拼接图片url
            db = montage(db);
            this.imageDb = db;
        }
        return imageDb;
    }

    private ImageDb montage(ImageDb db) {
        if (db == null) return null;
        db.setOriginalPath(Common.Constance.URL + iImagepath + ".jpg");
        db.setTargetPath(Common.Constance.URL + iImagepath + "-" + iDensity + ".jpg");
        return db;
    }

    public Integer getiId() {
        return iId;
    }

    public void setiId(Integer iId) {
        this.iId = iId;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getiImagepath() {
        return iImagepath;
    }

    public void setiImagepath(String iImagepath) {
        this.iImagepath = iImagepath;
    }

    public Double getiDensity() {
        return iDensity;
    }

    public void setiDensity(Double iDensity) {
        this.iDensity = iDensity;
    }

    public Date getiTime() {
        return iTime;
    }

    public void setiTime(Date iTime) {
        this.iTime = iTime;
    }

    @Override
    public String toString() {
        return "Image{" +
                "iId=" + iId +
                ", pId=" + pId +
                ", iImagepath='" + iImagepath + '\'' +
                ", iDensity=" + iDensity +
                ", iTime=" + iTime +
                ", imageDb=" + imageDb +
                '}';
    }
}
