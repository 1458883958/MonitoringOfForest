package com.wdl.factory.presenter.notice;

import android.support.v7.util.DiffUtil;

import com.wdl.common.widget.recycler.RecyclerAdapter;
import com.wdl.factory.data.data.helper.UserHelper;
import com.wdl.factory.data.data.notice.NoticeDataSource;
import com.wdl.factory.data.data.notice.NoticeRepository;
import com.wdl.factory.model.db.NoticeDb;
import com.wdl.factory.presenter.BaseSourcePresenter;
import com.wdl.factory.utils.DiffUiDataCallback;

import java.util.List;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.notice
 * 创建者：   wdl
 * 创建时间： 2018/9/12 15:56
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public class NoticePresenter extends BaseSourcePresenter<NoticeDb,NoticeDb,NoticeDataSource,NoticeContract.View>
    implements NoticeContract.Presenter{

    public NoticePresenter(NoticeContract.View view) {
        super(view, new NoticeRepository());
    }

    @Override
    public void start() {
        super.start();
        UserHelper.notice();

    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void onLoaded(List<NoticeDb> data) {
        final NoticeContract.View view = getView();
        if (view==null)return;
        RecyclerAdapter<NoticeDb> adapter = view.getRecyclerAdapter();
        List<NoticeDb> old = adapter.getItems();
        DiffUtil.Callback callback = new DiffUiDataCallback<>(old,data);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        refreshData(result,data);
    }

}
