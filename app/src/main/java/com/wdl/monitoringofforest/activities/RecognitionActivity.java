package com.wdl.monitoringofforest.activities;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wdl.common.app.PresenterToolbarActivity;
import com.wdl.common.app.ToolbarActivity;
import com.wdl.common.common.Common;
import com.wdl.common.widget.Progress;
import com.wdl.factory.Factory;
import com.wdl.factory.model.card.RecResult;
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
import cn.sharesdk.onekeyshare.OnekeyShare;

public class RecognitionActivity extends PresenterToolbarActivity<RecognitionContract.Presenter>
    implements RecognitionContract.View{
    private static final String IMAGE_BITMAP = "IMAGE_BITMAP";
    private String path;
    Progress dialog = null;
    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.result)
    TextView mResult;

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

    @Override
    public void succeed(RecResult result) {
        mResult.setText(result.toString());
        showDialog(result.toString());
    }

    private void showDialog(String result){
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(R.string.title_rec_result)
                .setMessage(result)
                .setNegativeButton(R.string.label_back, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(R.string.label_go_share, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        share();
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void share(){
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("分享");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl(Common.Constance.URL);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("郁闭度为:"+Double.valueOf(mResult.getText().toString().substring(0,5)));
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(path);//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网使用
        //oks.setComment("我是测试评论文本");
        // 启动分享GUI
        oks.show(this);
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
            Message message = new Message();
            message.what = 2;
            Bundle bundle = new Bundle();
            bundle.putDouble("result",x);
            bundle.putParcelable("bitmap",bitmap);
            message.setData(bundle);
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
        @SuppressLint("SetTextI18n")
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                int current = msg.arg1;
                int total = msg.arg2;
                double result = (double) current / total;
                int x = (int) (result * 100 + 0.5);
                dialog.setProgress(x);
            } else if (msg.what == 2) {
                Bundle bundle = msg.getData();
                Bitmap bitmap = bundle.getParcelable("bitmap");
                Double x = bundle.getDouble("result");
                imageView.setImageBitmap(bitmap);
                dialog.dismiss();
                mResult.setText(""+x);
                showDialog(""+x);
            }
            return true;
        }
    });
}
