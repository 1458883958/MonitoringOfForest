package com.wdl.factory.data.data.helper;

import android.os.SystemClock;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.wdl.common.app.Application;
import com.wdl.common.common.Common;
import com.wdl.factory.Factory;
import com.wdl.factory.model.api.CallbackImpl;
import com.wdl.factory.model.api.account.RspModel;
import com.wdl.factory.model.card.Message;
import com.wdl.factory.model.db.MessageDb;
import com.wdl.factory.net.Network;
import com.wdl.factory.net.RemoteService;
import com.wdl.factory.net.UploadHelper;
import com.wdl.utils.LogUtils;
import com.wdl.utils.PicturesCompressor;
import com.wdl.utils.StreamUtil;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.data.data.helper
 * 创建者：   wdl
 * 创建时间： 2018/9/15 18:54
 * 描述：    TODO
 */
public class MessageHelper {

    /**
     * 发送消息
     *
     * @param message Message
     */
    public static void pushMsg(final Message message) {
        Factory.runOnAsy(new Runnable() {
            @Override
            public void run() {
                //判断发送的类型
                //是图片 文件类型的  先上传阿里云OSS
                String content = null;
                if (message.getTyep() == MessageDb.MESSAGE_TYPE_PIC) {
                    //图片上传
                    String path = message.getMContent().split("-")[1];
                    content = uploadImage(path);
                }
                if (!TextUtils.isEmpty(content))
                    message.setMContent(message.getTyep() + "-" + content);
                RemoteService service = Network.remoteService();
                Call<RspModel> call = service.pushMessage(message);
                call.enqueue(new Callback<RspModel>() {
                    @Override
                    public void onResponse(Call<RspModel> call, Response<RspModel> response) {
                        RspModel rspModel = response.body();
                        if (rspModel != null) {
                            if (rspModel.getStatus() == 200) {
                                Factory.getMessageCenter().dispatch(message);
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<RspModel> call, Throwable t) {

                    }
                });
            }
        });

    }

    /**
     * 上传 先压缩
     *
     * @param path 本地路径
     * @return String
     */
    private static String uploadImage(String path) {
        File file = null;
        try {
            //通过glide 缓存区间 解决了图片外部权限的问题
            file = Glide.with(Factory.application())
                    .load(path)
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (file != null) {
            //压缩
            String cacheDir = Application.getCacheDirFile().getAbsolutePath();
            String tempFile = String.format("%s/image/Cache_%s.png", cacheDir, SystemClock.uptimeMillis());
            try {
                //压缩  原路径  目标路径  大小
                if (PicturesCompressor.compressImage(file.getAbsolutePath(),
                        tempFile,
                        Common.Constance.MAX_UPLOAD_IMAGE_LENGTH)) {
                    //上传
                    String ossPath = UploadHelper.uploadImage(tempFile);
                    //清理缓存
                    StreamUtil.delete(tempFile);

                    return ossPath;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return null;
    }
}
