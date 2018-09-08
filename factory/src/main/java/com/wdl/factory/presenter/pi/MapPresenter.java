package com.wdl.factory.presenter.pi;

import com.wdl.factory.R;
import com.wdl.factory.data.DataSource;
import com.wdl.factory.data.data.helper.PiHelper;
import com.wdl.factory.model.db.PiDb;
import com.wdl.factory.persistence.Account;
import com.wdl.factory.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.pi
 * 创建者：   wdl
 * 创建时间： 2018/9/4 10:08
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public class MapPresenter extends BasePresenter<MapContract.View> implements
        MapContract.Presenter, DataSource.Callback<List<PiDb>> {
    public MapPresenter(MapContract.View view) {
        super(view);
    }


    @Override
    public void query() {
        start();
        final MapContract.View view = getView();
        int userId = Account.getUserId();
        if (userId == -1) view.showError(R.string.data_account_error_un_login);
        PiHelper.query(userId, this);
    }

    @Override
    public void onNotAvailable(final int res) {
        final MapContract.View view = getView();
        if (view != null) {
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.showError(res);
                    view.failed();
                }
            });
        }
    }

    @Override
    public void onLoaded(final List<PiDb> data) {
        final MapContract.View view = getView();
        if (view != null) {
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.result(data);
                }
            });
        }
    }

}
