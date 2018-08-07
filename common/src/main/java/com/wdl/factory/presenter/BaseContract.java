package com.wdl.factory.presenter;

import android.support.annotation.StringRes;

import com.wdl.common.widget.recycler.RecyclerAdapter;


/**
 * 项目名：  MonitoringOfForest
 * 包名：    factory.presenter
 * 创建者：   wdl
 * 创建时间： 2018/8/3 14:29
 * 描述：    基础契约接口
 */
@SuppressWarnings("unused")
public interface BaseContract {

    /**
     * view层的基本功能
     *
     * @param <T> presenter
     */
    interface View<T extends Presenter> {

        /**
         * 显示进度
         */
        void showLoading();

        /**
         * 显示错误信息
         *
         * @param res 资源id
         */
        void showError(@StringRes int res);

        /**
         * 给view设置presenter的引用
         *
         * @param presenter presenter
         */
        void setPresenter(T presenter);
    }

    /**
     * 基本Presenter的职责
     */
    interface Presenter {

        /**
         * 开始触发
         */
        void start();

        /**
         * 销毁触发
         */
        void destroy();
    }

    /**
     * 基本列表view的职责
     *
     * @param <ViewModel> 要绑定的数据模型
     * @param <T>         Presenter
     */
    interface RecyclerView<ViewModel, T extends BaseContract.Presenter> extends View<T> {
        /**
         * 获取适配器,自主进行刷新
         *
         * @return RecyclerAdapter<ViewModel>
         */
        RecyclerAdapter<ViewModel> getRecyclerAdapter();

        /**
         * 当适配器数据更改时触发
         */
        void adapterDataChanged();
    }
}
