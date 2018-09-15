package com.wdl.factory.presenter.contact;

import com.wdl.factory.data.DataSource;
import com.wdl.factory.data.data.helper.UserHelper;
import com.wdl.factory.model.api.pi.Model;
import com.wdl.factory.model.card.User;
import com.wdl.factory.persistence.Account;
import com.wdl.factory.presenter.BasePresenter;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.contact
 * 创建者：   wdl
 * 创建时间： 2018/9/15 14:17
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public class FollowPresenter extends BasePresenter<FollowContract.View>
        implements FollowContract.Presenter,DataSource.SucceedCallback<Model>{
    private User follow;
    public FollowPresenter(FollowContract.View view) {
        super(view);
    }

    @Override
    public void follow(User user) {
        this.follow = user;
        Model model = new Model();
        model.setrSender(Account.getUserId());
        model.setrReceiver(user.getuId());
        UserHelper.follow(model,this);

    }

    @Override
    public void onLoaded(Model data) {
        UserHelper.agree(data,follow);
    }
}
