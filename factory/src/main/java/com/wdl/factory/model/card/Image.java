package com.wdl.factory.model.card;

import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.wdl.factory.model.db.ImageDb;

import java.security.PrivateKey;
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

    public ImageDb build(){
        if (imageDb==null){
            ImageDb db = new ImageDb();
            db.setId(iId);
            db.setPiId(pId);
            db.setDensity(iDensity);
            db.setImagePath(iImagepath);
            this.imageDb = db;
        }
        return imageDb;
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
}
