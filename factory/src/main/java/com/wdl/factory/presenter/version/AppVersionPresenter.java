package com.wdl.factory.presenter.version;

import com.wdl.factory.Factory;
import com.wdl.factory.R;
import com.wdl.factory.data.DataSource;
import com.wdl.factory.data.data.helper.VersionHelper;
import com.wdl.factory.model.card.Version;
import com.wdl.factory.presenter.BasePresenter;
import com.wdl.utils.ApkVersionCodeUtils;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.version
 * 创建者：   wdl
 * 创建时间： 2018/9/4 20:25
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public class AppVersionPresenter extends BasePresenter<AppVersionContract.View>
    implements AppVersionContract.Presenter,DataSource.Callback<Version>{

    public AppVersionPresenter(AppVersionContract.View view) {
        super(view);
    }

    @Override
    public void checkVersion() {
        start();
        VersionHelper.check(this);
    }

    @Override
    public void update() {

    }

    @Override
    public void onLoaded(final Version data) {
        final int versionCode = Integer.valueOf(data.getVAppversion());
        final int current = ApkVersionCodeUtils.getVersionCode(Factory.application());
        final AppVersionContract.View view = getView();
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                if (versionCode>current){
                    view.showDialog(data);
                }else
                    view.showError(R.string.data_the_latest_version);
            }
        });

    }

    @Override
    public void onNotAvailable(int res) {

    }
}
