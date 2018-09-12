package com.wdl.factory.presenter.user_search;

import com.wdl.factory.data.DataSource;
import com.wdl.factory.data.data.helper.UserHelper;
import com.wdl.factory.model.api.account.LoginModel;
import com.wdl.factory.model.card.User;
import com.wdl.factory.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

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
    public void onLoaded(final List<User> data) {
        final SearchContract.UserView view = getView();
        if (view!=null){
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.onSearchDone(data);
                }
            });
        }
    }

    @Override
    public void onNotAvailable(final int res) {
        final SearchContract.UserView view = getView();
        if (view!=null){
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.showError(res);
                }
            });
        }
    }

    @Override
    public void search(String query) {
        Call call = searchCall;
        //如果上一次请求还存在或者未完成,将其取消
        if (call!=null&&!call.isCanceled())call.cancel();
        LoginModel model = new LoginModel();
        model.setKey(query);
        searchCall = UserHelper.search(model,this);
    }
}
