package com.wdl.factory.data.data.message;

import android.text.TextUtils;

import com.wdl.factory.data.data.helper.DbHelper;
import com.wdl.factory.data.data.notice.NoticeCenter;
import com.wdl.factory.model.card.Message;
import com.wdl.factory.model.card.Notice;
import com.wdl.factory.model.db.MessageDb;
import com.wdl.factory.model.db.NoticeDb;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.data.data.pi
 * 创建者：   wdl
 * 创建时间： 2018/8/7 11:33
 * 描述：    设备中心实现类
 */
@SuppressWarnings("unused")
public class MessageDispatcher implements MessageCenter {
    //单例
    private static MessageCenter instance;
    //单线程池,用来处理设备列表
    private final Executor executor = Executors.newSingleThreadExecutor();

    /**
     * 获取单例
     *
     * @return PiCenter
     */
    public static MessageCenter getMessageCenter() {
        //如果单前实例为空,避免重复创建
        if (instance == null) {
            //同步操作
            synchronized (MessageDispatcher.class) {
                //内存中，此实例为空,创建
                if (instance == null)
                    instance = new MessageDispatcher();
            }
        }
        return instance;
    }

    @Override
    public void dispatch(Message... messages) {
        if (messages == null || messages.length == 0) return;
        //丢到单线程池中
        executor.execute(new MessageHandler(messages));
    }


    private class MessageHandler implements Runnable {
        private final Message[] messages;

        public MessageHandler(Message[] messages) {
            this.messages = messages;
        }

        @Override
        public void run() {
            //单线程池调度时触发
            List<MessageDb> dbs = new ArrayList<>();
            for (Message message : messages) {
                if (message==null|| TextUtils.isEmpty(""+message.getMReceiver())||
                        TextUtils.isEmpty(""+message.getMSender()))continue;
                //添加
                MessageDb db = message.build();
                dbs.add(db);
            }
            //进行数据库存储并分发通知
            DbHelper.save(MessageDb.class,dbs.toArray(new MessageDb[0]));
        }
    }
}
