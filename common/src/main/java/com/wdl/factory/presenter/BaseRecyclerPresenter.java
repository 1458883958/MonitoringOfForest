package com.wdl.factory.presenter;

import android.support.v7.util.DiffUtil;

import com.wdl.common.widget.recycler.RecyclerAdapter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter
 * 创建者：   wdl
 * 创建时间： 2018/8/6 14:55
 * 描述：    基础recycler presenter封装
 *
 * @param <ViewModel> 数据类型
 * @param <View>      view
 */
@SuppressWarnings({"unused", "unchecked"})
public class BaseRecyclerPresenter<ViewModel, View extends BaseContract.RecyclerView> extends BasePresenter<View> {
    public BaseRecyclerPresenter(View view) {
        super(view);
    }

    /**
     * 刷新一堆新数据到界面
     *
     * @param dataList List<ViewModel>
     */
    protected void refreshData(final List<ViewModel> dataList) {
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                View view = getView();
                if (view == null) return;
                //更新数据并刷新界面
                RecyclerAdapter<ViewModel> adapter = view.getRecyclerAdapter();
                adapter.replace(dataList);
                view.adapterDataChanged();
            }
        });
    }

    /**
     * @param result   差异结果
     * @param dataList 数据
     */
    protected void refreshData(final DiffUtil.DiffResult result, final List<ViewModel> dataList) {
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                refreshDataOnUiThread(result, dataList);
            }
        });
    }

    /**
     * 真实的刷新
     *
     * @param result   差异结果
     * @param dataList 数据
     */
    private void refreshDataOnUiThread(final DiffUtil.DiffResult result, final List<ViewModel> dataList) {
        View view = getView();
        if (view == null) return;
        //更新数据，不通知界面刷新 replace方法带有界面刷新
        RecyclerAdapter<ViewModel> adapter = view.getRecyclerAdapter();
        adapter.getItems().clear();
        adapter.getItems().addAll(dataList);
        //通知刷新占位布局
        view.adapterDataChanged();

        //增量更新
        result.dispatchUpdatesTo(adapter);
    }
}
