package com.wdl.factory.presenter.data;

import com.wdl.factory.model.db.ImageDb;
import com.wdl.factory.model.db.SensorDb;
import com.wdl.factory.presenter.BaseContract;

import java.util.List;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.data
 * 创建者：   wdl
 * 创建时间： 2018/8/16 15:44
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public interface DataContract {
    interface Presenter extends BaseContract.Presenter {
        /**
         * 查询传感器数据
         *
         * @param pId      设备ID
         * @param limitNum 查询条数
         */
        void select(int pId, int limitNum);

        /**
         * 查询传感器数据 数据库
         *
         * @param pId      设备ID
         * @param limitNum 查询条数
         */
        void selectOf(int pId, int limitNum);
    }

    interface ImageView extends BaseContract.RecyclerView<ImageDb, Presenter> {
    }

    interface SensorView extends BaseContract.View<Presenter> {
        /**
         * 成功回调用
         *
         * @param dbList List<SensorDb>
         */
        void succeed(List<SensorDb> dbList);
    }
}
