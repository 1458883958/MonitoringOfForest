package com.wdl.factory.presenter.message;

import android.support.v7.widget.RecyclerView;

import com.wdl.factory.model.db.MessageDb;
import com.wdl.factory.presenter.BaseContract;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.message
 * 创建者：   wdl
 * 创建时间： 2018/9/15 19:01
 * 描述：    TODO
 */
public interface MessageContract {
    interface View extends BaseContract.RecyclerView<MessageDb, Presenter> {
        RecyclerView getRecycler();
    }

    interface Presenter extends BaseContract.Presenter {
        /**
         * 发送消息
         *
         * @param type     发送类型
         * @param receiver 接收者
         * @param content  内容
         */
        void pushMessage(int type, int receiver, String content);

        /**
         * 发送消息
         *
         * @param type     发送类型
         * @param receiver 接收者
         * @param contents 内容
         */
        void pushImages(int type, int receiver, String[] contents);


        /**
         * @param type     类型
         * @param receiver 接收者
         * @param content  路径
         * @param time     语音时长
         */
        void pushAudio(int type, int receiver, String content, long time);
    }
}
