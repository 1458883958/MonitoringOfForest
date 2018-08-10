package com.wdl.factory.presenter.user;

import com.wdl.factory.Factory;
import com.wdl.factory.data.data.helper.UserHelper;
import com.wdl.factory.model.db.UserDb;
import com.wdl.factory.persistence.Account;
import com.wdl.factory.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.user
 * 创建者：   wdl
 * 创建时间： 2018/8/10 10:22
 * 描述：    用户信息presenter
 */
@SuppressWarnings("unused")
public class PersonalPresenter extends BasePresenter<PersonalContract.View>
    implements PersonalContract.Presenter{
    private UserDb user;
    public PersonalPresenter(PersonalContract.View view) {
        super(view);
    }

    @Override
    public void start() {
        super.start();
        Factory.runOnAsy(new Runnable() {
            @Override
            public void run() {
                int id = getView().getId();
                UserDb user = UserHelper.searchFromNet(id);
                PersonalContract.View view = getView();
                if (view!=null&&user!=null)
                    onLoad(view,user);
            }
        });
    }

    private void onLoad(final PersonalContract.View view, final UserDb user) {
        this.user = user;
        //是否是自己
        final boolean isSelf = user.getId().equals(Account.getUserId());
        //是否关注
        final boolean isFollow = true;
        final boolean isAllowChat = !isSelf;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.onDone(user);
                view.allowChat(isAllowChat);
                view.isFollow(isFollow);
            }
        });
    }

    @Override
    public UserDb getUserInfo() {
        return user;
    }
}
