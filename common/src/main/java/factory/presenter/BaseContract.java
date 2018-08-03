package factory.presenter;

import android.support.annotation.StringRes;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    factory.presenter
 * 创建者：   wdl
 * 创建时间： 2018/8/3 14:29
 * 描述：    基础契约接口
 */
@SuppressWarnings("unused")
public interface BaseContract {

    /**
     * view层的基本功能
     *
     * @param <T> presenter
     */
    interface View<T extends Presenter> {

        /**
         * 显示进度
         */
        void showLoading();

        /**
         * 显示错误信息
         *
         * @param res 资源id
         */
        void showError(@StringRes int res);

        /**
         * 给view设置presenter的引用
         *
         * @param presenter presenter
         */
        void setPresenter(T presenter);
    }

    /**
     * 基本Presenter的职责
     */
    interface Presenter {

        /**
         * 开始触发
         */
        void start();

        /**
         * 销毁触发
         */
        void destroy();
    }
}
