package com.wdl.factory.presenter.user_search;

import com.wdl.factory.data.DataSource;
import com.wdl.factory.data.data.helper.UserHelper;
import com.wdl.factory.model.card.User;
import com.wdl.factory.presenter.BasePresenter;
import java.util.List;

import retrofit2.Call;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.user_search
 * 创建者：   wdl
 * 创建时间： 2018/9/11 21:09
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public class SearchPresenter extends BasePresenter<SearchContract.UserView> implements
        SearchContract.Presenter, DataSource.Callback<List<User>> {
    private Call searchCall;
    public SearchPresenter(SearchContract.UserView view) {
        super(view);
    }

    @Override
    public void onLoaded(List<User> data) {

    }

    @Override
    public void onNotAvailable(int res) {

    }

    @Override
    public void search(String query) {
        Call call = searchCall;
        //如果上一次请求还存在或者未完成,将其取消
        if (call!=null&&!call.isCanceled())call.cancel();
        //searchCall = UserHelper.search(query,this);
    }
}
