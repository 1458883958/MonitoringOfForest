package com.wdl.factory.presenter.account;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.wdl.factory.R;
import com.wdl.factory.data.DataSource;
import com.wdl.factory.data.data.helper.AccountHelper;
import com.wdl.factory.model.card.User;
import com.wdl.factory.model.db.UserDb;
import com.wdl.factory.model.db.UserDb_Table;
import com.wdl.factory.persistence.Account;
import com.wdl.factory.presenter.BaseContract;
import com.wdl.factory.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.account
 * 创建者：   wdl
 * 创建时间： 2018/8/18 14:32
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public class UpdatePresenter extends BasePresenter<UpdateContract.View>
        implements UpdateContract.Presenter, DataSource.Callback<User> {
    public UpdatePresenter(UpdateContract.View view) {
        super(view);
    }

    @Override
    public void update(int type, String info) {
        User user = new User();
        user.setuId(Account.getUserId());
        if (type == 1) {
            user.setuFullname(info);
        } else if (type == 2) {
            user.setuEmail(info);
        } else if (type == 3) {

        } else if (type == 4) {
            user.setuImagepath(info);
        } else if (type == 5) {
            user.setuIpaddress(info);
        }
        AccountHelper.update(type, user, this);
    }

    @Override
    public void update(int type,String info, double lat, double lot) {
        User user = new User();
        user.setuId(Account.getUserId());
        user.setuIpaddress(info);
        user.setuLatitude(lat);
        user.setuLongitude(lot);
        AccountHelper.update(type, user, this);
    }

    @Override
    public UserDb getUser() {
        final UpdateContract.View view = getView();
        int userId = Account.getUserId();
        if (userId == -1) {
            if (view != null) {
                view.showError(R.string.data_account_error_un_login);
            }
            return null;
        } else
            return Account.getUserDb();
    }

    @Override
    public void onLoaded(final User data) {
        final UpdateContract.View view = getView();
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                if (view != null) {
                    view.showError(R.string.data_account_update_succeed);
                    view.succeed(data);
                }
            }
        });
    }

    @Override
    public void onNotAvailable(int res) {

    }
}
