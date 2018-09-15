package com.wdl.monitoringofforest.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.wdl.common.app.Activity;
import com.wdl.factory.model.db.UserDb;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.fragments.message.ChatUserFragment;

public class MessageActivity extends Activity {
    //接收者ID
    public static final String KEY_RECEIVER_ID = "KEY_RECEIVER_ID";

    private int receiverId;

    /**
     * 显示入口
     *
     * @param context 上下文
     * @param userDb  聊天的人
     */
    public static void show(Context context, UserDb userDb) {
        if (context == null || TextUtils.isEmpty(userDb.getId() + "")) return;
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra(KEY_RECEIVER_ID, userDb.getId());
        context.startActivity(intent);

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        receiverId = bundle.getInt(KEY_RECEIVER_ID);
        return receiverId != -1;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitle("");
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_RECEIVER_ID,receiverId);
        Fragment fragment = ChatUserFragment.newInstance();
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container,fragment)
                .commit();
    }
}
