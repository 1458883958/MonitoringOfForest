package com.wdl.monitoringofforest.activities;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wdl.common.app.Application;
import com.wdl.common.app.ToolbarActivity;
import com.wdl.monitoringofforest.R;
import com.wdl.utils.BitmapUtil;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressWarnings("unused")
public class PreviewActivity extends ToolbarActivity {

    private static final String IMAGE_PATH = "IMAGE_PATH";
    private String path;

    @BindView(R.id.iv_pre)
    ImageView pre;

    @OnClick(R.id.cancel_button)
    void cancel() {
        finish();
    }

    @OnClick(R.id.rotate_button)
    void rotate() {
        pre.setDrawingCacheEnabled(true);
        Bitmap bitmap = pre.getDrawingCache();
        BitmapUtil.VX bmp = BitmapUtil.rotate(bitmap, path);
        pre.setDrawingCacheEnabled(false);
        pre.setImageBitmap(bmp.getBitmap());
        path = bmp.getPath();
    }

    @OnClick(R.id.confirm_button)
    void confirm() {
        pre.setDrawingCacheEnabled(true);
        Bitmap bitmap = pre.getDrawingCache();
        pre.setDrawingCacheEnabled(false);
        RecognitionActivity.show(this,path);
        finish();
    }

    /**
     * 显示入口
     *
     * @param context 上下文
     * @param path    拍照或者相册选取的路径
     */
    public static void show(Context context, String path) {
        Intent intent = new Intent(context, PreviewActivity.class);
        intent.putExtra(IMAGE_PATH, path);
        context.startActivity(intent);
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        path = bundle.getString(IMAGE_PATH);
        return !TextUtils.isEmpty(path);
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_preview;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        //selectByGallery();
        showPre();
    }

//    private void selectByGallery() {
//        UCrop.Options options = new UCrop.Options();
//        //设置图片处理的格式
//        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
//        //设置图片压缩后的精度
//        options.setCompressionQuality(100);
//        //获取头像的缓存地址
//        File targetPath = Application.getPortraitTmpFile();
//        UCrop.of(Uri.fromFile(new File(path)), Uri.fromFile(targetPath))
//                .withAspectRatio(1, 1)  //比例
//                .withOptions(options)
//                .start(PreviewActivity.this);
//
//    }
//
//    /**
//     * 收到从activity传过来的回调,取出值进行图片加载
//     * 判断是否能够处理
//     *
//     * @param requestCode 请求码
//     * @param resultCode  结果码
//     * @param data        Intent
//     */
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
//            final Uri resultUrl = UCrop.getOutput(data);
//            if (resultUrl != null) {
//                showPre(resultUrl);
//            }
//        } else if (resultCode == UCrop.RESULT_ERROR) {
//            final Throwable cropError = UCrop.getError(data);
//        }
//    }

    private void showPre() {
        Glide.with(this)
                .load(path)
                .fitCenter()
                .dontAnimate()
                .into(pre);
    }
}
