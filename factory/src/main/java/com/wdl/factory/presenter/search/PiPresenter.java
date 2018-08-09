package com.wdl.factory.presenter.search;

import android.text.TextUtils;
import com.wdl.factory.R;
import com.wdl.factory.data.DataSource;
import com.wdl.factory.data.data.helper.PiHelper;
import com.wdl.factory.model.api.pi.PiModel;
import com.wdl.factory.model.card.Pi;
import com.wdl.factory.model.db.PiDb;
import com.wdl.factory.persistence.Account;
import com.wdl.factory.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.search
 * 创建者：   wdl
 * 创建时间： 2018/8/9 11:32
 * 描述：    用户绑定Pi
 */
@SuppressWarnings("unused")
public class PiPresenter extends BasePresenter<PiContract.View>
    implements PiContract.Presenter,DataSource.Callback<Pi>{
    public PiPresenter(PiContract.View view) {
        super(view);
    }

    /**
     * 绑定
     * @param pId      设备Id 扫码获取
     * @param password 密码
     */
    @Override
    public void bind(Integer pId, String password) {
        start();
        final PiContract.View view = getView();
        int userId = Account.getUserId();
        if (userId==-1){
            view.showError(R.string.data_account_error_un_login);
            return;
        }
        else if (TextUtils.isEmpty(password)){
            view.showError(R.string.data_bind_password_invalid_parameter);
        }else {
            PiModel model = new PiModel();
            model.setuId(userId);
            model.setpPassword(password);
            model.setpId(pId);
            PiHelper.bind(model,this);
        }
    }

    @Override
    public void onLoaded(Pi data) {
        final PiContract.View view = getView();
        if (view==null)return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.bindSucceed();
            }
        });
    }

    @Override
    public void onNotAvailable(int res) {

    }
}
