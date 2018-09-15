package com.wdl.factory.presenter.contact;

import android.support.v7.util.DiffUtil;

import com.wdl.factory.R;
import com.wdl.factory.data.data.helper.UserHelper;
import com.wdl.factory.data.data.user.UserRepository;
import com.wdl.factory.model.db.UserDb;
import com.wdl.factory.persistence.Account;
import com.wdl.factory.presenter.BaseSourcePresenter;
import com.wdl.factory.utils.DiffUiDataCallback;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.contact
 * 创建者：   wdl
 * 创建时间： 2018/9/15 13:44
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public class ContactPresenter extends BaseSourcePresenter<UserDb,
        UserDb, UserRepository, ContactContract.View> implements ContactContract.Presenter {
    public ContactPresenter(ContactContract.View view) {
        super(view, new UserRepository());
    }

    @Override
    public void start() {
        super.start();
        final ContactContract.View view = getView();
        final int userId = Account.getUserId();
        if (userId == -1) {
            if (view != null) {
                Run.onUiAsync(new Action() {
                    @Override
                    public void call() {
                        view.showError(R.string.data_account_error_un_login);
                    }
                });
            }
        } else
            UserHelper.contact(userId);
    }

    @Override
    public void onLoaded(List<UserDb> data) {
        final ContactContract.View view = getView();
        if (view==null)return;
        List<UserDb> old = view.getRecyclerAdapter().getItems();
        DiffUtil.Callback callback = new DiffUiDataCallback<>(old,data);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        refreshData(result,data);
    }
}
