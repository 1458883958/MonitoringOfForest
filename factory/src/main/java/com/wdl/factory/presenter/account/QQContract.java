package com.wdl.factory.presenter.account;

import com.wdl.factory.presenter.BaseContract;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.account
 * 创建者：   wdl
 * 创建时间： 2018/8/15 13:44
 * 描述：    QQ绑定注册 契约
 */
@SuppressWarnings("unused")
public interface QQContract {
    interface View extends BaseContract.View<Presenter>{
        void succeed();
        /**
         * 设置code
         *
         * @param code code
         */
        void setCode(String code);
    }
    interface Presenter extends BaseContract.Presenter{
        /**
         * 获取验证码
         *
         * @param phone 手机号
         */
        void getSms(String phone);

        /**
         * @param phone 手机号
         * @return 格式是否正确
         */
        boolean checkPhone(String phone);

        void bind(String userId,String phone, String password);

        void register(String userId,String phone, String password,String code);
    }
}
