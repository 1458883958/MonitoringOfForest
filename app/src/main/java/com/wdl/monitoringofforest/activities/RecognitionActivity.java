package com.wdl.monitoringofforest.activities;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wdl.common.app.PresenterToolbarActivity;
import com.wdl.common.app.ToolbarActivity;
import com.wdl.common.widget.Progress;
import com.wdl.factory.Factory;
import com.wdl.factory.persistence.Account;
import com.wdl.factory.presenter.recognition.RecognitionContract;
import com.wdl.factory.presenter.recognition.RecognitionPresenter;
import com.wdl.monitoringofforest.R;
import com.wdl.utils.BitmapUtil;
import com.wdl.utils.LogUtils;
import com.wdl.utils.baidu.Base64Util;
import com.wdl.utils.baidu.FileUtil;
import com.wdl.utils.baidu.HttpUtil;


import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.OnClick;

public class RecognitionActivity extends PresenterToolbarActivity<RecognitionContract.Presenter>
    implements RecognitionContract.View{
    private static final String IMAGE_BITMAP = "IMAGE_BITMAP";
    private String path;
    Progress dialog = null;
    @BindView(R.id.image)
    ImageView imageView;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_recognition;
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        path = bundle.getString(IMAGE_BITMAP);
        return !TextUtils.isEmpty(path);
    }

    public static void show(Context context, String path) {
        Intent intent = new Intent(context, RecognitionActivity.class);
        intent.putExtra("IMAGE_BITMAP", path);
        context.startActivity(intent);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitle("处理");
        Glide.with(this)
                .load(path)
                .dontAnimate()
                .fitCenter()
                .into(imageView);
    }

    @Override
    protected RecognitionContract.Presenter initPresenter() {
        return new RecognitionPresenter(this);
    }

    class DealPicHandler implements Runnable {

        @Override
        public void run() {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            Bitmap bitmapBinary = BitmapUtil.convertToBMW(bitmap, 100);
            double x = BitmapUtil.result(bitmapBinary, new BitmapUtil.Progress() {
                @Override
                public void progress(int current, int total) {

                    if (current < total) {
                        Message message = Message.obtain();
                        message.what = 1;
                        message.arg1 = current;
                        message.arg2 = total;
                        handler.sendMessage(message);
                    }
                }
            });
            LogUtils.e("result:"+x);
            Message message = new Message();
            message.what = 2;
            message.obj = bitmapBinary;
            handler.sendMessage(message);
        }
    }

    @OnClick(R.id.action_thr)
    void thr() {
        dialog = new Progress(this);
        Factory.runOnAsy(new DealPicHandler());
    }

    @OnClick(R.id.action_rec)
    void rec() {
        mPresenter.recognition(path);
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                int current = msg.arg1;
                int total = msg.arg2;
                double result = (double) current / total;
                int x = (int) (result * 100 + 0.5);
                dialog.setProgress(x);
            } else if (msg.what == 2) {
                Bitmap x = (Bitmap) msg.obj;
                imageView.setImageBitmap(x);
                dialog.dismiss();
            }
            return true;
        }
    });
}
