package com.wdl.monitoringofforest.service;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.wdl.factory.Factory;
import com.wdl.factory.persistence.Account;
import com.wdl.utils.LogUtils;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.monitoringofforest.service
 * 创建者：   wdl
 * 创建时间： 2018/9/7 15:08
 * 描述：    TODO
 */

/**
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息<br>
 * onReceiveMessageData 处理透传消息<br>
 * onReceiveClientId 接收 cid <br>
 * onReceiveOnlineState cid 离线上线通知 <br>
 * onReceiveCommandResult 各种事件处理回执 <br>
 */
public class MessageIntentService extends GTIntentService {

    private GTNotificationMessage message;
    public MessageIntentService(){}

    @Override
    public void onReceiveServicePid(Context context, int pid) {
        LogUtils.e("onReceiveServicePid -> " + pid);
    }

    /**
     * 接收cid
     *
     * @param context
     * @param s       cId
     */
    @Override
    public void onReceiveClientId(Context context, String s) {
        LogUtils.e("clientId:" + s);
        initPushId(s);
    }

    /**
     * 接收透传消息
     *
     * @param context
     * @param gtTransmitMessage 透传消息
     */
    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage gtTransmitMessage) {
        String message = new String(gtTransmitMessage.getPayload());
        LogUtils.e("message:"+message);
        messageArrived(message);
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean b) {

    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {

    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage message) {
        LogUtils.e("onNotificationMessageArrived -> " + "appid = " + message.getAppid() + "\ntaskid = " + message.getTaskId() + "\nmessageid = "
                + message.getMessageId() + "\npkg = " + message.getPkgName() + "\ncid = " + message.getClientId() + "\ntitle = "
                + message.getTitle() + "\ncontent = " + message.getContent());
         this.message = message;
//        Intent intent = new Intent(context, PreviewNoticeActivity.class);
//        @SuppressLint("WrongConstant")
//        PendingIntent pendingIntent = PendingIntent
//                .getActivity(this,1,intent,Intent.FLAG_ACTIVITY_NEW_TASK);
//        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
//                .setContentText(message.getContent())
//                .setTicker(message.getTitle())
//                .setContentTitle(message.getTitle());
//        Notification notification = builder.build();
//        assert manager != null;
//        manager.notify(0,notification);
    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage message) {
        LogUtils.d("onNotificationMessageClicked -> " + "appid = " + message.getAppid() + "\ntaskid = " + message.getTaskId() + "\nmessageid = "
                + message.getMessageId() + "\npkg = " + message.getPkgName() + "\ncid = " + message.getClientId() + "\ntitle = "
                + message.getTitle() + "\ncontent = " + message.getContent());
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sh://12"));
        intent.putExtra("content",this.message.getContent());
        intent.putExtra("title",this.message.getTitle());
        startActivity(intent);
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

    /**
     * 消息处理
     *
     * @param message 推送到达的消息
     */
    private void messageArrived(String message) {
        Factory.dispatchMessage(message);
    }
}
