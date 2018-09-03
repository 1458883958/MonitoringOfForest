package com.wdl.factory.presenter.pi;

import com.wdl.factory.model.card.Pi;

import com.wdl.factory.model.db.PiDb;
import com.wdl.factory.presenter.BaseContract;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.pi
 * 创建者：   wdl
 * 创建时间： 2018/8/6 11:20
 * 描述：    设备契约接口
 */
@SuppressWarnings("unused")
public interface PiContract {

    /**
     * 进行数据刷新等操作
     */
    interface View extends BaseContract.RecyclerView<PiDb, Presenter> {
        void changedSucceed();
    }

    /**
     * start()直接进行数据加载
     */
    interface Presenter extends BaseContract.Presenter {
        /**
         * 改变设备拍照状态
         */
        void changedSwitch(int pId, int state);

        void changedSwitch(PiDb db);

        /**
         * 删除设备
         *
         * @param pId 设备Id
         * @param uId 用户Id
         */
        void deleteDevice(int pId, int uId);

        /**
         * 更新设备
         *
         * @param remark    备注
         * @param threshold 阈值
         * @param delayed   延时
         */
        void update(PiDb pi, int pId, String remark, Integer threshold, Integer delayed, String password);
    }
}
