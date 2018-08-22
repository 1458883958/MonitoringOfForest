package com.wdl.factory.presenter.recognition;

import android.text.TextUtils;

import com.wdl.factory.Factory;
import com.wdl.factory.data.data.helper.RecognitionHelper;
import com.wdl.factory.persistence.Account;
import com.wdl.factory.presenter.BasePresenter;
import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;


/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.recognition
 * 创建者：   wdl
 * 创建时间： 2018/8/22 14:50
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public class RecognitionPresenter extends BasePresenter<RecognitionContract.View>
    implements RecognitionContract.Presenter{
    public RecognitionPresenter(RecognitionContract.View view) {
        super(view);
    }

    @Override
    public void recognition(final String path) {
        final RecognitionContract.View view = getView();
        if (TextUtils.isEmpty(path))return;
        final String accessToken = Account.getToken();
        if ("".equals(accessToken)) {
            if (view!=null){
                Run.onUiAsync(new Action() {
                    @Override
                    public void call() {
                       // view.showError();
                    }
                });
            }
            return;
        }
        Factory.runOnAsy(new Runnable() {
            @Override
            public void run() {
                RecognitionHelper.recognition(path,accessToken);
            }
        });
    }
}
