package com.wdl.factory.presenter.account;

import com.wdl.factory.presenter.BaseContract;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.account
 * 创建者：   wdl
 * 创建时间： 2018/8/4 10:21
 * 描述：    注册契约
 */
@SuppressWarnings("unused")
public interface RegisterContract {

    interface View extends BaseContract.View<Presenter> {
        /**
         * 注册成功
         */
        void registerSucceed();

        /**
         * 设置code
         *
         * @param code code
         */
        void setCode(String code);
    }

    interface Presenter extends BaseContract.Presenter {

        /**
         * @param phone    用户名
         * @param passwordA 密码
         * @param passwordB 密码
         * @param name     姓名
         * @param code     验证码
         */
        void register(String phone, String passwordA,String passwordB, String name, String code);

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

        /**
         * 检测两次输入的密码是否一致
         *
         * @param passwordA 密码
         * @param passwordB 密码
         * @return 密码是否一致
         */
        boolean checkPassword(String passwordA, String passwordB);
    }
}
