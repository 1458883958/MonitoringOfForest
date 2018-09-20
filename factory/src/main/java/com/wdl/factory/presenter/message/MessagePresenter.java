package com.wdl.factory.presenter.message;

import android.support.v7.util.DiffUtil;

import com.wdl.common.widget.recycler.RecyclerAdapter;
import com.wdl.factory.data.data.helper.MessageHelper;
import com.wdl.factory.data.data.message.MessageRepository;
import com.wdl.factory.model.card.Message;
import com.wdl.factory.model.db.MessageDb;
import com.wdl.factory.model.db.NoticeDb;
import com.wdl.factory.persistence.Account;
import com.wdl.factory.presenter.BaseSourcePresenter;
import com.wdl.factory.presenter.notice.NoticeContract;
import com.wdl.factory.utils.DiffUiDataCallback;
import com.wdl.utils.LogUtils;

import java.util.List;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.message
 * 创建者：   wdl
 * 创建时间： 2018/9/15 19:03
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public class MessagePresenter extends BaseSourcePresenter<MessageDb, MessageDb, MessageRepository,
        MessageContract.View> implements MessageContract.Presenter {
    public MessagePresenter(MessageContract.View view, int receiver) {
        super(view, new MessageRepository(receiver));
    }

    @Override
    public void start() {
        super.start();

    }

    @Override
    public void onLoaded(List<MessageDb> data) {
        final MessageContract.View view = getView();
        if (view == null) return;
        RecyclerAdapter<MessageDb> adapter = view.getRecyclerAdapter();
        List<MessageDb> old = adapter.getItems();
        DiffUtil.Callback callback = new DiffUiDataCallback<>(old, data);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        refreshData(result, data, view.getRecycler());
    }

    @Override
    public void pushMessage(int type, int receiver, String content) {
        if (content == null) return;
        Message message = new Message();
        message.setMContent(type + "-" + content);
        message.setTyep(type);
        message.setMBeemail(1);
        message.setMReceiver(receiver);
        message.setMSender(Account.getUserId());
        message.setMSubject("chat");
        LogUtils.e("pushMessage:" + message.toString());
        MessageHelper.pushMsg(message);
    }

    @Override
    public void pushImages(int type, int receiver, String[] contents) {
        if (contents == null || contents.length == 0) return;
        for (String content : contents) {
            Message message = new Message();
            message.setMContent(type + "-" + content);
            message.setTyep(type);
            message.setMBeemail(1);
            message.setMReceiver(receiver);
            message.setMSender(Account.getUserId());
            message.setMSubject("chat");
            MessageHelper.pushMsg(message);
        }

    }

    @Override
    public void pushAudio(int type, int receiver, String content, long time) {
        if (content == null) return;
        Message message = new Message();
        message.setMContent(type + "-" + content + "@" + time);
        message.setTyep(type);
        message.setMBeemail(1);
        message.setMReceiver(receiver);
        message.setMSender(Account.getUserId());
        message.setAttach(String.valueOf(time));
        message.setMSubject("chat");
        LogUtils.e("pushMessage:" + message.toString());
        MessageHelper.pushMsg(message);
    }


}
