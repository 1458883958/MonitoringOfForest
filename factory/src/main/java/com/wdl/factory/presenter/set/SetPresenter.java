package com.wdl.factory.presenter.set;

import com.wdl.factory.Factory;
import com.wdl.factory.R;
import com.wdl.factory.presenter.BasePresenter;
import com.wdl.utils.DataCleanManager;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.set
 * 创建者：   wdl
 * 创建时间： 2018/8/19 16:51
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public class SetPresenter extends BasePresenter<SetContract.View>
        implements SetContract.Presenter {
    public SetPresenter(SetContract.View view) {
        super(view);
    }

    /**
     * 清除缓存
     */
    @Override
    public void cleanCache() {
        final SetContract.View view = getView();
        boolean flag = DataCleanManager.cleanInternalCache(Factory.application());
        refresh(flag,view);
    }

    private void refresh(final boolean flag, final SetContract.View view) {
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                if (view != null) {
                    if (flag) {
                        view.cleanSucceed();
                    } else
                        view.showError(R.string.data_clean_fail);
                }
            }
        });
    }
}
