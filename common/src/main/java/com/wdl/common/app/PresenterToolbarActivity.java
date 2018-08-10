package com.wdl.common.app;

import com.wdl.factory.presenter.BaseContract;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.common.app
 * 创建者：   wdl
 * 创建时间： 2018/8/10 10:09
 * 描述：    activity基础presenter
 */
@SuppressWarnings("unused")
public abstract class PresenterToolbarActivity<Presenter extends BaseContract.Presenter> extends ToolbarActivity
        implements BaseContract.View<Presenter> {

    protected Presenter mPresenter;

    //初始化presenter
    protected abstract Presenter initPresenter();

    @Override
    protected void initBefore() {
        super.initBefore();
        initPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.destroy();
    }

    /**
     * 隐藏
     */
    protected void hideLoading() {
        if (placeHolderView != null) {
            placeHolderView.triggerOk();
        }
    }

    /**
     * 显示进度
     */
    @Override
    public void showLoading() {
        if (placeHolderView != null) {
            placeHolderView.triggerLoading();
        }
    }

    @Override
    public void showError(int res) {
        if (placeHolderView != null) {
            placeHolderView.triggerError(res);
        } else
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
