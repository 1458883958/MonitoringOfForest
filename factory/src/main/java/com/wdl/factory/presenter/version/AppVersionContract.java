package com.wdl.factory.presenter.version;

import com.wdl.factory.model.card.Version;
import com.wdl.factory.presenter.BaseContract;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.version
 * 创建者：   wdl
 * 创建时间： 2018/9/4 20:12
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public interface AppVersionContract {
    interface View extends BaseContract.View<Presenter> {

        /**
         * 提示更新内容的弹框
         *
         * @param version json
         */
        void showDialog(Version version);
    }

    interface Presenter extends BaseContract.Presenter {
        /**
         * 检查版本
         */
        void checkVersion();

        void update();
    }
}
