package com.wdl.factory.presenter.data;

import android.support.v7.util.DiffUtil;
import android.util.Log;

import com.wdl.common.widget.recycler.RecyclerAdapter;
import com.wdl.factory.data.data.devicedata.DataRepository;
import com.wdl.factory.data.data.devicedata.DeviceDataSource;
import com.wdl.factory.data.data.helper.DataHelper;
import com.wdl.factory.model.api.pi.ImagePage;
import com.wdl.factory.model.card.Pi;
import com.wdl.factory.model.db.ImageDb;
import com.wdl.factory.presenter.BaseSourcePresenter;
import com.wdl.factory.utils.DiffUiDataCallback;
import com.wdl.utils.LogUtils;

import java.util.List;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.data
 * 创建者：   wdl
 * 创建时间： 2018/8/16 15:45
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public class DataPresenter extends BaseSourcePresenter<
        ImageDb,ImageDb,DeviceDataSource,DataContract.View> implements DataContract.Presenter{
    public DataPresenter(DataContract.View view,int pId) {
        super(view, new DataRepository(pId));
    }

    @Override
    public void start() {
        super.start();
        final DataContract.View view = getView();
        ImagePage page = new ImagePage();
        page.setPId(view.getPiId());
        page.setPageNum(1);
        page.setPageSize(20);
        DataHelper.select(page);
    }

    @Override
    public void onLoaded(List<ImageDb> data) {
        final DataContract.View view = getView();
        if (view == null) return;
        RecyclerAdapter<ImageDb> adapter = view.getRecyclerAdapter();
        //获取老数据
        List<ImageDb> oldList = adapter.getItems();
        //进行差异化比较
        DiffUtil.Callback callback = new DiffUiDataCallback<>(oldList, data);
        //差异结果集
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        //调用基类刷新
        refreshData(result, data);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
