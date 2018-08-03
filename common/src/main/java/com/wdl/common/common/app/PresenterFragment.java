package com.wdl.common.common.app;

import android.content.Context;

import factory.presenter.BaseContract;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.common.common.app
 * 创建者：   wdl
 * 创建时间： 2018/8/3 15:26
 * 描述：    MVP view层公共方法的提取
 */
@SuppressWarnings("unused")
public abstract class PresenterFragment<Presenter extends BaseContract.Presenter> extends Fragment
        implements BaseContract.View<Presenter> {
    protected Presenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initPresenter();
    }

    /**
     * 初始化presenter
     */
    protected abstract Presenter initPresenter();

    @Override
    public void showLoading() {

    }

    @Override
    public void showError(int res) {
        Application.showToast(res);
    }

    /**
     * 设置presenter
     *
     * @param presenter presenter
     */
    @Override
    public void setPresenter(Presenter presenter) {
        this.mPresenter = presenter;
    }
}
