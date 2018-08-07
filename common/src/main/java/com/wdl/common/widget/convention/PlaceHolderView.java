package com.wdl.common.widget.convention;

import android.support.annotation.StringRes;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.common.widget.convention
 * 创建者：   wdl
 * 创建时间： 2018/8/6 10:35
 * 描述：    占位布局的接口
 */
@SuppressWarnings("unused")
public interface PlaceHolderView {

    /**
     * 没有数据,显示当前空布局,隐藏数据布局
     */
    void triggerEmpty();

    /**
     * 网络错误,显示网络出错的图标
     */
    void triggerNetError();

    /**
     * 加载错误,显示错误信息
     *
     * @param res 资源Id
     */
    void triggerError(@StringRes int res);

    /**
     * 加载成功,隐藏此布局
     */
    void triggerOk();

    /**
     * 正在加载的状态
     */
    void triggerLoading();

    /**
     * true: 隐藏占位布局,false:显示占位布局
     *
     * @param isOk 数据是否完成加载
     */
    void triggerOkOrEmpty(boolean isOk);
}
