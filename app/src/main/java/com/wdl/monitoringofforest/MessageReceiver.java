package com.wdl.monitoringofforest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.igexin.sdk.PushConsts;
import com.wdl.factory.Factory;
import com.wdl.factory.persistence.Account;
import com.wdl.utils.LogUtils;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.monitoringofforest
 * 创建者：   wdl
 * 创建时间： 2018/8/6 15:59
 * 描述：    个推sdk 消息接收器
 */
@SuppressWarnings("unchecked")
public class MessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) return;
        Bundle bundle = intent.getExtras();

        //获取推送的类型
        assert bundle != null;
        int code = bundle.getInt(PushConsts.CMD_ACTION);
        //判断类型,做出相应的动作
        switch (code) {
            //获取pushId
            case PushConsts.GET_CLIENTID:
                LogUtils.e("pushId:" + bundle.toString());
                initPushId(bundle.getString("clientid"));
                break;
            //常规消息
            case PushConsts.GET_MSG_DATA:
                byte[] payload = bundle.getByteArray("payload");
                if (payload != null) {
                    String message = new String(payload);
                    LogUtils.e("message:" + message);
                    messageArrived(message);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 消息处理
     *
     * @param message 推送到达的消息
     */
    private void messageArrived(String message) {
        Factory.dispatchMessage(message);
    }

    /**
     * 设置pushId
     *
     * @param clientid 个推给予的id
     */
    private void initPushId(String clientid) {
        Account.setPushId(clientid);
        //如果登录,绑定pushID
        //未登录状态下不能绑定pushID
        if (Account.isLogin()){
            //TODO 绑定
        }
    }
}
