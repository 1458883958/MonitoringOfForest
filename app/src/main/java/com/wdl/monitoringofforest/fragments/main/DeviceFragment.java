package com.wdl.monitoringofforest.fragments.main;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.wdl.common.common.app.Application;
import com.wdl.common.common.app.Fragment;
import com.wdl.common.common.widget.PortraitView;
import com.wdl.factory.Factory;
import com.wdl.factory.net.UploadHelper;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.fragments.media.GalleryFragment;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import utils.LogUtils;

import static android.app.Activity.RESULT_OK;

/**
 * 设备模块
 */
@SuppressWarnings("unused")
public class DeviceFragment extends Fragment {

    @BindView(R.id.mPortrait)
    PortraitView mPortrait;

    private String path;

    public DeviceFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_device;
    }

    @OnClick(R.id.mPortrait)
    void selectPortrait() {
        new GalleryFragment()
                .setListener(new GalleryFragment.OnSelectImageListener() {
                    @Override
                    public void onSelect(String path) {
                        UCrop.Options options = new UCrop.Options();
                        //设置图片处理的格式
                        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                        //设置图片压缩后的精度
                        options.setCompressionQuality(96);
                        //获取头像的缓存地址
                        File targetPath = Application.getPortraitTmpFile();
                        UCrop.of(Uri.fromFile(new File(path)), Uri.fromFile(targetPath))
                                .withAspectRatio(1, 1)  //比例
                                .withMaxResultSize(520, 520) //返回的像素
                                .withOptions(options)
                                .start(Objects.requireNonNull(getActivity()));

                    }
                }).show(getChildFragmentManager(), DeviceFragment.class.getName());
    }


    /**
     * 收到从activity传过来的回调,取出值进行图片加载
     * 判断是否能够处理
     *
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        Intent
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUrl = UCrop.getOutput(data);
            if (resultUrl != null) {
                loadPortrait(resultUrl);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }

    /**
     * 加载Uri到头像
     *
     * @param resultUrl Uri
     */
    private void loadPortrait(Uri resultUrl) {
        Glide.with(this)
                .load(resultUrl)
                .asBitmap()
                .centerCrop()
                .dontAnimate()
                .into(mPortrait);
        path = resultUrl.getPath();
        Factory.runOnAsy(new Runnable() {
            @Override
            public void run() {
                String url = UploadHelper.uploadImage(path);
                LogUtils.e(path);
            }
        });
    }
}
