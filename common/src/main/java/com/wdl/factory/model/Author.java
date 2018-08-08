package com.wdl.factory.model;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.model
 * 创建者：   wdl
 * 创建时间： 2018/8/8 13:06
 * 描述：    用户基础接口
 */
@SuppressWarnings("unused")
public interface Author {
    Integer getId();
    void setId(Integer id);
    String getPhone();
    void setPhone(String phone);
    String getImage();
    void setImage(String image);
}
