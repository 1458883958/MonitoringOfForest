package com.wdl.factory.presenter.feedback;

import android.support.v7.util.DiffUtil;

import com.wdl.common.widget.recycler.RecyclerAdapter;
import com.wdl.factory.R;
import com.wdl.factory.data.data.feedback.FeedbackDataSource;
import com.wdl.factory.data.data.feedback.FeedbackRepository;
import com.wdl.factory.data.data.helper.FeedbackHelper;
import com.wdl.factory.model.card.Feedback;
import com.wdl.factory.model.db.FeedbackDb;
import com.wdl.factory.persistence.Account;
import com.wdl.factory.presenter.BaseSourcePresenter;
import com.wdl.factory.utils.DiffUiDataCallback;
import com.wdl.utils.LogUtils;

import java.util.List;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.feedback
 * 创建者：   wdl
 * 创建时间： 2018/8/14 15:00
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public class HisFeedbackPresenter extends BaseSourcePresenter<FeedbackDb,FeedbackDb,
        FeedbackDataSource,HisFeedbackContract.View> implements HisFeedbackContract.Presenter{
    public HisFeedbackPresenter(HisFeedbackContract.View view) {
        super(view, new FeedbackRepository());
    }

    @Override
    public void start() {
        super.start();
        final HisFeedbackContract.View view = getView();
        LogUtils.e(Account.getUserId() + "");
        if (Account.getUserId() == -1) {
            view.showError(R.string.data_account_error_un_login);
            return;
        }
        Feedback feedback = new Feedback();
        feedback.setuId(Account.getUserId());
        //网络查询
        FeedbackHelper.select(feedback);
    }

    @Override
    public void onLoaded(List<FeedbackDb> data) {
        final HisFeedbackContract.View view = getView();
        if (view == null) return;
        RecyclerAdapter<FeedbackDb> adapter = view.getRecyclerAdapter();
        //获取老数据
        List<FeedbackDb> oldList = adapter.getItems();
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
