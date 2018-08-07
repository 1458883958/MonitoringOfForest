package com.wdl.factory.data.data.user;

import com.wdl.factory.model.card.User;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.data.data.user
 * 创建者：   wdl
 * 创建时间： 2018/8/7 11:20
 * 描述：    用户中心
 */
@SuppressWarnings("unused")
public interface UserCenter {
    void dispatch(User...users);
}
